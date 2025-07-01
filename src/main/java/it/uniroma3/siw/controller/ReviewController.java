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
public class ReviewController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CredentialsService credentialsService;

	/*
	@GetMapping("/book/{id}/review")
	public String showReviewForm(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		Review review = new Review();

		model.addAttribute("review", review);
		model.addAttribute("book", book);
		return "form/create/formNewReview.html";
	}

	@GetMapping("/review/{id}")
	public String getReviewById(@PathVariable Long id, Model model) {
		Review review = reviewService.getReviewById(id);
		model.addAttribute("review", review);
		return "detail/review";
	}

	@PostMapping("/book/{bookId}/newReview")
	public String saveReview(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("bookId") Long bookId, @ModelAttribute Review review) {
		Book book = bookService.getBookById(bookId);
		review.setBook(book);

		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		review.setUser(user);
		user.addReview(review);

		reviewService.saveReview(review);
		return "redirect:/book/" + bookId;
	}
	
	@PostMapping("/book/{bookId}/review/{reviewId}/delete")
	public String deleteReview(@PathVariable Long bookId, @PathVariable Long reviewId) {
	    reviewService.deleteById(reviewId);
	    return "redirect:/admin/book/" + bookId + "/update";
	}
	
	@GetMapping("/review/{id}/edit")
	public String editReview(@PathVariable Long id, Model model) {
		model.addAttribute("review", reviewService.getReviewById(id));
		return "form/update/formUpdateReview";
	}
	
	@PostMapping("/review")
	public String updateReview(@Valid @ModelAttribute("review") Review review,
						  BindingResult bindingResult,
			              @RequestParam("bookId") Long bookId,
			              @AuthenticationPrincipal UserDetails user,
			              Model model) {
	    if (bindingResult.hasErrors()) {
	        return "form/update/formUpdateReview";
	    }
	    review.setUser(credentialsService.getCredentials(user.getUsername()).getUser());
	    review.setBook(bookService.getBookById(bookId));
	    reviewService.saveReview(review);
	    return "redirect:/profile";
	}
	*/
}
