package br.com.rafunance.rafunance.repositories;

import br.com.rafunance.rafunance.models.entities.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query("SELECT r FROM Receita r WHERE 1=1 " +
            "AND (:id IS NULL OR r.id = :id) " +
            "AND (:desc IS NULL OR LOWER(r.descricao) LIKE(CONCAT('%',:desc,'%'))) " +
            "AND (:valor IS NULL OR r.valor = :valor) " +
            "AND (:data IS NULL OR r.data = :data)")
    Page<Receita> findByFilter(
            @Param("id") Long id,
            @Param("desc") String descricao,
            @Param("data") LocalDate data,
            @Param("valor") Double valor,
            Pageable pageable
    );

    @Query("SELECT r FROM Receita r WHERE r.data BETWEEN :initialDate AND :lastDate")
    List<Receita> findByDateRange(
            @Param("initialDate") LocalDate initialDate,
            @Param("lastDate") LocalDate lastDate
    );

    List<Receita> findByDescricao(String desc);

    @Query("SELECT r FROM Receita r WHERE r.data BETWEEN :initialDate AND :lastDate AND r.descricao = :desc")
    List<Receita> findByDateRangeAndDescricao(
            @Param("initialDate") LocalDate initialDate,
            @Param("lastDate") LocalDate lastDate,
            @Param("desc") String desc
    );
}
