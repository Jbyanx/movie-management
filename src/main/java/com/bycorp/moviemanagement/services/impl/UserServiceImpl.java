package com.bycorp.moviemanagement.services.impl;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.dto.response.GetUserStatistic;
import com.bycorp.moviemanagement.entity.User;
import com.bycorp.moviemanagement.exception.ObjectNotFoundException;
import com.bycorp.moviemanagement.mapper.UserMapper;
import com.bycorp.moviemanagement.repository.RatingRepository;
import com.bycorp.moviemanagement.repository.UserRepository;
import com.bycorp.moviemanagement.services.UserService;
import com.bycorp.moviemanagement.services.validator.PasswordValidator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional(readOnly=true)
    @Override
    public Page<GetUser> findAll(String name, Pageable pageable) {

        Specification<User> userSpecification = (root, query, builder) -> {
            if(StringUtils.hasText(name)) {
                Predicate nameLike = builder.like(root.get("name"), "%" + name + "%");
                return nameLike;
            }
            return null; //no mas predicados
        };

        Page<User> userPage = userRepository.findAll(userSpecification, pageable);

        return userPage.map(UserMapper::toGetDto);
    }

    @Transactional(readOnly=true)
    @Override
    public GetUserStatistic findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        int totalRatings = Optional.ofNullable(ratingRepository.countRatingsByUserUsername(username)).orElse(0);
        double averageRating = Optional.ofNullable(ratingRepository.avgRatingByUserUsername(username)).orElse(0.0);
        int lowestRating = Optional.ofNullable(ratingRepository.minRatingByUserUsername(username)).orElse(0);
        int highestRating = Optional.ofNullable(ratingRepository.maxRatingByUserUsername(username)).orElse(0);

        return UserMapper.toGetStatisticDto(
                this.findOneEntityByUsername(username),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );
    }

    @Transactional(readOnly=true)
    @Override
    public User findOneEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Username: "+username+" not found"));
    }

    @Transactional(readOnly=true)
    @Override
    public GetUserStatistic findOneById(Long id) {
        int totalRatings = Optional.ofNullable(ratingRepository.countRatingsByUserId(id)).orElse(0);
        double averageRating = Optional.ofNullable(ratingRepository.avgRatingByUserId(id)).orElse(0.0);
        int lowestRating = Optional.ofNullable(ratingRepository.minRatingByUserId(id)).orElse(0);
        int highestRating = Optional.ofNullable(ratingRepository.maxRatingByUserId(id)).orElse(0);

        return UserMapper.toGetStatisticDto(
                this.findOneEntityById(id),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );
    }

    @Transactional(readOnly=true)
    @Override
    public User findOneEntityById(Long id) {
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
