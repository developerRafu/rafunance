package br.com.rafunance.rafunance.repositories;

import br.com.rafunance.rafunance.models.entities.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
