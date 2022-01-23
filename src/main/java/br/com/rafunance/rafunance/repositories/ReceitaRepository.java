package br.com.rafunance.rafunance.repositories;

import br.com.rafunance.rafunance.models.entities.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
