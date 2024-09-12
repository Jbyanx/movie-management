package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContaining(String name);

    Optional<User> findByUsername(String username);

    @Modifying
    void deleteByUsername(String username);
}
