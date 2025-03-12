package com.bycorp.moviemanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
            mappedBy = "user",
            cascade = {CascadeType.REMOVE} //si elimino un usuario se eliminan sus ratings
    )
    private List<Rating> ratings;

    @CreationTimestamp //genera de forma automatica esta vaina
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;
}
