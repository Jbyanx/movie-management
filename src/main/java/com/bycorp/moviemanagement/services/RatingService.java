package com.bycorp.moviemanagement.services;

import com.bycorp.moviemanagement.dto.request.SaveRating;
import com.bycorp.moviemanagement.dto.response.GetCompleteRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {
    Page<GetCompleteRating> findAll(Pageable pageable);
    Page<GetCompleteRating> findRatingsByMovieId(Long movieId, Pageable pageable);
    Page<GetCompleteRating> findRatingsByUserUsername(String user_username, Pageable pageable);
    GetCompleteRating findOneById(Long id);
    GetCompleteRating createOne(SaveRating rating);
    GetCompleteRating updateOneById(Long id,SaveRating rating);
    void deleteOneById(Long id);
}
