package com.bycorp.moviemanagement.repository;

import com.bycorp.moviemanagement.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Page<Rating> findRatingsByMovieId(Long movieId, Pageable pageable);

    Page<Rating> findRatingsByUserUsername(String user_username, Pageable pageable);

    Boolean existsByMovieIdAndUserUsername(Long movieId, String username);

    /*CAOS DE USO PARA MOVIES*/
    @Query("select count(r) from Rating r where r.movieId = ?1")
    Integer countByMovieId(Long id);

    @Query("select avg(r.rating) from Rating r where r.movieId = ?1")
    Double avgRatingByMovieId(Long id);

    @Query("select min(r.rating) from Rating r where r.movieId = ?1")
    Integer minRatingByMovieId(Long id);

    @Query("select max(r.rating) from Rating r where r.movieId = ?1")
    Integer maxRatingByMovieId(Long id);

    /*CASOS DE USO PARA USERS*/

    @Query("select count(r) from Rating r where r.user.username = ?1")
    Integer countRatingsByUserUsername(String username);

    @Query("select avg(r.rating) from Rating r where r.user.username = ?1")
    Double avgRatingByUserUsername(String username);

    @Query("select min(r.rating) from Rating r where r.user.username = ?1")
    Integer minRatingByUserUsername(String username);

    @Query("select max(r.rating) from Rating r where r.user.username = ?1")
    Integer maxRatingByUserUsername(String username);

    @Query("select count(r) from Rating r where r.userId = ?1")
    Integer countRatingsByUserId(Long id);

    @Query("select avg(r.rating) from Rating r where r.userId = ?1")
    Double avgRatingByUserId(Long id);

    @Query("select min(r.rating) from Rating r where r.userId = ?1")
    Integer minRatingByUserId(Long id);

    @Query("select max(r.rating) from Rating r where r.userId = ?1")
    Integer maxRatingByUserId(Long id);
}
