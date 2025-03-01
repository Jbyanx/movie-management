package com.bycorp.moviemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetUserStatistic(
    String username,
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm a")
    LocalDateTime createdAt,
    @JsonProperty("total_ratings")
    int totalRatings,
    @JsonProperty("average_rating")
    double averageRating,
    @JsonProperty("lowest_rating")
    int lowestRating,
    @JsonProperty("highest_rating")
    int highestRating
) implements Serializable {
}
