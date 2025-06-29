package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.service.CredentialsService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	CredentialsService credentialsService;
	
	@GetMapping()
	public String profile(@AuthenticationPrincipal UserDetails user, Model model) {
		model.addAttribute("user", credentialsService.getCredentials(user.getUsername()).getUser());
		return "profile";
	}
	
	
}
