package com.bycorp.moviemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Entity
@Table(
        name = "ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movie_id","user_id"})
) //la anotacion @Table entiende en terminos de sql a los nombres de los parametros
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
            insertable = false, //este campo no sirve para guardar
            updatable = false   //este campo no sirve para actualizar
    )
    private Movie movie;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            insertable = false, //este campo no sirve para guardar
            updatable = false   //este campo no sirve para actualizar
    )
    private User user;

    @Check(constraints = "rating >= 0 and rating <=5")
    @Column(nullable = false)
    private int rating;

    @Column(
            name = "movie_id",
            nullable = false
    )
    private Long movieId;

    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;
}
