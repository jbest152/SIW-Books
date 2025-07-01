package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

@Service
public class BookService extends GenericService<Book, Long>{

	@Autowired
	private BookRepository repository;
	
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
