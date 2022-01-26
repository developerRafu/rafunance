package br.com.rafunance.rafunance.models.filters;

import br.com.rafunance.rafunance.models.DespesaCategoria;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
public class DespesaFilter extends BaseFilter {
    private DespesaCategoria categoria;

    public DespesaFilter(Long id, String descricao, Double valor, LocalDate data, Pageable pageable, DespesaCategoria categoria) {
        super(id, descricao, valor, data, pageable);
        this.categoria = categoria;
    }
}
