package com.bycorp.moviemanagement.mapper;

import com.bycorp.moviemanagement.dto.request.SaveUser;
import com.bycorp.moviemanagement.dto.response.GetUser;
import com.bycorp.moviemanagement.dto.response.GetUserStatistic;
import com.bycorp.moviemanagement.entity.User;

import java.util.List;

public class UserMapper {

    public static GetUser toGetDto(User entity) {
        if(entity == null) return null;

        int totalRatedMovies = entity.getRatings() != null ? entity.getRatings().size() : 0;

        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                totalRatedMovies
        );
    }

    public static List<GetUser> toGetDtoList(List<User> entityList) {
        if(entityList == null) return null;

        return entityList.stream()
                .map(UserMapper::toGetDto)
                .toList();
    }

    public static User toEntity(SaveUser saveDto) {
        if (saveDto ==null) return null;

        User newUser = new User();
        newUser.setUsername(saveDto.username());
        newUser.setName(saveDto.name());
        newUser.setPassword(saveDto.password());

        return newUser;
    }

    public static void updateEntity(User oldUser, SaveUser userDto) {
        if(oldUser==null || userDto ==null) return;

        oldUser.setName(userDto.name());
        oldUser.setPassword(userDto.password());
    }

    public static GetUserStatistic toGetStatisticDto(
            User entity, int totalRatings, double averageRating, int lowestRating, int highestRating
    ) {
        if(entity == null) return null;

        return new GetUserStatistic(
                entity.getUsername(),
                entity.getCreatedAt(),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );
    }
}
