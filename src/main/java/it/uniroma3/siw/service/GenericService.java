package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.BaseEntity;

public class GenericService<T extends BaseEntity, ID> {

	@Autowired
    protected CrudRepository<T, ID> repository;

    public Iterable<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return repository.findById(id).get();
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}
