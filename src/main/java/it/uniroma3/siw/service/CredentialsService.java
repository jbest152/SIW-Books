package it.uniroma3.siw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Role;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;

    public Credentials getCredentials(Long id) {
        return this.credentialsRepository.findById(id).get();
    }

    public Credentials getCredentials(String username) {
        return this.credentialsRepository.findByUsername(username).get();
    }

    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Role.USER);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
}
