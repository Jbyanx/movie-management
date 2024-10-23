package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.entity.User;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.mapper.UserMapper;
import com.bycorp.moviemanagement.repository.UserRepository;
import com.bycorp.moviemanagement.services.UserService;
import com.bycorp.moviemanagement.services.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly=true)
    @Override
    public List<GetUser> findAll() {
        return UserMapper.toGetDtoList(userRepository.findAll());
    }

    @Transactional(readOnly=true)
    @Override
    public List<GetUser> findByNameContaining(String name) {
        return UserMapper.toGetDtoList(userRepository.findByNameContainingIgnoreCase(name));
    }

    @Transactional(readOnly=true)
    @Override
    public GetUser findByUsername(String username) {
        return UserMapper.toGetDto(this.findOneEntityByUsername(username));
    }

    @Transactional(readOnly=true)
    public User findOneEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Username: "+username+" not found"));
    }

    @Transactional(readOnly=true)
    @Override
    public GetUser findOneById(Long id) {
        return UserMapper.toGetDto(this.findOneEntityById(id));
    }

    @Transactional(readOnly=true)
    protected User findOneEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User :"+id+" not found"));
    }

    @Override
    public GetUser createOne(SaveUser userDto) {
        PasswordValidator.validatePassword(userDto.password(), userDto.passwordRepeated());

        User newUser = UserMapper.toEntity(userDto);
        return UserMapper.toGetDto(userRepository.save(newUser));
    }

    @Override
    public GetUser updateOneById(Long id, SaveUser saveUserDto) {
        PasswordValidator.validatePassword(saveUserDto.password(), saveUserDto.passwordRepeated());

        User oldUser = this.findOneEntityById(id);

        UserMapper.updateEntity(oldUser, saveUserDto);

        return UserMapper.toGetDto(userRepository.save(oldUser));
    }

    @Override
    public int updateByUsername(String username, SaveUser userDto) {
        PasswordValidator.validatePassword(userDto.password(), userDto.passwordRepeated());

        User oldUser = this.findOneEntityByUsername(username);

        UserMapper.updateEntity(oldUser, userDto);

        return userRepository.updateByUsername(username, oldUser);
    }

    @Override
    public void deleteOneById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteOneByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
