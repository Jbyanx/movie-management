package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<GetUser>> findAllUsers(@RequestParam(required = false) String name, Pageable pageable){
        Page<GetUser> users = null;

        if(StringUtils.hasText(name)){
            users = userService.findByNameContaining(name, pageable);
        }else {
            users = userService.findAll(pageable);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetUser> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
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
        GetUser oldUser = userService.findByUsername(username);
        return ResponseEntity.ok(userService.updateByUsername(username, newUserDto));
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteOneUser(@PathVariable String username){
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
