package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    void deleteByUsername(String username);
}
