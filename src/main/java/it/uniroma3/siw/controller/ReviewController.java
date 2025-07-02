package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
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
	private ReviewService reviewService;

	@Autowired
	private CredentialsService credentialsService;

	@GetMapping("/book/{id}/new")
	public String showReviewForm(@PathVariable("id") Long id, Model model) {
		Book book = bookService.findById(id);

		Review review = new Review();
		review.setBook(book);

		model.addAttribute("item", review);
		return "review/create";
	}
	
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
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		Book book = service.findById(id).getBook();
		service.deleteById(id);
		return "redirect:/book/" + book.getId() + "/edit";
	}
}
