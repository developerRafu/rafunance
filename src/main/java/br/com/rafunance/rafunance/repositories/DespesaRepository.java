package br.com.rafunance.rafunance.repositories;

import br.com.rafunance.rafunance.models.DespesaCategoria;
import br.com.rafunance.rafunance.models.entities.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
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

    @Query("SELECT d FROM Despesa d WHERE 1=1 " +
            "AND (:id IS NULL OR d.id = :id) " +
            "AND (:desc IS NULL OR LOWER(d.descricao) LIKE(CONCAT('%',:desc,'%'))) " +
            "AND (:valor IS NULL OR d.valor = :valor) " +
            "AND (:data IS NULL OR d.data = :data) " +
            "AND (:categoria IS NULL OR d.categoria = :categoria)")
    Page<Despesa> findByFilter(
            @Param("id") Long id,
            @Param("desc") String descricao,
            @Param("data") LocalDate data,
            @Param("valor") Double valor,
            @Param("categoria") DespesaCategoria categoria,
            Pageable pageable
    );

    List<Despesa> findByDescricao(String desc);
}
