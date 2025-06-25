package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
// import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

@Controller
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "form/auth/formRegisterUser";
	}

	@PostMapping("/register")
    public String registerUser(
    			@Valid @ModelAttribute("user") User user,
                BindingResult userBindingResult, 
                @Valid @ModelAttribute("credentials") Credentials credentials,
                BindingResult credentialsBindingResult,
                Model model) {

        if(userBindingResult.hasErrors() || credentialsBindingResult.hasErrors()) {
        	return "form/auth/formRegisterUser";
        }
        
        userService.saveUser(user);
        credentials.setUser(user);
        credentialsService.saveCredentials(credentials);
        model.addAttribute("user", user);
        return "redirect:/login";
    }
	
	@GetMapping("/login") 
	public String login() {
		return "form/auth/formLogin";
	}

	@GetMapping("/") 
    public String home(Model model) {
        return "homepage";
    }
	
	@GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		if (userDetails == null)
			return "redirect:/";
		boolean isAdmin = userDetails.getAuthorities().stream()
			    .anyMatch(auth -> auth.getAuthority().equals(ADMIN_ROLE));
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("isAdmin", isAdmin);
        return "homepage";
    }
		
    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        return "redirect:/home";
    }
}