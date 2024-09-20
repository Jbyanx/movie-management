package com.bycorp.moviemanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "movie_id",
            insertable = false, //este campo no sirve para guardar
            updatable = false   //este campo no sirve para actualizar
    )
    @JsonBackReference("movie-to-rating")
    private Movie movie;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            insertable = false, //este campo no sirve para guardar
            updatable = false   //este campo no sirve para actualizar
    )
    @JsonBackReference("user-to-rating")
    private User user;

    @Check(constraints = "rating >= 0 and rating <=5")
    @Column(nullable = false)
    private int rating;

    @Column(
            name = "movie_id",
            nullable = false
    )
    @JsonProperty(value = "movie-id")
    private Long movieId;

    @Column(
            name = "user_id",
            nullable = false
    )
    @JsonProperty(value = "user-id")
    private Long userId;
}
