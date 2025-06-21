package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;



@Controller
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	

	
	@GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", this.bookService.getBookById(id));    
        return "book.html";
    }
	
	@GetMapping("/books")
	public String showAllBooks(Model model) {
		model.addAttribute("isAdmin", false);
		model.addAttribute("books", this.bookService.getAllBooks());
		return "books.html";
	}

	@GetMapping("/formNewBook")
	public String formNewBook(Model model) {
		model.addAttribute("book", new Book());
		return "formNewBook.html";
	}

	@PostMapping("/book")
	public String newBook(@Valid @ModelAttribute("book") Book book,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {         //Sono emersi errori nel binding
			model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
			return "formNewBook.html";
		} 
		else {
			this.bookService.saveBook(book);
			return "redirect:/book/"+book.getId();
		}
	}
	
	
	@GetMapping("/updateBooks")
	public String updateBooks(Model model) {
		model.addAttribute("isAdmin", true);
		model.addAttribute("books", this.bookService.getAllBooks());
		return "books.html";
	}
	
	@GetMapping("/book/{id}/update")
	 public String updateBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", this.bookService.getBookById(id));    
        model.addAttribute("authors", this.authorService.getAllAuthors());
        return "formUpdateBook.html";
    }
	
	@PostMapping("/book/{id}/delete")
	public String deleteBook(@PathVariable Long id) {
	    bookService.deleteById(id);
	    return "redirect:/updateBooks";
	}
	
	@PostMapping("/book/{id}/addAuthor")
	public String addAuthorToBook(@PathVariable Long id, @RequestParam Long authorId) {
	    Book book = bookService.getBookById(id);
	    Author author = authorService.getAuthorById(authorId);

	    author.addBook(book);
	    authorService.saveAuthor(author);

	    return "redirect:/book/" + id + "/update";
	}


	@PostMapping("/book/{id}/removeAuthor")
	public String removeAuthorFromBook(@PathVariable Long id, @RequestParam Long authorId) {
	    Book book = bookService.getBookById(id);
	    Author author = authorService.getAuthorById(authorId);

	    author.removeBook(book);
	    authorService.saveAuthor(author);

	    return "redirect:/book/" + id + "/update";
	}

	
}
