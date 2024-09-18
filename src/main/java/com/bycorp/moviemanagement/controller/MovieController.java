package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.services.MovieService;
import com.bycorp.moviemanagement.utils.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController //combina @Controller y @ResponseBody
@RequestMapping("/movies") //este controlador es accesible por localhost:puerto/path/movies
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> findAllMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MovieGenre genre
    ) {
        List<Movie> movies = null;
        if(StringUtils.hasText(title) && genre != null) { //si hay titulo y hay genero
            movies = movieService.findAllByTitleAndGenre(title, genre);
        }else if(StringUtils.hasText(title)) { //si hay titulo
            movies = movieService.findAllByTitle(title);
        } else if(genre != null) { //si hay genero
            movies = movieService.findAllByGenre(genre);
        } else {//si no son hay filtros trae todos
            movies = movieService.findAll();
        }
        //return new ResponseEntity<>(movies, HttpStatusCode.valueOf(200)); opc 1
        //return ResponseEntity.status(HttpStatus.OK).body(movies); opc 2
        return ResponseEntity.ok(movies);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{idPelicula}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long idPelicula) {
        try{
            return ResponseEntity.ok(movieService.findOneById(idPelicula));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> createOneMovie(@RequestParam String title,
                                                @RequestParam MovieGenre genre,
                                                @RequestParam String director,
                                                @RequestParam int releaseYear,
                                                HttpServletRequest request) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDirector(director);
        movie.setGenre(genre);

        Movie movieCreated = movieService.createOne(movie);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl+"/"+movieCreated.getId());

        return  ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }
}
