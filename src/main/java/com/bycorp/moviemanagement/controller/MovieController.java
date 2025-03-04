package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.MovieSearchCriteria;
import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.dto.response.GetMovieStatistic;
import com.bycorp.moviemanagement.services.MovieService;
import com.bycorp.moviemanagement.services.RatingService;
import com.bycorp.moviemanagement.utils.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController //combina @Controller y @ResponseBody
@RequestMapping("/movies") //este controlador es accesible por localhost:puerto/path/movies
public class MovieController {
    private final RatingService ratingService;
    private final MovieService movieService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<Page<GetMovie>> findAllMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MovieGenre genre,
            @RequestParam(required = false, name = "min_release_year") Integer minReleaseYear,
            @RequestParam(required = false, name = "max_release_year") Integer maxReleaseYear,
            @RequestParam(required = false, name = "min_average_rating") Integer minAverageRating,
            @RequestParam(required = false, name = "username") String  username,
            Pageable moviePageable
    ) {
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title,genre,minReleaseYear,maxReleaseYear,minAverageRating, username);

        Page<GetMovie> getMovies = movieService.findAll(searchCriteria, moviePageable);

        return ResponseEntity.ok(getMovies);
    }

    @GetMapping(value = "/{idPelicula}")
    public ResponseEntity<GetMovieStatistic> findMovieById(@PathVariable Long idPelicula) {
        return ResponseEntity.ok(movieService.findOneById(idPelicula));
    }

    @GetMapping("/{idPelicula}/ratings")
    public ResponseEntity<Page<GetMovie.GetRating>> findAllRatingsforMovieId(@PathVariable Long idPelicula, Pageable pageable) {
        return ResponseEntity.ok(ratingService.findRatingsByMovieId(idPelicula, pageable));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOneMovie(@Valid @RequestBody SaveMovie movieDto,
                                                HttpServletRequest request) {

        GetMovie movieCreated = movieService.createOne(movieDto);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl+"/"+movieCreated.id());

        return  ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PutMapping(value = "/{idPelicula}")
    public ResponseEntity<GetMovie> updateOneMovieById(@PathVariable Long idPelicula, @RequestBody @Valid SaveMovie movieDto) {
        GetMovie updatedMovie = movieService.updateOneById(idPelicula, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneMovieById(@PathVariable Long id) {
        movieService.deleteOneById(id);
        return  ResponseEntity.noContent().build();
    }
}
