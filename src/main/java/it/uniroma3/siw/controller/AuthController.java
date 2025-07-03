package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;


@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "auth/register";
	}

	@PostMapping("/register")
    public String registerUser(
    			@Valid @ModelAttribute("user") User user,
                BindingResult userBindingResult, 
                @Valid @ModelAttribute("credentials") Credentials credentials,
                BindingResult credentialsBindingResult,
                Model model) {

		if (credentialsService.getCredentials(credentials.getUsername())!=null) {
			credentialsBindingResult.rejectValue("username", "error.credentials", "Username già esistente");
		}
		
        if(userBindingResult.hasErrors() || credentialsBindingResult.hasErrors()) {
        	return "auth/register";
        }
        
        userService.saveUser(user);
        credentials.setUser(user);
        credentialsService.saveCredentials(credentials);
        model.addAttribute("user", user);
        return "redirect:/login";
    }
	
	@GetMapping("/login") 
	public String login() {
		return "auth/login";
	}
		
    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        return "redirect:/home";
    }
    
    @GetMapping("/test") 
	public String test() {
		return "auth/testLogin";
	}
}