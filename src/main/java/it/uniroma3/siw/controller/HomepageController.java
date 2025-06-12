package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

	@GetMapping("/")
	public String home() {
		return "homepage"; // index.html nella cartella templates
	}

	@GetMapping("/login")
	public String login() {
		return "login"; // login.html nella cartella templates
	}

	@GetMapping("/register")
	public String register() {
		return "register"; // register.html nella cartella templates
	}

	@GetMapping("/bookIndex")
	public String homeBook(Model model) {
		return "bookIndex.html";
	}
	
	@GetMapping("/authorIndex")
	public String homeAuthor(Model model) {
		return "authorIndex.html";
	}
}
