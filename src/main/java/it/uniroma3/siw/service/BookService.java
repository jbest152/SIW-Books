package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

@Service
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

	public long countBooks() {
		return repository.count();
	}

	 public List<Book> findLatestBooks(int limit) {
		 return repository.findLatestBooks()
                         .stream()
                         .limit(limit)
                         .toList();
	 }

	/*
	public void addAuthorToBook(Long authorId, Long bookId) {
		repository.addAuthorToBook(authorId, bookId);
	}
	*/

}
