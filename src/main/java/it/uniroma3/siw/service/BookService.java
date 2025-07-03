package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.BookRepository;
import jakarta.transaction.Transactional;

@Service
public class BookService extends GenericService<Book, Long>{
	
	@Autowired
	ImageService imageService;
	
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

	 @Transactional
	 public void addImagesToBook(Long bookId, List<MultipartFile> files) {
	     Book book = super.findById(bookId);
	     for (MultipartFile file : files) {
	         if (!file.isEmpty()) {
	             try {
	                 Image img = new Image();
	                 img.setName(file.getOriginalFilename());
	                 img.setType(file.getContentType());
	                 img.setData(file.getBytes());
	                 book.getImages().add(img);
	             } catch (IOException e) {
	                 System.out.println("errore");
	             }
	         }
	     }
	     super.repository.save(book);
	 }
	 
	 @Transactional
	 public void removeImageFromBook(Long bookId, Long imageId) {
	     Book book = super.findById(bookId);
	     Image imageToRemove = null;

	     for (Image img : book.getImages()) {
	         if (img.getId().equals(imageId)) {
	             imageToRemove = img;
	             break;
	         }
	     }

	     if (imageToRemove != null) {
	         book.getImages().remove(imageToRemove);
	         imageService.deleteById(imageId);
	     }

	     super.save(book);
	 }
	 
	 @Override
	 public List<Book> findAll() {
		 BookRepository repository = (BookRepository) super.repository;
		 return repository.findAllByOrderByTitleAsc();
	 }
}
