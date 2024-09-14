package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.services.MovieService;
import com.bycorp.moviemanagement.utils.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
    public List<Movie> findAllMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MovieGenre genre
    ) {

        if(StringUtils.hasText(title) && genre != null) { //si hay titulo y hay genero
            return movieService.findAllByTitleAndGenre(title, genre);
        }else if(StringUtils.hasText(title)) { //si hay titulo
            return movieService.findAllByTitle(title);
        } else if(genre != null) { //si hay genero
            return movieService.findAllByGenre(genre);
        } //si no son hay filtros trae todos

        return movieService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{idPelicula}")
    public Movie findMovieById(@PathVariable Long idPelicula) {
        return movieService.findOneById(idPelicula);
    }

}
