package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.SaveRating;
import com.bycorp.moviemanagement.dto.response.GetCompleteRating;
import com.bycorp.moviemanagement.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "calificaciones", description = "Operaciones sobre las calificaciones de películas")
@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(summary = "Obtener todas las calificaciones", description = "Devuelve una lista paginada de todas las calificaciones registradas.")
    @GetMapping
    public ResponseEntity<Page<GetCompleteRating>> getAllRatings(Pageable pageable) {
        return ResponseEntity.ok(ratingService.findAll(pageable));
    }

    @Operation(summary = "Obtener una calificación por ID", description = "Devuelve los detalles de una calificación específica identificada por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<GetCompleteRating> findOneById(@Parameter(description = "ID de la calificación", example = "1")
                                                         @PathVariable Long id) {
        return ResponseEntity.ok(ratingService.findOneById(id));
    }

    @Operation(summary = "Actualizar una calificación", description = "Actualiza los datos de una calificación identificada por su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<GetCompleteRating> updateOneById(@Valid @RequestBody SaveRating rating,
                                                           @Parameter(description = "ID de la calificación", example = "1")
                                                           @PathVariable Long id) {
        return ResponseEntity.ok(ratingService.updateOneById(id, rating));
    }

    @Operation(summary = "Crear una nueva calificación", description = "Crea una nueva calificación y devuelve su información.")
    @PostMapping
    public ResponseEntity<GetCompleteRating> saveOne(@Valid @RequestBody SaveRating rating) {
        GetCompleteRating createdRating = ratingService.createOne(rating);
        URI newLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRating.ratingId())
                .toUri();
        return ResponseEntity.created(newLocation).body(createdRating);
    }

    @Operation(summary = "Eliminar una calificación", description = "Elimina una calificación identificada por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@Parameter(description = "ID de la calificación", example = "1")
                                              @PathVariable Long id) {
        ratingService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }
}
