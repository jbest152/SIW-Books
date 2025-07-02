package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/review")
public class ReviewController extends GenericController<Review>{

	public ReviewController() {
		super(Review.class);
	}

	@Autowired
	private BookService bookService;

	@Autowired
	private CredentialsService credentialsService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/book/{id}/new")
	public String showReviewForm(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		super.addModelUser(model, userDetails);
		Book book = bookService.findById(id);

		Review review = new Review();
		review.setBook(book);

		model.addAttribute("item", review);
		return "review/create";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("item") Review review, @AuthenticationPrincipal UserDetails userDetails,BindingResult result) {
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		if (result.hasErrors()) {
			return  "review/create";
		}
		review.setUser(user);
		service.save(review);
		return "redirect:/review/" + review.getId();
	}
	
	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		Book book = service.findById(id).getBook();
		service.deleteById(id);
		return "redirect:/book/" + book.getId() + "/edit";
	}
}
