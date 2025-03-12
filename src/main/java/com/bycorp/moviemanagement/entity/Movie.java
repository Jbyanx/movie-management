package com.bycorp.moviemanagement.entity;

import com.bycorp.moviemanagement.utils.MovieGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Enumerated(EnumType.STRING)
    private MovieGenre genre;

    @Column(name = "release_year")
    private int releaseYear;

    @CreationTimestamp //genera de forma automatica esta vaina
    @Column(name = "created_at", updatable = false,  columnDefinition = "TIMESTAMP DEFAULT NOW()")//para colocar valores por def
    private LocalDateTime createdAt;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "movie",
            targetEntity = Rating.class,
            cascade = {CascadeType.REMOVE}
    )
    private List<Rating> ratings;
}
