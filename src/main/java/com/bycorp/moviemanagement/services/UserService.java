package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;

import java.util.List;

public interface UserService {
    List<GetUser> findAll();
    List<GetUser> findByNameContaining(String name);
    GetUser findByUsername(String username);
    GetUser findOneById(Long id);
    GetUser createOne(SaveUser userDto);
    GetUser updateOneById(Long id,SaveUser userDto);
    int updateByUsername(String username,SaveUser userDto);
    void deleteOneById(Long id);
    void deleteOneByUsername(String username);
}
