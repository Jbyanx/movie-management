package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.SaveRating;
import com.bycorp.moviemanagement.dto.response.GetCompleteRating;
import com.bycorp.moviemanagement.services.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetCompleteRating>> getAllRatings(Pageable pageable) {
        return ResponseEntity.ok(ratingService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCompleteRating> findOneById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.findOneById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCompleteRating> updateOneById(@Valid @RequestBody SaveRating rating, @PathVariable Long id) {
        return ResponseEntity.ok(ratingService.updateOneById(id, rating));
    }

    @PostMapping
    public ResponseEntity<GetCompleteRating> saveOne(@Valid @RequestBody SaveRating rating, HttpServletRequest request) {
        GetCompleteRating createdRating = ratingService.createOne(rating);

        String baseUrl = request.getRequestURL().toString();

        URI newLocation = URI.create(baseUrl + "/" + createdRating.ratingId());

        return ResponseEntity
                .created(newLocation)
                .body(createdRating);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        ratingService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }
}
