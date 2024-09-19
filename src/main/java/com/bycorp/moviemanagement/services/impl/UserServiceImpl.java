package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.entity.User;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.repository.UserRepository;
import com.bycorp.moviemanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly=true)
    @Override
    public List<User> findByNameContaining(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly=true)
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Username: "+username+" not found"));
    }

    @Transactional(readOnly=true)
    @Override
    public User findOneById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User :"+id+" not found"));
    }

    @Override
    public User createOne(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateOneById(Long id, User user) {
        User userBd = findOneById(id);

        userBd.setPassword(user.getPassword());
        userBd.setName(user.getName());
        userBd.setRatings(user.getRatings());

        return userRepository.save(userBd);
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
