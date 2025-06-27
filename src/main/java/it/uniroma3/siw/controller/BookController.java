package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;
import jakarta.validation.Valid;



@Controller
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private ReviewService reviewRepository;
	@Autowired
	private CredentialsService credentialsService;



	@GetMapping("/book/{id}")
	public String getBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, Model model) {
		model.addAttribute("book", this.bookService.getBookById(id));
		if (userDetails!=null) {
			Credentials c = credentialsService.getCredentials(userDetails.getUsername());
			if (c != null) {
				User user = c.getUser();

				boolean userHasReviewed = reviewRepository.existsByBookIdAndUserId(id, user.getId());
				model.addAttribute("userHasReviewed", userHasReviewed);
			}
		}
		return "detail/book.html";
	}

	@GetMapping("/book")
	public String showAllBooks(Model model) {
		model.addAttribute("books", this.bookService.getAllBooks());
		return "list/books.html";
	}

	@GetMapping("admin/formNewBook")
	public String formNewBook(Model model) {
		model.addAttribute("book", new Book());
		return "form/create/formNewBook.html";
	}

	@PostMapping("/book")
	public String newBook(@Valid @ModelAttribute("book") Book book,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
			return "form/create/formNewBook.html";
		} 
		else {
			this.bookService.saveBook(book);
			return "redirect:/book/"+book.getId();
		}
	}


	@GetMapping("admin/updateBooks")
	public String updateBooks(Model model) {
		model.addAttribute("isAdmin", true);
		model.addAttribute("books", this.bookService.getAllBooks());
		return "list/books.html";
	}

	@GetMapping("admin/book/{id}/update")
	public String updateBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", this.bookService.getBookById(id));    
		model.addAttribute("authors", this.authorService.getAllAuthors());
		return "form/update/formUpdateBook.html";
	}

	@PostMapping("admin/book/{id}/delete")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteById(id);
		return "redirect:/admin/updateBooks";
	}

	@PostMapping("/book/{id}/addAuthor")
	public String addAuthorToBook(@PathVariable Long id, @RequestParam Long authorId) {
		Book book = bookService.getBookById(id);
		Author author = authorService.getAuthorById(authorId);

		author.addBook(book);
		authorService.saveAuthor(author);

		return "redirect:/admin/book/" + id + "/update";
	}


	@PostMapping("/book/{id}/removeAuthor")
	public String removeAuthorFromBook(@PathVariable Long id, @RequestParam Long authorId) {
		Book book = bookService.getBookById(id);
		Author author = authorService.getAuthorById(authorId);

		author.removeBook(book);
		authorService.saveAuthor(author);

		return "redirect:/admin/book/" + id + "/update";
	}


}
