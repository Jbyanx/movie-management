package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.entity.Rating;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.repository.RatingRepository;
import com.bycorp.moviemanagement.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> findRatingsByMovieId(Long movieId) {
        return ratingRepository.findRatingsByMovieId(movieId);
    }

    @Override
    public List<Rating> findRatingsByUserUsername(String user_username) {
        return ratingRepository.findRatingsByUserUsername(user_username);
    }

    @Override
    public Rating findOneById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow( ()-> new ObjectNotFoundException("Rating with id " + Long.toString(id) + " not found" ) );
    }

    @Override
    public Rating createOne(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateOneById(Long id, Rating rating) {
        Rating ratingDb = findOneById(id);
        ratingDb.setMovieId(rating.getMovieId());
        ratingDb.setUserId(rating.getUserId());
        return ratingRepository.save(ratingDb);
    }

    @Override
    public void deleteOneById(Long id) {
        if(ratingRepository.existsById(id)){
            ratingRepository.deleteById(id);
            return;
        }
        throw new ObjectNotFoundException("Rating with id " + Long.toString(id) + " not found");
    }
}
