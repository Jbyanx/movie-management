package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/movies") //este controlador es accesible por localhost:puerto/path/movies
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    //esta es la funcion por defecto cuando consultamos el controlador
    @ResponseBody //al usar @Controller necesitamos decir que metodos devuelven literalmente la respuesta o caso contrario quitar @ResponseBody para devolver una vista
    public List<Movie> findAllMovies() {
        return movieService.findAll();
    }
}