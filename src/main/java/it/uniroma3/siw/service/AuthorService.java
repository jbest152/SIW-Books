package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;

@Service
public class AuthorService extends GenericService<Author, Long>{

	public long countAuthors() {
		return repository.count();
	}
	
	public List<Author> searchByNameOrSurname(String query) {
		AuthorRepository repository = (AuthorRepository) super.repository;
		return repository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(query, query);
	}
}
