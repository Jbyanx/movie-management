package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.MovieSearchCriteria;
import com.bycorp.moviemanagement.dto.request.SaveMovie;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.dto.response.GetMovieStatistic;
import com.bycorp.moviemanagement.services.MovieService;
import com.bycorp.moviemanagement.services.RatingService;
import com.bycorp.moviemanagement.utils.MovieGenre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Películas", description = "Operaciones relacionadas con la gestión de películas")
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final RatingService ratingService;
    private final MovieService movieService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @Operation(
            summary = "Buscar películas",
            description = "Devuelve una lista paginada de películas filtradas por título, género, año de lanzamiento y calificación promedio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de películas obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<GetMovie>> findAllMovies(
            @Parameter(description = "Título de la película para filtrar", example = "Inception")
            @RequestParam(required = false) String title,

            @Parameter(description = "Género de la película", example = "ACTION")
            @RequestParam(required = false) MovieGenre genre,

            @Parameter(description = "Año mínimo de lanzamiento", example = "2000")
            @RequestParam(required = false, name = "min_release_year") Integer minReleaseYear,

            @Parameter(description = "Año máximo de lanzamiento", example = "2024")
            @RequestParam(required = false, name = "max_release_year") Integer maxReleaseYear,

            @Parameter(description = "Calificación promedio mínima", example = "7")
            @RequestParam(required = false, name = "min_average_rating") Integer minAverageRating,

            @Parameter(description = "Nombre de usuario que calificó la película", example = "johndoe")
            @RequestParam(required = false, name = "username") String username,

            @Parameter(description = "Parámetros de paginación") Pageable moviePageable
    ) {
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title, genre, minReleaseYear, maxReleaseYear, minAverageRating, username);
        Page<GetMovie> getMovies = movieService.findAll(searchCriteria, moviePageable);
        return ResponseEntity.ok(getMovies);
    }

    @Operation(
            summary = "Obtener una película por ID",
            description = "Devuelve los detalles de una película específica por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película encontrada"),
            @ApiResponse(responseCode = "404", description = "Película no encontrada")
    })
    @GetMapping("/{idPelicula}")
    public ResponseEntity<GetMovieStatistic> findMovieById(
            @Parameter(description = "ID de la película", example = "1")
            @PathVariable Long idPelicula
    ) {
        return ResponseEntity.ok(movieService.findOneById(idPelicula));
    }

    @Operation(
            summary = "Obtener calificaciones de una película",
            description = "Devuelve una lista paginada de calificaciones asociadas a una película específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de calificaciones obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Película no encontrada")
    })
    @GetMapping("/{idPelicula}/ratings")
    public ResponseEntity<Page<GetMovie.GetRating>> findAllRatingsforMovieId(
            @Parameter(description = "ID de la película", example = "1")
            @PathVariable Long idPelicula,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ratingService.findRatingsByMovieId(idPelicula, pageable));
    }

    @Operation(
            summary = "Crear una nueva película",
            description = "Guarda una nueva película en la base de datos y devuelve la película creada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Película creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<GetMovie> createOneMovie(
            @RequestBody(
                    description = "Datos de la película a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SaveMovie.class))
            )
            @Valid SaveMovie movieDto,
            HttpServletRequest request
    ) {
        GetMovie movieCreated = movieService.createOne(movieDto);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.id());

        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @Operation(
            summary = "Actualizar una película",
            description = "Modifica los datos de una película existente por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Película no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{idPelicula}")
    public ResponseEntity<GetMovie> updateOneMovieById(
            @Parameter(description = "ID de la película a actualizar", example = "1")
            @PathVariable Long idPelicula,

            @RequestBody(
                    description = "Datos actualizados de la película",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SaveMovie.class))
            )
            @Valid SaveMovie movieDto
    ) {
        GetMovie updatedMovie = movieService.updateOneById(idPelicula, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @Operation(
            summary = "Eliminar una película",
            description = "Elimina una película de la base de datos por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Película eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Película no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneMovieById(
            @Parameter(description = "ID de la película a eliminar", example = "1")
            @PathVariable Long id
    ) {
        movieService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }
}
