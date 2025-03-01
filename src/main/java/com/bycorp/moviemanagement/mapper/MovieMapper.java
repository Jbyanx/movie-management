package com.bycorp.moviemanagement.mapper;

import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.dto.response.GetMovieStatistic;
import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.entity.Rating;

import java.util.List;

public class MovieMapper {

    public static GetMovie toGetDto(Movie entity) {
        if(entity == null) return null;

        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                entity.getRatings() != null ? entity.getRatings().size() : 0
        );
    }

    public static List<GetMovie> toGetDtoList(List<Movie> entities) {
        if(entities == null) return null;

        return entities.stream() //List<Movie> -> Stream<Movie>
                .map(MovieMapper::toGetDto) //Stream<Movie> -> Stream<GetMovie> //cada entidad la volvemos dto con la  funcion anterior
                .toList();
    }

    public static Movie toEntity(SaveMovie saveDto) {
        if(saveDto == null) return null;

        Movie newMovie = new Movie();
        newMovie.setTitle(saveDto.title());
        newMovie.setDirector(saveDto.director());
        newMovie.setGenre(saveDto.genre());
        newMovie.setReleaseYear(saveDto.releaseYear());

        return newMovie;
    }

    public static void updateEntity(Movie oldMovie, SaveMovie movieDto) {
        if(oldMovie == null || movieDto==null) return;

        oldMovie.setTitle(movieDto.title());
        oldMovie.setGenre(movieDto.genre());
        oldMovie.setDirector(movieDto.director());
        oldMovie.setReleaseYear(movieDto.releaseYear());
    }

    public static GetMovieStatistic toGetStatisticDto(
            Movie movie, int totalRatings, double averageRating, int lowestRating, int highestRating
    ) {
        if(movie == null) return null;

        return new GetMovieStatistic(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getGenre().name(),
                totalRatings,
                movie.getReleaseYear(),
                averageRating,
                lowestRating,
                highestRating
        );
    }
}
