package it.uniroma3.siw.controller;


import jakarta.validation.Valid;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.BaseEntity;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.GenericService;

public abstract class GenericController<T extends BaseEntity> {

	@Autowired
	protected GenericService<T, Long> service;
	
	@Autowired
	private CredentialsService credentialsService;
	
	private final String className;
	private final Class<T> clazz;

	public GenericController(Class<T> clazz) {
		this.clazz = clazz;
		this.className = this.clazz.getSimpleName().toLowerCase();
	}

	@GetMapping
	public String list(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		addModelUser(model, userDetails);
		model.addAttribute(className + "s", service.findAll());
		return className + "/list";
	}

	@GetMapping("/{id}")
	public String view(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		addModelUser(model, userDetails);
		T item = service.findById(id);

		model.addAttribute(className, item);
		return className + "/view";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/new")
	public String showCreateForm(Model model){
		try {
			model.addAttribute("item", getEntityInstance());
		} catch (Exception e) {
			return "redirect:/" + className;
		}
		return className + "/create";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute("item") T item, BindingResult result) {
		if (result.hasErrors()) {
			return className + "/create";
		}
		service.save(item);
		return "redirect:/" + className + "/" + item.getId();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/edit")
	public String updateItems(Model model) {
		model.addAttribute("isAdmin", true);
		model.addAttribute(className + "s", service.findAll());
		return className + "/list";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model) {
		T item = service.findById(id);

		model.addAttribute(className, item);
		return className + "/edit";
	}

	@PostMapping("/{id}")
	public String update(@Valid @ModelAttribute("item") T item, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors()) {
			return className + "/edit";
		}
		service.save(item);
		return "redirect:/" + className + "/" + id;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		service.deleteById(id);
		return "redirect:/" + className + "/edit";
	}

	private T getEntityInstance() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return clazz.getDeclaredConstructor().newInstance();
	}
	
	private void addModelUser(Model model, UserDetails userDetails) {
		User user = null;
		if (userDetails != null)
			user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		model.addAttribute("user", user);
	}
}

