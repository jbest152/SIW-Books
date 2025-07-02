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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/author")
public class AuthorController extends GenericController<Author> {


	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private BookService bookService;
	
	public AuthorController() {
		super(Author.class);
	}

	@Override
	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		model.addAttribute("books", bookService.findAll());
		return super.showEditForm(id, userDetails, model);
	}


	@PostMapping("/{id}/addBook")
	public String addBookToAuthor(@PathVariable Long id, @RequestParam Long bookId) {
		Author author = authorService.findById(id);

		author.addBook(bookService.findById(bookId));
		authorService.save(author);

		return "redirect:/author/" + id + "/edit";
	}

	@PostMapping("/{id}/removeBook")
	public String removeBookFromAuthor(@PathVariable Long id, @RequestParam Long bookId) {
		Author author = authorService.findById(id);

		author.removeBook(bookService.findById(bookId));
		authorService.save(author);

		return "redirect:/author/"+ id + "/edit";
	}
}
