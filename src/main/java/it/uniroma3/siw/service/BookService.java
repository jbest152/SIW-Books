package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

public class BookService {

	@Autowired
	private BookRepository repository;

	public Book getBookById(Long id) {
		return repository.findById(id).get();
	}

	public Iterable<Book> getAllBooks() {
		return repository.findAll();
	}

	public Book saveBook(Book book) {
		return repository.save(book);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public void addAuthorToBook(Long authorId, Long bookId) {
		repository.addAuthorToBook(authorId, bookId);
	}

}
