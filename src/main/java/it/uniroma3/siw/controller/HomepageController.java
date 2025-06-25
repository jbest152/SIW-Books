package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

	@GetMapping("admin/bookIndex")
	public String homeBook(Model model) {
		return "admin/bookIndex.html";
	}
	
	@GetMapping("admin/authorIndex")
	public String homeAuthor(Model model) {
		return "admin/authorIndex.html";
	}
}
