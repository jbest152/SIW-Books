package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class ReviewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/book/{id}/review")
    public String showReviewForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);

        Review review = new Review();

        model.addAttribute("review", review);
        model.addAttribute("book", book);
        return "form/create/formNewReview.html";
    }


    @PostMapping("/book/{bookId}/newReview")
    public String saveReview(@PathVariable("bookId") Long bookId, @ModelAttribute Review review) {
        Book book = bookService.getBookById(bookId);
        review.setBook(book);

        reviewService.saveReview(review);
        return "redirect:/book/" + bookId;
    }

}
