package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.entity.User;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET) //cada metodo debe especificarse el verbo
    public ResponseEntity<List<User>> findAllUsers(@RequestParam(required = false) String name){
        List<User> users = null;

        if(StringUtils.hasText(name)){
            users = userService.findByNameContaining(name);
        }else {
            users = userService.findAll();
        }
        return ResponseEntity.ok(users);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        try{
            return ResponseEntity.ok(userService.findByUsername(username));
        } catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
