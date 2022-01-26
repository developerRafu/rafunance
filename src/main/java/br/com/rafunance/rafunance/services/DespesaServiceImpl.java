package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.ConcurrentDespesaException;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.models.filters.BaseFilter;
import br.com.rafunance.rafunance.models.filters.DespesaFilter;
import br.com.rafunance.rafunance.repositories.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DespesaServiceImpl implements IDespesaService {

    private final DespesaRepository repository;

    @Override
    public List<Despesa> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Despesa> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Despesa save(Despesa obj) {
        boolean thereIsAConcurrentDespesa = this.findByDescricaoAndDateRange(obj).isPresent();

        if (thereIsAConcurrentDespesa) {
            throw new ConcurrentDespesaException("Despesa já cadastrada");
        }

        return repository.save(obj);
    }

    @Override
    public Despesa update(Despesa obj) {
        return repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Despesa> findByFilter(DespesaFilter filter) {
        return repository.findByFilter(
                filter.getId(),
                filter.getDescricao(),
                filter.getData(),
                filter.getValor(),
                filter.getCategoria(),
                filter.getPageable()
        );
    }

    @Override
    public Optional<Despesa> findByDescricaoAndDateRange(Despesa despesa) {

        LocalDate dateDespesa = despesa.getData();

        LocalDate dateAsFirstDayOfMonth = dateDespesa.withDayOfMonth(1);
        LocalDate dateAsLastDayOfMonth = dateDespesa.withDayOfMonth(dateDespesa.lengthOfMonth());

        String descricaoReceita = despesa.getDescricao();

        return repository.findByDescricaoAndDateRange(descricaoReceita, dateAsFirstDayOfMonth, dateAsLastDayOfMonth);
    }
}
