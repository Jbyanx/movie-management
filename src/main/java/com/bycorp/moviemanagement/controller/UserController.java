package com.bycorp.moviemanagement.controller;

import com.bycorp.moviemanagement.entity.User;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> findAllUsers(@RequestParam(required = false) String name){
        if(StringUtils.hasText(name)){
            return userService.findByNameContaining(name);
        }else {
            return userService.findAll();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public User findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }
}
