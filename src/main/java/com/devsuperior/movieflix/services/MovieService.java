package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Movie not found."));
		return new MovieDetailsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<MovieDetailsDTO> findByGenre(String genreIdInp, Pageable pageable) {
		Long genreId = 0L;
		if(!"0".equals(genreIdInp)){
			genreId = Long.parseLong(genreIdInp);
		}
		Page<Movie> page = repository.findMoviesByGenre_Id(genreId, pageable);
		return page.map(MovieDetailsDTO::new);
	}
}
