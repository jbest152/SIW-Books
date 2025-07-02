package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;

@Service
public class AuthorService extends GenericService<Author, Long>{

	@Autowired
	private AuthorRepository repository;

	public long countAuthors() {
		return repository.count();
	}

	/*
	public void addBookToAuthor(Long bookId, Long authorId) {
		repository.addBookToAuthor(bookId, authorId);
	}
	*/
}
