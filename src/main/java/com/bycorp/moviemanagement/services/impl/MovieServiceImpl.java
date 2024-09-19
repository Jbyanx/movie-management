package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.repository.MovieRepository;
import com.bycorp.moviemanagement.services.MovieService;
import com.bycorp.moviemanagement.utils.MovieGenre;
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
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Transactional(readOnly=true)
    @Override
    public List<Movie> findAllByTitle(String title) {
        return movieRepository.findByTitleContaining(title);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Movie> findAllByGenre(MovieGenre genre) {
        return movieRepository.findByGenre(genre);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Movie> findAllByTitleAndGenre(String title, MovieGenre genre) {
        return movieRepository.findByTitleContainingAndGenre(title, genre);
    }

    @Transactional(readOnly=true)
    @Override
    public Movie findOneById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("Movie "+Long.toString(id)+" not found!"));
    }

    @Override
    public Movie createOne(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateOneById(Long id, Movie movie) {
        Movie movieDb = findOneById(id);
        movieDb.setTitle(movie.getTitle());
        movieDb.setGenre(movie.getGenre());
        movieDb.setDirector(movie.getDirector());
        movieDb.setRatings(movie.getRatings());
        movieDb.setReleaseYear(movie.getReleaseYear());
        return movieRepository.save(movieDb);
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = findOneById(id);
        movieRepository.delete(movie);
    }
}
