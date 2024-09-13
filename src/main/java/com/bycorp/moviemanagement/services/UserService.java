package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    List<User> findByNameContaining(String name);
    User findByUsername(String username);
    User findOneById(Long id);
    User createOne(User user);
    User updateOneById(Long id,User user);
    void deleteOneById(Long id);
    void deleteOneByUsername(String username);
}
