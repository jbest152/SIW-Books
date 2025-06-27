package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;

@Controller
public class HomepageController {

	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;

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
		model.addAttribute("bookCount", bookService.countBooks());
		model.addAttribute("authorCount", authorService.countAuthors());
		model.addAttribute("latestBooks", bookService.findLatestBooks(5));
		if (userDetails == null)
			return "homepage";
		model.addAttribute("username", userDetails.getUsername());
        return "homepage";
    }
	
	@GetMapping("/home")
    public String home(Model model) {
        return "redirect:/";
    }
}
