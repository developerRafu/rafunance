package br.com.rafunance.rafunance.repositories;

import br.com.rafunance.rafunance.models.entities.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query("SELECT d FROM Despesa d " +
            "WHERE 1=1 " +
            "AND d.descricao = :desc " +
            "AND d.data BETWEEN :initialDate AND :lastDate")
    Optional<Despesa> findByDescricaoAndDateRange(
            @Param("desc") String descricao,
            @Param("initialDate") LocalDate initialDate,
            @Param("lastDate") LocalDate lastDate
    );

}
