package com.magellan_test_jose_castrillon.movies.modules.rating.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magellan_test_jose_castrillon.movies.core.exception.ResourceNotFoundException;
import com.magellan_test_jose_castrillon.movies.modules.movie.repository.MovieRepository;
import com.magellan_test_jose_castrillon.movies.modules.rating.model.Rating;
import com.magellan_test_jose_castrillon.movies.modules.rating.repository.RatingRepository;

@RestController
@RequestMapping("/api")
public class RatingController {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private MovieRepository movieRepository;

	/***
	 * Get all rating by movie id
	 * 
	 * @param movieId
	 * @return
	 */
	@GetMapping("/movies/{movieId}/ratings")
	public ResponseEntity<List<Rating>> getAllRatingByMovieId(@PathVariable(value = "movieId") Long movieId) {
		if (!movieRepository.existsById(movieId)) {
			throw new ResourceNotFoundException("Not found Movie with id = " + movieId);
		}

		List<Rating> ratings = ratingRepository.findByMovieId(movieId);
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}

	/***
	 * Get rating by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/raiting/{id}")
	public ResponseEntity<Rating> getRatingById(@PathVariable(value = "id") Long id) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Rating with id = " + id));

		return new ResponseEntity<>(rating, HttpStatus.OK);
	}

	/***
	 * Create rating
	 * 
	 * @param movieId
	 * @param ratingRequest
	 * @return
	 */
	@PostMapping("/movies/{movieId}/ratings")
	public ResponseEntity<Rating> createRating(@PathVariable(value = "movieId") Long movieId,
			@RequestBody Rating ratingRequest) {
		Rating rating = movieRepository.findById(movieId).map(movie -> {
			ratingRequest.setMovie(movie);
			return ratingRepository.save(ratingRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Movie with id = " + movieId));

		return new ResponseEntity<>(rating, HttpStatus.CREATED);
	}

	/***
	 * Update rating
	 * 
	 * @param id
	 * @param ratingRequest
	 * @return
	 */
	@PutMapping("/ratings/{id}")
	public ResponseEntity<Rating> updateRating(@PathVariable("id") long id, @RequestBody Rating ratingRequest) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("RatingId " + id + "not found"));

		rating.setMovie(ratingRequest.getMovie());
		rating.setValue(ratingRequest.getValue());

		return new ResponseEntity<>(ratingRepository.save(rating), HttpStatus.OK);
	}

	/***
	 * Delete rating
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/ratings/{id}")
	public ResponseEntity<HttpStatus> deleteRating(@PathVariable("id") long id) {
		ratingRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/***
	 * Delete all ratings of movie
	 * 
	 * @param movieId
	 * @return
	 */
	@DeleteMapping("/movies/{movieId}/ratings")
	public ResponseEntity<List<Rating>> deleteAllRatingsOfMovie(@PathVariable(value = "movieId") Long movieId) {
		if (!movieRepository.existsById(movieId)) {
			throw new ResourceNotFoundException("Not found movie with id = " + movieId);
		}

		ratingRepository.deleteByMovieId(movieId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
