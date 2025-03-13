package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.dto.response.GetUserStatistic;
import com.bycorp.moviemanagement.services.RatingService;
import com.bycorp.moviemanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "usuarios", description = "Operaciones sobre los usuarios")
@RestController
@RequestMapping("/users")
public class UserController {
    private final RatingService ratingService;
    private final UserService userService;

    public UserController(UserService userService, RatingService ratingService) {
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @Operation(summary = "Obtener lista de usuarios", description = "Devuelve una lista paginada de usuarios opcionalmente filtrada por nombre.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<GetUser>> findAllUsers(@Parameter(description = "Nombre del usuario para filtrar", example = "John Doe")
                                                      @RequestParam(required = false) String name,
                                                      Pageable pageable) {
        Page<GetUser> users = userService.findAll(name, pageable);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Obtener detalles de un usuario", description = "Devuelve estadísticas de un usuario identificado por su username.")
    @GetMapping(value = "/{username}")
    public ResponseEntity<GetUserStatistic> findByUsername(@Parameter(description = "Nombre de usuario", example = "johndoe")
                                                           @PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @Operation(summary = "Obtener calificaciones de un usuario", description = "Devuelve una lista paginada de calificaciones realizadas por el usuario.")
    @GetMapping("/{username}/ratings")
    public ResponseEntity<Page<GetUser.GetRating>> getUserRatings(@Parameter(description = "Nombre de usuario", example = "john_doe")
                                                                  @PathVariable String username,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(ratingService.findRatingsByUserUsername(username, pageable));
    }

    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario y devuelve su información.")
    @PostMapping
    public ResponseEntity<GetUser> createUser(@Valid @RequestBody SaveUser userDto) {
        GetUser userCreated = userService.createOne(userDto);
        URI newLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(userCreated.username())
                .toUri();
        return ResponseEntity.created(newLocation).body(userCreated);
    }

    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario identificado por su username.")
    @PutMapping(value = "/{username}")
    public ResponseEntity<Integer> updateOneUser(@Parameter(description = "Nombre de usuario", example = "johndoe")
                                                 @PathVariable String username,
                                                 @Valid @RequestBody SaveUser newUserDto) {
        return ResponseEntity.ok(userService.updateByUsername(username, newUserDto));
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario identificado por su username.")
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteOneUser(@Parameter(description = "Nombre de usuario", example = "johndoe")
                                              @PathVariable String username) {
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
