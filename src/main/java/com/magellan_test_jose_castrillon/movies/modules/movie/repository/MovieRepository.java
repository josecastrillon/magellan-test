package com.magellan_test_jose_castrillon.movies.modules.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.magellan_test_jose_castrillon.movies.modules.movie.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	List<Movie> findMoviesByDirectorsId(Long tutorialId);

	List<Movie> findMoviesByDirectorsNameContaining(String Name);

	@Query("SELECT m from Rating r JOIN r.movie m  WHERE r.value >= :value GROUP BY m.id")
	List<Movie> findMoviesByGreaterValueRating(Integer value);

}
