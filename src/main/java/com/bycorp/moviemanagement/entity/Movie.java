package com.bycorp.moviemanagement.entity;

import com.bycorp.moviemanagement.utils.MovieGenre;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Enumerated(EnumType.STRING)
    private MovieGenre genre;

    @Column(name = "release_year")
    @JsonProperty(value = "release-year")
    private int releaseYear;

    @CreationTimestamp //genera de forma automatica esta vaina
    @Column(name = "created_at", updatable = false,  columnDefinition = "TIMESTAMP DEFAULT NOW()")//para colocar valores por def
    @JsonProperty(value = "created-at")
    @JsonFormat(pattern = "yyyy/MM/dd - HH:mm:ss")
    private LocalDateTime createdAt;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "movie",
            targetEntity = Rating.class
    )
    @JsonManagedReference("movie-to-rating")
    private List<Rating> ratings;
}
