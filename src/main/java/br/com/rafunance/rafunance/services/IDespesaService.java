package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Despesa;

import java.util.List;
import java.util.Optional;

public interface IDespesaService {
    List<Despesa> findAll();
    Optional<Despesa> findByID(Long id);
    Despesa save(Despesa obj);
    Despesa update(Despesa obj, Long id);
    void deleteById(Long id);
}
