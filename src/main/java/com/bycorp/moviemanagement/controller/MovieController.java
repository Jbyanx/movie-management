package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<Movie> findAllMovies() {
        return movieService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{idPelicula}")
    public Movie findMovieById(@PathVariable Long idPelicula) {
        return movieService.findOneById(idPelicula);
    }

}
