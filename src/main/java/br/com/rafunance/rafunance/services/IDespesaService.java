package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Despesa;

import java.util.Optional;

public interface IDespesaService extends IBaseService<Despesa> {
    Optional<Despesa> findByDescricaoAndDateRange(Despesa despesa);
}
