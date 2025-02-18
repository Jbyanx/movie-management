package com.bycorp.moviemanagement.repository.specification;

import com.bycorp.moviemanagement.dto.request.MovieSearchCriteria;
import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.entity.Rating;
import com.bycorp.moviemanagement.entity.User;
import com.bycorp.moviemanagement.utils.MovieGenre;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class FindAllMoviesSpecification implements Specification<Movie> {

    private MovieSearchCriteria movieSearchCriteria;

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
         //root = es la tabla principal en una secuencia, equivale al from Movie
         //query = criterios de la consulta en si misma
         //criteriaBuilder = permite construir los predicados y expresiones
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(this.movieSearchCriteria.title())) {//el titulo que envia el controlador es valido?
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%"+this.movieSearchCriteria.title()+"%");//crea el predicado
            predicates.add(titleLike);//agrega el predicado
        }
        if(movieSearchCriteria.genre() != null) {
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), movieSearchCriteria.genre());
            predicates.add(genreEqual);
        }
        if(movieSearchCriteria.minReleaseYear() != null && movieSearchCriteria.minReleaseYear().intValue() > 0) {
            //el anio minimo de lanzamiento es valido y mayor a 0
            //agrega movies con anio mayor o igual al enviado
            Predicate releaseYearGreatherThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), movieSearchCriteria.minReleaseYear());
            predicates.add(releaseYearGreatherThanEqual);
        }

        //maxReleaseYear
        if(movieSearchCriteria.maxReleaseYear() != null && movieSearchCriteria.maxReleaseYear().intValue() > 0){
            //el anio minimo de lanzamiento es valido y mayor a 0
            //agrega movies con anio menor o igual al enviado
            Predicate releaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), movieSearchCriteria.maxReleaseYear());
            predicates.add(releaseYearLessThanEqual);
        }

        //si el rating minimo es valido
        if(movieSearchCriteria.minAverageRating() != null && movieSearchCriteria.minAverageRating().intValue() > 0){
            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);
            Predicate averageRatingGreatherThanEqual;

            //la expresion es la subquery y el valor es el del dto de search criteria que envia el controlador
            averageRatingGreatherThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, movieSearchCriteria.minAverageRating().doubleValue());

            predicates.add(averageRatingGreatherThanEqual);
        }

        if(StringUtils.hasText(this.movieSearchCriteria.username())){

            Join<Movie, Rating> joinMovieRating = root.join("ratings");
            Join<Rating, User> joinRatingUser = joinMovieRating.join("user");

            Predicate usernameEqual = criteriaBuilder.equal(root.get("username"), this.movieSearchCriteria.username());
            
            predicates.add(usernameEqual);
        }

        //hacemos una lista abstracta de un array de predicate, todos separados por and
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private static Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate averageRatingGreatherThanEqual = null;

        //subquery para obtener el average rating de cada movie
        Subquery<Double> averageRatingSubquery = query.subquery(Double.class);
        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class);

        averageRatingSubquery.select(criteriaBuilder.avg(ratingRoot.get("rating")));

        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));
        averageRatingSubquery.where(movieIdEqual);
        return averageRatingSubquery;
    }

}
