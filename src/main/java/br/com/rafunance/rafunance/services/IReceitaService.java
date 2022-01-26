package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.models.filters.ReceitaFilter;

import java.time.LocalDate;
import java.util.List;

public interface IReceitaService extends IBaseService<Receita, ReceitaFilter> {
    List<Receita> findByDateRange(LocalDate dateAsFirstDayOfMonth, LocalDate dateAsLastDayOfMonth);
}
