package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.filters.BaseFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T save(T obj);
    T update(T obj);
    void deleteById(Long id);
    Page<T> findByFilter(BaseFilter filter);
}
