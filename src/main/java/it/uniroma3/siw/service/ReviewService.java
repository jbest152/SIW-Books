package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.repository.ReviewRepository;

public class ReviewService {
	
	@Autowired
	private ReviewRepository repository;

}
