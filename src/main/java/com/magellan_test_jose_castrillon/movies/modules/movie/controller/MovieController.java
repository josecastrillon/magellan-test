package com.magellan_test_jose_castrillon.movies.modules.movie.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magellan_test_jose_castrillon.movies.core.exception.ResourceNotFoundException;
import com.magellan_test_jose_castrillon.movies.modules.director.model.Director;
import com.magellan_test_jose_castrillon.movies.modules.director.repository.DirectorRepository;
import com.magellan_test_jose_castrillon.movies.modules.movie.model.Movie;
import com.magellan_test_jose_castrillon.movies.modules.movie.repository.MovieRepository;

@RestController
@RequestMapping("/api")
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private DirectorRepository directorRepository;

	/***
	 * Get all movies
	 * 
	 * @return
	 */
	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		List<Movie> movies = new ArrayList<Movie>();

		movieRepository.findAll().forEach(movies::add);

		if (movies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	/***
	 * Get all movies by greater rating
	 * 
	 * @param value
	 * @return
	 */

	@GetMapping("/movies/greaterRating")
	public ResponseEntity<List<Movie>> getAllMoviesBYGreaterRating(@RequestParam(required = true) Integer value) {
		List<Movie> movies = new ArrayList<Movie>();

		movieRepository.findMoviesByGreaterValueRating(value).forEach(movies::add);

		if (movies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	/***
	 * Get all movies by director
	 * 
	 * @param directorId
	 * @return
	 */
	@GetMapping("/directors/{directorId}/movies")
	public ResponseEntity<List<Movie>> getAllMoviesByDirectorId(@PathVariable(value = "directorId") Long directorId) {
		if (!directorRepository.existsById(directorId)) {
			throw new ResourceNotFoundException("Not found Director with id = " + directorId);
		}

		List<Movie> movies = movieRepository.findMoviesByDirectorsId(directorId);
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	/***
	 * Get all movies by director name • Search movies by Director
	 * 
	 * @param directorName
	 * @return
	 */

	@GetMapping("/directors/{directorName}/movie")
	public ResponseEntity<List<Movie>> getAllMoviesByDirectorName(
			@PathVariable(value = "directorName") String directorName) {

		List<Movie> movies = movieRepository.findMoviesByDirectorsNameContaining(directorName);
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	/***
	 * Get movie by Id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/movies/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long id) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found movie with id = " + id));

		return new ResponseEntity<>(movie, HttpStatus.OK);
	}

	/***
	 * 
	 * 
	 * @param movieId
	 * @return
	 */
	@GetMapping("/movies/{movieID}/directors")
	public ResponseEntity<List<Director>> getAllDirectorsByMovieId(@PathVariable(value = "movieId") Long movieId) {
		if (!movieRepository.existsById(movieId)) {
			throw new ResourceNotFoundException("Not found movie  with id = " + movieId);
		}

		List<Director> directors = directorRepository.findDirectorsByMoviesId(movieId);
		return new ResponseEntity<>(directors, HttpStatus.OK);
	}

	/***
	 * Add movie.
	 * 
	 * @param directorId
	 * @param movieRequest
	 * @return
	 */
	@PostMapping("/directors/{directorId}/movies")
	public ResponseEntity<Movie> addMovie(@PathVariable(value = "directorId") Long directorId,
			@RequestBody Movie movieRequest) {

		Movie movie = directorRepository.findById(directorId).map(director -> {

			if (movieRequest.getId() != null) {
				Long movieId = movieRequest.getId();

				// movie is existed
				if (movieId != 0L) {
					Movie _movie = movieRepository.findById(movieId)
							.orElseThrow(() -> new ResourceNotFoundException("Not found Movie with id = " + movieId));
					director.addMovie(_movie);
					directorRepository.save(director);
					return _movie;
				}
			}
			// add and create new movie
			director.addMovie(movieRequest);
			return movieRepository.save(movieRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Director with id = " + directorId));

		return new ResponseEntity<>(movie, HttpStatus.CREATED);
	}

	/***
	 * Update movie
	 * 
	 * @param id
	 * @param movieRequest
	 * @return
	 */
	@PutMapping("/movies/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable("id") long id, @RequestBody Movie movieRequest) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MovieId " + id + "not found"));

		movie.setName(movieRequest.getName());
		movie.setGenre(movieRequest.getGenre());
		movie.setRunning_time(movieRequest.getRunning_time());
		movie.setYear(movieRequest.getYear());

		return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.OK);
	}

	/***
	 * Delete movie from director
	 * 
	 * @param directorId
	 * @param movieId
	 * @return
	 */
	@DeleteMapping("/directors/{directorId}/movies/{movieId}")
	public ResponseEntity<HttpStatus> deleteMovieFromDirector(@PathVariable(value = "directorId") Long directorId,
			@PathVariable(value = "movieId") Long movieId) {
		Director director = directorRepository.findById(directorId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Director with id = " + directorId));

		director.removeMovie(movieId);
		directorRepository.save(director);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/***
	 * delete movie by Id.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") long id) {
		movieRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
