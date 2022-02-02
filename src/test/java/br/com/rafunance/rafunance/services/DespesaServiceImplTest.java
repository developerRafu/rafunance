package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.ConcurrentDespesaException;
import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.models.DespesaCategoria;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.repositories.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DespesaServiceImplTest {
    IDespesaService service;
    DespesaRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(DespesaRepository.class);
        service = new DespesaServiceImpl(repository);
    }

    @DisplayName("Save")
    @Nested
    class SaveTest {
        @DisplayName("Deve buscar despesas concorrentes")
        @Test
        void it_should_find_concurrent_despesas() {
            Despesa despesa = mockDespesa();
            despesa.setId(null);

            LocalDate dateAsFirstDayOfMonth = despesa.getData().withDayOfMonth(1);

            LocalDate dateAsLastDayOfMonth = dateAsFirstDayOfMonth.withDayOfMonth(dateAsFirstDayOfMonth.lengthOfMonth());

            service.save(despesa);

            verify(repository).findByDateRangeAndDescricaoAndId(
                    dateAsFirstDayOfMonth,
                    dateAsLastDayOfMonth,
                    despesa.getDescricao(),
                    despesa.getId());
        }

        @DisplayName("Deve lançar exceção caso exista despesa concorrente")
        @Test
        void it_should_throws_exception_if_exists_concurrent_despesa() {
            Despesa despesa = mockDespesa();
            despesa.setId(null);

            LocalDate dateAsFirstDayOfMonth = despesa.getData().withDayOfMonth(1);
            LocalDate dateAsLastDayOfMonth = dateAsFirstDayOfMonth.withDayOfMonth(dateAsFirstDayOfMonth.lengthOfMonth());

            given(repository.findByDateRangeAndDescricaoAndId(
                    dateAsFirstDayOfMonth,
                    dateAsLastDayOfMonth,
                    despesa.getDescricao(),
                    despesa.getId())
            ).willReturn(List.of(new Despesa()));

            assertThatThrownBy(() -> service.save(despesa))
                    .isInstanceOf(ConcurrentDespesaException.class);
        }
    }

    @DisplayName("Update")
    @Nested
    class UpdateTest {
        @DisplayName("Deve verificar se a entidade existe")
        @Test
        void it_should_verify_if_entity_exists() {
            Despesa despesa = mockDespesa();
            Long id = 1L;
            given(repository.findById(id)).willReturn(Optional.of(despesa));
            service.update(despesa, id);
            verify(repository).findById(id);
        }

        @DisplayName("Deve lançar exceção caso não encontre o objeto")
        @Test
        void it_should_throws_exception_if_not_found_object(){
            Despesa despesa = mockDespesa();
            Long id = 1L;
            given(repository.findById(id)).willReturn(Optional.empty());
            assertThatThrownBy(()->service.update(despesa, id))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    private static Despesa mockDespesa() {
        return new Despesa(1L, "Netflix", 65.0, LocalDate.now().withDayOfMonth(1), DespesaCategoria.LAZER);
    }
}
