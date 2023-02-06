package com.magellan_test_jose_castrillon.movies.modules.movie.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.magellan_test_jose_castrillon.movies.modules.director.model.Director;

@Entity
@Table(name = "movies", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "year"}, name = "UniqueColumnNameYearMovie")})
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Name is required")
	@Column(name = "name")
	private String name;

	/*
	 * This field can be other table in the database, at moment is only open field
	 */
	@NotBlank(message = "Genre is required")
	@Column(name = "genre")
	private String genre;

	@NotBlank(message = "Year of the movie is required")
	@Column(name = "year")
	private String year;

	@NotBlank(message = "Running time of the movie is required")
	@Column(name = "running_time")
	private String running_time;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })

	@JoinTable(name = "director_movie", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = {
			@JoinColumn(name = "director_id") })
	private Set<Director> directors = new HashSet<>();

	public Movie() {

	}

	public Movie(Long id, @NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Genre is required") String genre,
			@NotBlank(message = "Year of the movie is required") String year,
			@NotBlank(message = "Running time of the movie is required") String running_time, Set<Director> directors) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.year = year;
		this.running_time = running_time;
		this.directors = directors;
	}

	public Movie(@NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Genre is required") String genre,
			@NotBlank(message = "Year of the movie is required") String year,
			@NotBlank(message = "Running time of the movie is required") String running_time) {
		super();
		this.name = name;
		this.genre = genre;
		this.year = year;
		this.running_time = running_time;
	}

	public Movie(@NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Genre is required") String genre,
			@NotBlank(message = "Year of the movie is required") String year,
			@NotBlank(message = "Running time of the movie is required") String running_time, Set<Director> directors) {
		super();
		this.name = name;
		this.genre = genre;
		this.year = year;
		this.running_time = running_time;
		this.directors = directors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRunning_time() {
		return running_time;
	}

	public void setRunning_time(String running_time) {
		this.running_time = running_time;
	}

	public Set<Director> getDirectors() {
		return directors;
	}

	public void setDirectors(Set<Director> directors) {
		this.directors = directors;
	}

}
