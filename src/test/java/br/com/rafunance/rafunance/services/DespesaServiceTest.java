package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.ConcurrentDespesaException;
import br.com.rafunance.rafunance.models.DespesaCategoria;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.repositories.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DespesaServiceTest {
    IDespesaService service;

    @Mock
    DespesaRepository repository;

    @BeforeEach
    public void beforeAll() {
        service = new DespesaServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve ir ao repositório buscar despesa por descrição e range de data")
    void it_should_go_to_repository_find_despesa_by_descricao_and_date_range() {
        Despesa despesa = new Despesa(1L, "Netflix", 60.0, LocalDate.now(), DespesaCategoria.LAZER);
        service.findByDescricaoAndDateRange(despesa);
        LocalDate dateAsFirstDayOfMonth = despesa.getData().withDayOfMonth(1);
        LocalDate dateAsLastDayOfMonth = despesa.getData().withDayOfMonth(despesa.getData().lengthOfMonth());
        verify(repository).findByDescricaoAndDateRange(despesa.getDescricao(), dateAsFirstDayOfMonth, dateAsLastDayOfMonth);
    }

    @Test
    @DisplayName("Deve retornar um optional de Despesa")
    void it_should_return_an_optional_of_despesa() {
        Despesa despesa = getDespesaWithId();

        LocalDate dateAsFirstDayOfMonth = despesa.getData().withDayOfMonth(1);

        LocalDate dateAsLastDayOfMonth = despesa.getData().withDayOfMonth(despesa.getData().lengthOfMonth());

        given(repository.findByDescricaoAndDateRange(despesa.getDescricao(), dateAsFirstDayOfMonth, dateAsLastDayOfMonth))
                .willReturn(Optional.of(new Despesa()));

        Optional<Despesa> result = service.findByDescricaoAndDateRange(despesa);

        assertThat(result.get()).isNotNull();
    }

    @Test
    @DisplayName("Deve ir chamar o repositório")
    void it_should_call_repository() {
        Despesa despesa = getDespesaWithoutId();

        given(service.findByDescricaoAndDateRange(despesa)).willReturn(Optional.empty());

        service.save(despesa);

        verify(repository).save(despesa);
    }

    @Test
    @DisplayName("Deve salvar um objeto no banco")
    void it_should_save_an_object_at_database() {
        Despesa despesa = getDespesaWithoutId();

        given(service.findByDescricaoAndDateRange(despesa)).willReturn(Optional.empty());

        given(repository.save(despesa)).willReturn(getDespesaWithId());

        var result = service.save(despesa);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar exceção caso exista despesa concorrente")
    void it_should_throw_exception_if_exists_concurrent_despesa() {
        Despesa despesa = getDespesaWithoutId();

        given(service.findByDescricaoAndDateRange(despesa)).willReturn(Optional.of(getDespesaWithId()));

        assertThatThrownBy(() -> service.save(despesa))
                .isInstanceOf(ConcurrentDespesaException.class)
                .hasMessageContaining("Despesa já cadastrada");
    }

    private Despesa getDespesaWithId() {
        return new Despesa(1L, "Netflix", 60.0, LocalDate.now(), DespesaCategoria.LAZER);
    }

    private Despesa getDespesaWithoutId() {
        return new Despesa(null, "Netflix", 60.0, LocalDate.now(), DespesaCategoria.LAZER);
    }
}
