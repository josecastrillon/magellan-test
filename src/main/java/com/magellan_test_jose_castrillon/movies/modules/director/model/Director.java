package com.magellan_test_jose_castrillon.movies.modules.director.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magellan_test_jose_castrillon.movies.modules.movie.model.Movie;

@Entity
@Table(name = "directors")

public class Director {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Director name is required")
	@Column(name = "name")
	private String name;

	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@Column(name = "deathday")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deathday;

	@Column(name = "place_of_birth")
	private String place_of_birth;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "directors")
	@JsonIgnore
	private Set<Movie> movies = new HashSet<>();

	
	
	public Director() {
		
	}

	public Director(@NotBlank(message = "Director name is required") String name, Date birthday, Date deathday,
			String place_of_birth) {

		this.name = name;
		this.birthday = birthday;
		this.deathday = deathday;
		this.place_of_birth = place_of_birth;

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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getDeathday() {
		return deathday;
	}

	public void setDeathday(Date deathday) {
		this.deathday = deathday;
	}

	public String getPlace_of_birth() {
		return place_of_birth;
	}

	public void setPlace_of_birth(String place_of_birth) {
		this.place_of_birth = place_of_birth;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	public void addMovie(Movie movie) {
		this.movies.add(movie);
		movie.getDirectors().add(this);
	}

	public void removeMovie(long movieId) {
		Movie movie = this.movies.stream().filter(t -> t.getId() == movieId).findFirst().orElse(null);
		if (movie != null) {
			this.movies.remove(movie);
			movie.getDirectors().remove(this);
		}
	}

	@Override
	public String toString() {
		return "Director [id=" + id + ", name=" + name + ", birthday=" + birthday + ", deathday=" + deathday
				+ ", place_of_birth=" + place_of_birth + "]";
	}

}
