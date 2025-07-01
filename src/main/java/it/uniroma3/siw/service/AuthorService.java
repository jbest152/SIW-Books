package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;

@Service
public class AuthorService extends GenericService<Author, Long>{

	@Autowired
	private AuthorRepository repository;
	
	public Author getAuthorById(Long id) {
		return repository.findById(id).get();
	}

	public Iterable<Author> getAllAuthors() {
		return repository.findAll();
	}

	public Author saveAuthor(Author Author) {
		return repository.save(Author);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public long countAuthors() {
		return repository.count();
	}

	/*
	public void addBookToAuthor(Long bookId, Long authorId) {
		repository.addBookToAuthor(bookId, authorId);
	}
	*/
}
