package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.ConcurrentReceitaException;
import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.repositories.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ReceitaServiceImplTest {

    IReceitaService service;
    ReceitaRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ReceitaRepository.class);
        service = new ReceitaServiceImpl(repository);
    }

    @DisplayName("Save")
    @Nested
    class SaveTest {

        @DisplayName("Deve chamar save do repositório")
        @Test
        void it_should_call_save_from_repository() {
            Receita receita = new Receita();
            service.save(receita);
            verify(repository).save(receita);
        }

        @DisplayName("Deve chamar findByDateRangeAndDescricao do repositório")
        @Test
        void it_should_call_findByDateRangeAndDescricao_from_repository() {
            Receita receita = mockReceita();

            service.save(receita);

            verify(repository).findByDateRangeAndDescricaoAndId(
                    any(LocalDate.class),
                    any(LocalDate.class),
                    anyString(),
                    anyLong()
            );
        }

        @DisplayName("Deve lançar exceção caso já exista receita no mês")
        @Test
        void it_should_throw_exception_if_exists_receita_in_the_month() {
            Receita receita = mockReceita();
            Long id = null;

            LocalDate dateAsFirstDayOfMonth = receita.getData().withDayOfMonth(1);
            LocalDate dateAsLastDayOfMonth = receita.getData().withDayOfMonth(receita.getData().lengthOfMonth());

            given(repository.findByDateRangeAndDescricaoAndId(
                    dateAsFirstDayOfMonth,
                    dateAsLastDayOfMonth,
                    receita.getDescricao(),
                    id))
                    .willReturn(List.of(receita));

            assertThatThrownBy(() -> service.save(receita))
                    .isInstanceOf(ConcurrentReceitaException.class);
        }

        @DisplayName("Deve salvar receita no banco de dados")
        @Test
        void it_should_save_receita_on_database() {
            Receita receitaToSave = mockReceita();
            receitaToSave.setId(null);

            given(repository.findByDateRangeAndDescricaoAndId(
                    any(LocalDate.class),
                    any(LocalDate.class),
                    anyString(),
                    anyLong()))
                    .willReturn(List.of());

            given(repository.save(receitaToSave)).willReturn(mockReceita());

            Receita result = service.save(receitaToSave);

            assertThat(result).isNotNull();
        }
    }

    @DisplayName("Update")
    @Nested
    class UpdateTest {
        @DisplayName("Deve lançar exceção caso não encontre entidade")
        @Test
        void it_should_throw_exception_if_not_found_entity() {
            Long id = 1L;

            Receita receita = mockReceita();

            given(service.findByID(id)).willReturn(Optional.empty());

            assertThatThrownBy(() -> service.update(receita, id))
                    .isInstanceOf(NotFoundException.class);
        }

        @DisplayName("Deve verificar se existe receitas concorrentes e lançar exceção se existir")
        @Test
        void it_should_verify_is_exists_concurrent_receitas_and_throws_exception_if_exists() {
            Receita receita = mockReceita();
            Long id = 1L;

            given(repository.findById(id)).willReturn(Optional.of(receita));

            given(repository.findByDateRangeAndDescricaoAndId(
                    any(LocalDate.class),
                    any(LocalDate.class),
                    anyString(),
                    anyLong()))
                    .willReturn(List.of(new Receita()));

            assertThatThrownBy(() -> service.update(receita, id))
                    .isInstanceOf(ConcurrentReceitaException.class);
        }

        @DisplayName("Deve atualizar entidade no banco de dados")
        @Test
        void it_should_update_entity_on_database() {
            Receita receita = mockReceita();

            Long id = 1L;

            given(repository.findById(id)).willReturn(Optional.of(receita));

            given(repository.findByDateRangeAndDescricaoAndId(
                    any(LocalDate.class),
                    any(LocalDate.class),
                    anyString(),
                    anyLong()))
                    .willReturn(List.of());

            given(repository.save(receita)).willReturn(new Receita());

            Receita result = service.update(receita, id);

            assertThat(result).isNotNull();
        }
    }

    @DisplayName("verifyIfExistsConcurrentReceita")
    @Nested
    class verifyIfExistsConcurrentReceitaTest {

        @DisplayName("Deve chamar o repositório")
        @Test
        void it_should_call_repository() {
            Receita receita = mockReceita();
            Long id = 1L;

            service.verifyIfExistsConcurrentReceita(receita, id);

            verify(repository).findByDateRangeAndDescricaoAndId(
                    any(LocalDate.class),
                    any(LocalDate.class),
                    anyString(),
                    anyLong())
            ;
        }

        @DisplayName("Deve lançar exceção caso encontre receitas")
        @Test
        void it_should_throw_exception_if_finds_receitas() {
            Receita receita = mockReceita();
            Long id = 1L;

            given(repository.findByDateRangeAndDescricaoAndId(
                    any(LocalDate.class),
                    any(LocalDate.class),
                    anyString(),
                    anyLong())
            ).willReturn(List.of(new Receita()));

            assertThatThrownBy(() -> service.verifyIfExistsConcurrentReceita(receita, id))
                    .isInstanceOf(ConcurrentReceitaException.class);
        }
    }

    private static Receita mockReceita() {
        return new Receita(1L, "Salário", 4000.0, LocalDate.now().withDayOfMonth(5));
    }
}
