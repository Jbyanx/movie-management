package com.bycorp.moviemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            unique = true,
            nullable = false
    )
    private String username;

    private String name;

    private String password;

    @OneToMany(
            fetch = FetchType.EAGER,
            targetEntity = Rating.class,
            mappedBy = "user"
    )
    private List<Rating> ratings;
}
