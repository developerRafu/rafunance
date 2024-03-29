package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Receita;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IReceitaService {
    List<Receita> findAll();
    Optional<Receita> findByID(Long id);
    Receita save(Receita obj);
    Receita update(Receita obj, Long id);
    void deleteById(Long id);
    List<Receita> findByDesc(String desc);
    List<Receita> findByDateRange(LocalDate dateAsFirstDayOfMonth, LocalDate dateAsLastDateOfMonth);
    void verifyIfExistsConcurrentReceita(Receita obj, Long id);
}
