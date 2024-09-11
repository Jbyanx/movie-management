package com.bycorp.moviemanagement.entity;

import com.bycorp.moviemanagement.utils.MovieGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "peliculas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Enumerated(EnumType.STRING)
    private MovieGenre genre;

    @Column(name = "release_year")
    private int releaseYear;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "movie",
            targetEntity = Rating.class
    )
    private List<Rating> ratings;
}
