package com.bycorp.moviemanagement.entities;

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
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "movie_id",
            insertable = false,
            updatable = false
    )
    private Movie movie;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            insertable = false,
            updatable = false
    )
    private User user;

    @Check(constraints = "rating >= 0 and rating <=5")
    @Column(nullable = false)
    private int rating;
}
