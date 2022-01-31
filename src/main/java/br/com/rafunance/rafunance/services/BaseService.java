package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class BaseService<T, I> {
    protected JpaRepository<T, I> repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(I id) {
        return repository.findById(id);
    }

    public T save(T obj) {
        return repository.save(obj);
    }

    public T update(I id, T obj) {
        boolean thereIsObject = findById(id).isPresent();
        if (thereIsObject) {
            throw new NotFoundException("Objeto não encontrado");
        }
        return save(obj);
    }

    public void deleteById(I id) {
        repository.deleteById(id);
    }
}
