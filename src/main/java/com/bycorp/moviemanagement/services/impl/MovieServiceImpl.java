package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.dto.request.MovieSearchCriteria;
import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.mapper.MovieMapper;
import com.bycorp.moviemanagement.repository.MovieRepository;
import com.bycorp.moviemanagement.repository.specification.FindAllMoviesSpecification;
import com.bycorp.moviemanagement.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly=true)
    @Override
    public Page<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria, Pageable pageable) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(movieSearchCriteria);

        Page<Movie> entities = movieRepository.findAll(moviesSpecification, pageable);

        return entities.map(MovieMapper::toGetDto);
    }

    @Transactional(readOnly=true)
    @Override
    public GetMovie findOneById(Long id) {
        return MovieMapper.toGetDto(this.findOneEntityById(id));
    }

    @Transactional(readOnly=true)
    protected Movie findOneEntityById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("Movie "+Long.toString(id)));
    }

    @Override
    public GetMovie createOne(SaveMovie movieDto) {
        Movie newMovie = MovieMapper.toEntity(movieDto); //mapeamos de dto a entidad y la asignamos a una variable
        return MovieMapper.toGetDto( //devolvemos el mapeo a dto
                movieRepository.save(newMovie) //de la entidad ya guardada
        );
    }

    @Override
    public GetMovie updateOneById(Long id, SaveMovie movieDto) {
        Movie oldMovie = this.findOneEntityById(id);

        MovieMapper.updateEntity(oldMovie, movieDto);//vaya y actualicelo


        return MovieMapper.toGetDto(
                movieRepository.save(oldMovie)
        );
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneEntityById(id);
        movieRepository.delete(movie);
    }
}
