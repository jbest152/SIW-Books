package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

	@GetMapping("/bookIndex")
	public String homeBook(Model model) {
		return "bookIndex.html";
	}
	
	@GetMapping("/authorIndex")
	public String homeAuthor(Model model) {
		return "authorIndex.html";
	}
}
