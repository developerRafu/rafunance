package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.filters.DespesaFilter;

import java.util.Optional;

public interface IDespesaService extends IBaseService<Despesa, DespesaFilter> {
    Optional<Despesa> findByDescricaoAndDateRange(Despesa despesa);
}
