package br.com.rafunance.rafunance.models.dtos;

import br.com.rafunance.rafunance.models.DespesaCategoria;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Resumo {

    private Double totalReceitas;
    private Double totalDespeas;
    private Double saldo;
    private Map<DespesaCategoria, Double> despesasPorCategoria;

}
