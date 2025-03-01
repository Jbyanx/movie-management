package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.dto.response.GetUserStatistic;
import com.bycorp.moviemanagement.services.RatingService;
import com.bycorp.moviemanagement.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RatingService ratingService;
    private UserService userService;

    @Autowired
    public UserController(UserService userService, RatingService ratingService) {
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<Page<GetUser>> findAllUsers(@RequestParam(required = false) String name, Pageable pageable){
        Page<GetUser> users = null;

        users = userService.findAll(name,pageable);

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetUserStatistic> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/{username}/ratings")
    public ResponseEntity<Page<GetUser.GetRating>> getUserRatings(@PathVariable String username, Pageable pageable){
        return ResponseEntity.ok(ratingService.findRatingsByUserUsername(username, pageable));
    }

    @PostMapping
    public ResponseEntity<GetUser> createUser(@RequestBody @Valid SaveUser userDto, HttpServletRequest request){
        GetUser userCreated = userService.createOne(userDto);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + userCreated.username());

        return ResponseEntity
                .created(newLocation)
                .body(userCreated);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<Integer>  updateOneUser(@PathVariable String  username, @RequestBody @Valid SaveUser newUserDto){
        GetUserStatistic oldUser = userService.findByUsername(username);
        return ResponseEntity.ok(userService.updateByUsername(username, newUserDto));
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteOneUser(@PathVariable String username){
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
