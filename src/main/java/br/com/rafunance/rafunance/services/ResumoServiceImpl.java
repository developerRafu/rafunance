package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.DespesaCategoria;
import br.com.rafunance.rafunance.models.dtos.Resumo;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.entities.Receita;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ResumoServiceImpl implements IResumoService {

    private final IDespesaService despesaService;
    private final IReceitaService receitaService;

    @Override
    public Resumo getResumoByDateRange(LocalDate initialDate, LocalDate lastDate) {

        List<Despesa> despesasByMonth = despesaService.findByDateRange(initialDate, lastDate);
        List<Receita> receitasByMonth = receitaService.findByDateRange(initialDate, lastDate);

        Double calculatedDespesas = this.calculateDespesas(despesasByMonth);

        Double calculatedReceitas = this.calculateReceitas(receitasByMonth);

        Double saldo = this.calculateSaldo(calculatedReceitas, calculatedDespesas);

        Map<DespesaCategoria, Double> despesaPorCategoria = this.calculateDespesaPorCategoria(despesasByMonth);

        Resumo resumo = new Resumo();
        resumo.setTotalDespeas(calculatedDespesas);
        resumo.setTotalReceitas(calculatedReceitas);
        resumo.setSaldo(saldo);
        resumo.setDespesasPorCategoria(despesaPorCategoria);

        return resumo;
    }

    private Double calculateDespesas(List<Despesa> despesas) {
        return despesas.stream()
                .map(Despesa::getValor)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    private Double calculateReceitas(List<Receita> receitas) {
        return receitas.stream()
                .map(Receita::getValor)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    private Double calculateSaldo(Double valorReceita, Double valorDespesa) {
        return Double.min(valorReceita, valorDespesa);
    }

    private Map<DespesaCategoria, Double> calculateDespesaPorCategoria(List<Despesa> despesasByMonth) {
        Map<DespesaCategoria, Double> map = new EnumMap<>(DespesaCategoria.class);
        despesasByMonth.forEach(obj -> map.put(obj.getCategoria(), 0.0));
        despesasByMonth.forEach(obj -> map.put(obj.getCategoria(), (map.get(obj.getCategoria()) + obj.getValor())));
        return map;
    }
}
