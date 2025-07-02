package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;



@Controller
@RequestMapping("/book")
public class BookController extends GenericController<Book>{

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CredentialsService credentialsService;

	public BookController() {
		super(Book.class);
	}
	
	@Override
	@GetMapping("/{id}")
	public String view(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		boolean bool = false;
		if (userDetails != null) {
			User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
			bool = reviewService.existsByBookIdAndUserId(id, user.getId());
		}
		model.addAttribute("userHasReviewed", bool);
		return super.view(id, userDetails, model);
	}
	
	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		model.addAttribute("authors", authorService.findAll());
		return super.showEditForm(id, userDetails, model);
	}
	
	@Override
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		Book book = super.service.findById(id);
		for (Author a : book.getAuthors())
			a.getBooks().remove(book);
		service.deleteById(id);
		return "redirect:/book/edit";
	}

	@PostMapping("/{id}/addAuthor")
	public String addAuthorToBook(@PathVariable Long id, @RequestParam Long authorId) {
		Book book = super.service.findById(id);
		Author author = authorService.findById(authorId);

		author.addBook(book);
		authorService.save(author);

		return "redirect:/book/" + id + "/edit";
	}

	@PostMapping("/{id}/removeAuthor")
	public String removeAuthorFromBook(@PathVariable Long id, @RequestParam Long authorId) {
		Book book = super.service.findById(id);
		Author author = authorService.findById(authorId);

		author.removeBook(book);
		authorService.save(author);

		return "redirect:/book/" + id + "/edit";
	}
	
	@GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		System.out.println("query");
		addModelUser(model, userDetails);
		BookService bookService = (BookService) super.service;
        List<Book> results = bookService.searchByTitle(query);

        model.addAttribute("books", results);

        return "book/list";
    }
}
