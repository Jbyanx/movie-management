package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Page<User> findAll(String name, Pageable pageable);

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = :#{#user.name}, u.password = :#{#user.password} WHERE u.username LIKE :username")
    Integer updateByUsername(@Param("username") String username, @Param("user") User user);

    @Transactional
    @Modifying
    void deleteByUsername(String username);
}
