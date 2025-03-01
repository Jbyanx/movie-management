package com.bycorp.moviemanagement.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetUserStatistic(
    String username,
    LocalDateTime createdAt,
    int totalRatings,
    double averageRating,
    int lowestRating,
    int highestRating
) implements Serializable {
}
