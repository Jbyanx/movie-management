package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.dto.request.SaveRating;
import com.bycorp.moviemanagement.dto.response.GetCompleteRating;
import com.bycorp.moviemanagement.dto.response.GetMovie;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.entity.Rating;
import com.bycorp.moviemanagement.entity.User;
import com.bycorp.moviemanagement.exception.DuplicateRatingException;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.mapper.RatingMapper;
import com.bycorp.moviemanagement.repository.RatingRepository;
import com.bycorp.moviemanagement.services.RatingService;
import com.bycorp.moviemanagement.services.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    private UserService userService;
    private EntityManager entityManager;


    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, UserService userService, EntityManager entityManager) {
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly=true)
    @Override
    public Page<GetCompleteRating> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable)
                .map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly=true)
    @Override
    public Page<GetMovie.GetRating> findRatingsByMovieId(Long movieId, Pageable pageable) {
        return ratingRepository.findRatingsByMovieId(movieId, pageable)
                .map(RatingMapper::toGetMovieRatingDto);
    }

    @Transactional(readOnly=true)
    @Override
    public Page<GetUser.GetRating> findRatingsByUserUsername(String userUsername, Pageable pageable) {
        return ratingRepository.findRatingsByUserUsername(userUsername, pageable)
                .map(RatingMapper::toGetUserRatingDto);
    }

    @Transactional(readOnly=true)
    @Override
    public GetCompleteRating findOneById(Long id) {
        return RatingMapper.toGetCompleteRatingDto(this.findOneEntityById(id));
    }

    @Override
    public GetCompleteRating createOne(SaveRating rating) {
        boolean ratingExist = ratingRepository.existsByMovieIdAndUserUsername(rating.movieId(), rating.username());

        System.out.println("rating eiste? "+ratingExist);
        if(ratingExist) throw new DuplicateRatingException(rating.username(), rating.movieId());

        User userEntity = userService.findOneEntityByUsername(rating.username());

        Rating entity = RatingMapper.toRatingEntity(rating, userEntity.getId());
        ratingRepository.save(entity);
        entityManager.detach(entity);



        return ratingRepository.findById(entity.getId())
                .map(RatingMapper::toGetCompleteRatingDto)
                .get();
    }

    @Transactional(readOnly=true)
    public Rating findOneEntityById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow( ()-> new ObjectNotFoundException("Rating with id " + Long.toString(id) + " not found" ) );
    }

    @Override
    public GetCompleteRating updateOneById(Long id, SaveRating rating) {
        Rating oldRating = this.findOneEntityById(id); //buscamos la entidad a actualizar

        User userEntity = userService.findOneEntityByUsername(rating.username());

        RatingMapper.updateEntity(oldRating, rating, userEntity.getId()); //la mandamos a actualizar

        return RatingMapper.toGetCompleteRatingDto(ratingRepository.save(oldRating)); //la guardamos y devolvemos dto
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
