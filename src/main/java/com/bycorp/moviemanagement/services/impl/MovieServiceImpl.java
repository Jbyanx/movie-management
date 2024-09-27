package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.mapper.MovieMapper;
import com.bycorp.moviemanagement.repository.MovieRepository;
import com.bycorp.moviemanagement.services.MovieService;
import com.bycorp.moviemanagement.utils.MovieGenre;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public List<GetMovie> findAll() {
        return MovieMapper.toGetDtoList(movieRepository.findAll());
    }

    @Transactional(readOnly=true)
    @Override
    public List<GetMovie> findAllByTitle(String title) {
        return MovieMapper.toGetDtoList(movieRepository.findByTitleContaining(title));
    }

    @Transactional(readOnly=true)
    @Override
    public List<GetMovie> findAllByGenre(MovieGenre genre) {
        return MovieMapper.toGetDtoList(movieRepository.findByGenre(genre));
    }

    @Transactional(readOnly=true)
    @Override
    public List<GetMovie> findAllByTitleAndGenre(String title, MovieGenre genre) {
        return MovieMapper.toGetDtoList(movieRepository.findByTitleContainingAndGenre(title, genre));
    }

    @Transactional(readOnly=true)
    @Override
    public GetMovie findOneById(Long id) {
        return MovieMapper.toGetDto(this.findOneEntityById(id));
    }

    @Transactional(readOnly=true)
    protected Movie findOneEntityById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("Movie "+Long.toString(id)+" not found!"));
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
