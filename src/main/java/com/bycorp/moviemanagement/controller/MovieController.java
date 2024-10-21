package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.services.MovieService;
import com.bycorp.moviemanagement.utils.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<GetMovie>> findAllMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MovieGenre genre
    ) {
        List<GetMovie> movies = null;

        if(StringUtils.hasText(title) && genre != null) { //si hay titulo y hay genero
            movies = movieService.findAllByTitleAndGenre(title, genre);
        }else if(StringUtils.hasText(title)) { //si hay titulo
            movies = movieService.findAllByTitle(title);
        } else if(genre != null) { //si hay genero
            movies = movieService.findAllByGenre(genre);
        } else {//si no son hay filtros trae todos
            movies = movieService.findAll();
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/{idPelicula}")
    public ResponseEntity<GetMovie> findMovieById(@PathVariable Long idPelicula) {
        return ResponseEntity.ok(movieService.findOneById(idPelicula));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOneMovie(@Valid @RequestBody SaveMovie movieDto,
                                                HttpServletRequest request) {

//        System.out.println("fecha: "+movieDto.availabilityEndTime());
        GetMovie movieCreated = movieService.createOne(movieDto);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl+"/"+movieCreated.id());

        return  ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PutMapping(value = "/{idPelicula}")
    public ResponseEntity<GetMovie> updateOneMovieById(@PathVariable Long idPelicula, @RequestBody @Valid SaveMovie movieDto) {
        try {
            GetMovie updatedMovie = movieService.updateOneById(idPelicula, movieDto);
            return ResponseEntity.ok(updatedMovie);
        } catch(ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneMovieById(@PathVariable Long id) {
        try{
            movieService.deleteOneById(id);
            return  ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
