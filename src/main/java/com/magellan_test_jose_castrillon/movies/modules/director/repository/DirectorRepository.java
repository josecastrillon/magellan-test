package com.magellan_test_jose_castrillon.movies.modules.director.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.magellan_test_jose_castrillon.movies.modules.director.model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
	Optional<Director> findById(Long id);

	List<Director> findByNameContaining(String name);

	List<Director> findDirectorsByMoviesId(Long movieId);

}
