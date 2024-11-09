package com.bycorp.moviemanagement.repository.specification;

import com.bycorp.moviemanagement.dto.request.MovieSearchCriteria;
import com.bycorp.moviemanagement.entity.Movie;
import com.bycorp.moviemanagement.entity.Rating;
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
         //root = from Movie
         //query = criterios de la consulta en si misma
         //criteriaBuilder = permite construir los predicados y expresiones
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(this.movieSearchCriteria.title())) {
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%"+this.movieSearchCriteria.title()+"%");
            predicates.add(titleLike);
        }
        if(movieSearchCriteria.genre() != null) {
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), movieSearchCriteria.genre());
            predicates.add(genreEqual);
        }
        if(movieSearchCriteria.minReleaseYear() != null && movieSearchCriteria.minReleaseYear().intValue() > 0) {
            Predicate releaseYearGreatherThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), movieSearchCriteria.minReleaseYear());
            predicates.add(releaseYearGreatherThanEqual);
        }
        if(movieSearchCriteria.maxReleaseYear() != null && movieSearchCriteria.maxReleaseYear().intValue()>0){
            Predicate releaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), movieSearchCriteria.maxReleaseYear());
            predicates.add(releaseYearLessThanEqual);
        }
        if(movieSearchCriteria.minAverageRating() != null && movieSearchCriteria.minAverageRating().intValue() > 0){
            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);
            Predicate averageRatingGreatherThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, movieSearchCriteria.minAverageRating().doubleValue());

            predicates.add(averageRatingGreatherThanEqual);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private static Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate averageRatingGreatherThanEqual = null;

        Subquery<Double> averageRatingSubquery = query.subquery(Double.class);//sub consulta que devuelve el promedio de ratings

        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class); //from rating
        averageRatingSubquery.select(criteriaBuilder.avg(ratingRoot.get("rating"))); //promedio de la columna rating

        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));

        averageRatingSubquery.where(movieIdEqual);
        return averageRatingSubquery;
    }

}
