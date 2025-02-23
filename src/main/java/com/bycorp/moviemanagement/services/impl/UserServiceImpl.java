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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Page<GetUser> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserMapper::toGetDto);
    }

    @Transactional(readOnly=true)
    @Override
    public Page<GetUser> findByNameContaining(String name, Pageable pageable) {
        Page<User> userPage = userRepository.findByNameContainingIgnoreCase(name);
        return userPage.map(UserMapper::toGetDto);
    }

    @Transactional(readOnly=true)
    @Override
    public GetUser findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
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
    public Integer updateByUsername(String username, SaveUser userDto) {
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
