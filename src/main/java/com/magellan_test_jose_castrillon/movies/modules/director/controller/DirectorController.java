package com.magellan_test_jose_castrillon.movies.modules.director.controller;

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

@RestController
@RequestMapping("/api")
public class DirectorController {

	@Autowired
	DirectorRepository directorRepository;

	/***
	 * Get all directors optional search by id.
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/directors")
	public ResponseEntity<List<Director>> getAllDirectors(@RequestParam(required = false) String name) {
		List<Director> directors = new ArrayList<Director>();

		if (name == null)
			directorRepository.findAll().forEach(directors::add);
		else
			directorRepository.findByNameContaining(name).forEach(directors::add);

		if (directors.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(directors, HttpStatus.OK);
	}

	/***
	 * Get director by Id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/director/{id}")
	public ResponseEntity<Director> getDirectorById(@PathVariable("id") long id) {
		Director director = directorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found director with id = " + id));

		return new ResponseEntity<>(director, HttpStatus.OK);
	}

	/***
	 * Create a director on database.
	 * 
	 * @param director
	 * @return
	 */
	@PostMapping("/director")
	public ResponseEntity<Director> createDirector(@RequestBody Director director) {
		Director _director = directorRepository.save(new Director(director.getName(), director.getBirthday(),
				director.getDeathday(), director.getPlace_of_birth()));
		return new ResponseEntity<>(_director, HttpStatus.CREATED);
	}

	/***
	 * 
	 * @param id
	 * @param director
	 * @return
	 */
	@PutMapping("/directors/{id}")
	public ResponseEntity<Director> updateDirector(@PathVariable("id") long id, @RequestBody Director director) {
		Director _director = directorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Director with id = " + id));

		_director.setName(director.getName());
		_director.setBirthday(director.getBirthday());
		_director.setDeathday(director.getDeathday());
		_director.setPlace_of_birth(director.getPlace_of_birth());

		return new ResponseEntity<>(directorRepository.save(_director), HttpStatus.OK);
	}

	/***
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/directors/{id}")
	public ResponseEntity<HttpStatus> deleteDirector(@PathVariable("id") long id) {
		directorRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/***
	 * 
	 * @return
	 */
	@DeleteMapping("/directors")
	public ResponseEntity<HttpStatus> deleteAllDirectors() {
		directorRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
