package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

@Controller
public class HomeController {

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private CredentialsService credentialsService;

	@GetMapping("admin/bookIndex")
	public String homeBook(Model model) {
		return "admin/bookIndex.html";
	}
	
	@GetMapping("admin/authorIndex")
	public String homeAuthor(Model model) {
		return "admin/authorIndex.html";
	}
	
	@GetMapping("/") 
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User user = null;
		boolean isAdmin = false;
		if (userDetails != null) {
			Credentials c = credentialsService.getCredentials(userDetails.getUsername());
			user = c.getUser();
			isAdmin = c.getRole().equals(ADMIN_ROLE);
		}
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("user", user);
		model.addAttribute("bookCount", bookService.countBooks());
		model.addAttribute("authorCount", authorService.countAuthors());
		model.addAttribute("latestBooks", bookService.findLatestBooks(5));
        return "home";
    }
	
	@GetMapping("/home")
    public String home(Model model) {
        return "redirect:/";
    }
}
