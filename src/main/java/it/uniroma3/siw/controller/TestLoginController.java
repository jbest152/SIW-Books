package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TestLoginController {


	private void doLogin(String username, String role, HttpServletRequest request) {
	    var authorities = List.of(new SimpleGrantedAuthority(role));
	    User userDetails = new User(username, "", authorities);
	    var auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
	    
	    SecurityContextHolder.getContext().setAuthentication(auth);
	    
	    // salva il contesto nella sessione
	    HttpSession session = request.getSession(true);
	    session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	}

	@PostMapping("/loginAsUser")
	public String loginAsUser(HttpServletRequest request) {
	    doLogin("user", Role.USER.name(), request);
	    return "redirect:/home";
	}

	@PostMapping("/loginAsAdmin")
	public String loginAsAdmin(HttpServletRequest request) {
	    doLogin("admin", Role.ADMIN.name(), request);
	    return "redirect:/home";
	}
}
