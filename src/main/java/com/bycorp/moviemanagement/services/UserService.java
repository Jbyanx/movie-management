package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.dto.response.GetUserStatistic;
import com.bycorp.moviemanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<GetUser> findAll(String name, Pageable pageable);

    /**
     * @param username
     * @throws {@link com.bycorp.moviemanagement.exception.ObjectNotFoundException} si no encuentra el username en bbdd
     * @return
     */
    GetUserStatistic findByUsername(String username);

    GetUserStatistic findOneById(Long id);

    User findOneEntityById(Long id);
    /**
     * @param username
     * @throws {@link com.bycorp.moviemanagement.exception.ObjectNotFoundException} si no encuentra el username en bbdd
     * @return
     */
    User findOneEntityByUsername(String username);

    GetUser createOne(SaveUser userDto);
    GetUser updateOneById(Long id,SaveUser userDto);
    Integer updateByUsername(String username,SaveUser userDto);
    void deleteOneById(Long id);
    void deleteOneByUsername(String username);
}
