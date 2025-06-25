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
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;

@Controller
public class AuthorController {

	private final UserRepository userRepository;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;

	AuthorController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/author/{id}")
	public String getAuthor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("author", this.authorService.getAuthorById(id));    
		return "author.html";
	}

	@GetMapping("/authors")
	public String showAllAuthors(Model model) {
		model.addAttribute("isAdmin", false);
		model.addAttribute("authors", this.authorService.getAllAuthors());
		return "list/authors.html";
	}

	@GetMapping("/formNewAuthor")
	public String formNewAuthor(Model model) {
		model.addAttribute("author", new Author());
		return "formNewAuthor.html";
	}

	@GetMapping("/updateAuthors")
	public String updateAuthors(Model model) {
		model.addAttribute("isAdmin", true);
		model.addAttribute("authors", this.authorService.getAllAuthors());
		return "list/authors.html";
	}

	@GetMapping("/author/{id}/update")
	public String updateBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("author", this.authorService.getAuthorById(id));    
		model.addAttribute("books", this.bookService.getAllBooks());
		return "form/formUpdateAuthor.html";
	}


	@PostMapping("/author")
	public String createOrUpdateAuthor(@Valid @ModelAttribute("author") Author author,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
			return "form/formNewAuthor.html";
		} 

		if (author.getId() != null) {
			Author temp = authorService.getAuthorById(author.getId());
			author.setBooks(temp.getBooks());
		}
		this.authorService.saveAuthor(author);
		model.addAttribute("author", author);
		return "redirect:/author/"+author.getId();

	}

	@PostMapping("/author/{id}/delete")
	public String deleteAuthor(@PathVariable Long id) {
		authorService.deleteById(id);
		return "redirect:/updateAuthors";
	}

	@PostMapping("/author/{id}/addBook")
	public String addBookToAuthor(@PathVariable Long id, @RequestParam Long bookId) {
		Author author = authorService.getAuthorById(id);

		author.addBook(bookService.getBookById(bookId));
		authorService.saveAuthor(author);

		return "redirect:/author/" + id + "/update";
	}

	@PostMapping("/author/{id}/removeBook")
	public String removeBookFromAuthor(@PathVariable Long id, @RequestParam Long bookId) {
		Author author = authorService.getAuthorById(id);

		author.removeBook(bookService.getBookById(bookId));
		authorService.saveAuthor(author);

		return "redirect:/author/"+ id + "/update";
	}


}
