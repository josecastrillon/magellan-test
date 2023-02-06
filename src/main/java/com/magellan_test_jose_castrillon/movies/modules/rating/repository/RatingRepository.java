package com.magellan_test_jose_castrillon.movies.modules.rating.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.magellan_test_jose_castrillon.movies.modules.rating.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByMovieId(Long movieId);

	@Transactional
	void deleteByMovieId(long movieId);
}
