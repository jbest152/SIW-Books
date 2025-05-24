package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.repository.UserRepository;

public class UserService {
	
	@Autowired
	private UserRepository repository;

}
