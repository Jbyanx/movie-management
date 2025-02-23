package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<GetUser> findAll(String name, Pageable pageable);
    GetUser findByUsername(String username);
    GetUser findOneById(Long id);
    GetUser createOne(SaveUser userDto);
    GetUser updateOneById(Long id,SaveUser userDto);
    Integer updateByUsername(String username,SaveUser userDto);
    void deleteOneById(Long id);
    void deleteOneByUsername(String username);
}
