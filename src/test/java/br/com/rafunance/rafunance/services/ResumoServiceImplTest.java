package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.mocks.DespesaMockBuilder;
import br.com.rafunance.rafunance.mocks.ReceitaMockBuilder;
import br.com.rafunance.rafunance.models.dtos.Resumo;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.entities.Receita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResumoServiceImplTest {

    IResumoService service;
    IDespesaService despesaService;
    IReceitaService receitaService;

    @BeforeEach
    void setUp() {
        despesaService = mock(DespesaServiceImpl.class);
        receitaService = mock(ReceitaServiceImpl.class);
        service = new ResumoServiceImpl(despesaService, receitaService);
    }

    @DisplayName("getResumoByDateRange")
    @Nested
    class getResumoByDateRangeTest {

        @DisplayName("Deve chamar findByDateRange em despesaService")
        @Test
        void it_should_call_findByDateRange_in_despesaService() {
            LocalDate initialDate = LocalDate.now();
            LocalDate lastDate = LocalDate.now();
            service.getResumoByDateRange(initialDate, lastDate);
            verify(despesaService).findByDateRange(initialDate, lastDate);
        }

        @DisplayName("Deve chamar findByDateRange em receitaService")
        @Test
        void it_should_findByDateRange_in_receitaService() {
            LocalDate initialDate = LocalDate.now();
            LocalDate lastDate = LocalDate.now();
            service.getResumoByDateRange(initialDate, lastDate);
            verify(receitaService).findByDateRange(initialDate, lastDate);
        }

        @DisplayName("Deve calcular despesas do mês")
        @Test
        void it_should_calculate_despesas_of_month() {
            LocalDate initialDate = LocalDate.now();
            LocalDate lastDate = LocalDate.now();
            List<Despesa> depesasMock = List.of(DespesaMockBuilder.getMock());
            List<Receita> receitasMock = List.of(ReceitaMockBuilder.getMock());

            given(despesaService.findByDateRange(initialDate, lastDate)).willReturn(depesasMock);
            given(receitaService.findByDateRange(initialDate, lastDate)).willReturn(receitasMock);

            Resumo result = service.getResumoByDateRange(initialDate, lastDate);

            assertThat(result.getTotalDespeas()).isNotNull();
        }

        @DisplayName("Deve calcular receitas do mês")
        @Test
        void it_should_calculate_receitas_of_month() {
            LocalDate initialDate = LocalDate.now();
            LocalDate lastDate = LocalDate.now();
            List<Despesa> depesasMock = List.of(DespesaMockBuilder.getMock());
            List<Receita> receitasMock = List.of(ReceitaMockBuilder.getMock());

            given(despesaService.findByDateRange(initialDate, lastDate)).willReturn(depesasMock);
            given(receitaService.findByDateRange(initialDate, lastDate)).willReturn(receitasMock);

            Resumo result = service.getResumoByDateRange(initialDate, lastDate);

            assertThat(result.getTotalReceitas()).isNotNull();
        }

        @DisplayName("Deve calcular saldo do mês")
        @Test
        void it_should_calculate_saldo_of_month() {
            LocalDate initialDate = LocalDate.now();
            LocalDate lastDate = LocalDate.now();
            List<Despesa> depesasMock = List.of(DespesaMockBuilder.getMock());
            List<Receita> receitasMock = List.of(ReceitaMockBuilder.getMock());

            given(despesaService.findByDateRange(initialDate, lastDate)).willReturn(depesasMock);
            given(receitaService.findByDateRange(initialDate, lastDate)).willReturn(receitasMock);

            Resumo result = service.getResumoByDateRange(initialDate, lastDate);

            assertThat(result.getSaldo()).isNotNull();
        }

        @DisplayName("Deve calcular despesas por categoria do mês")
        @Test
        void it_should_calculate_despesas_by_categoria_of_month() {
            LocalDate initialDate = LocalDate.now();
            LocalDate lastDate = LocalDate.now();
            List<Despesa> depesasMock = List.of(DespesaMockBuilder.getMock());
            List<Receita> receitasMock = List.of(ReceitaMockBuilder.getMock());

            given(despesaService.findByDateRange(initialDate, lastDate)).willReturn(depesasMock);
            given(receitaService.findByDateRange(initialDate, lastDate)).willReturn(receitasMock);

            Resumo result = service.getResumoByDateRange(initialDate, lastDate);

            result.getDespesasPorCategoria().values().forEach(obj -> assertThat(obj).isNotNull());
        }
    }

}
