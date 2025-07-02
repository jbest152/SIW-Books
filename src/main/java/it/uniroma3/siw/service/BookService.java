package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

@Service
public class BookService extends GenericService<Book, Long>{
	
	public long countBooks() {
		return super.repository.count();
	}

	 public List<Book> findLatestBooks(int limit) {
		 BookRepository repository = (BookRepository) super.repository;
		 return repository.findLatestBooks()
                         .stream()
                         .limit(limit)
                         .toList();
	 }
	 
	 public List<Book> searchByTitle(String query) {
		 BookRepository repository = (BookRepository) super.repository;
		 return repository.findByTitleContainingIgnoreCase(query);
	 }
}
