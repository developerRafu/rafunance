package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.models.filters.ReceitaFilter;
import br.com.rafunance.rafunance.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReceitaServiceImpl implements IReceitaService {

    @Autowired
    private ReceitaRepository repository;

    @Override
    public List<Receita> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Receita> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Receita save(Receita obj) {
        return repository.save(obj);
    }

    @Override
    public Receita update(Receita obj) {
        return repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Receita> findByFilter(ReceitaFilter filter) {
        return repository.findByFilter(
                filter.getId(),
                filter.getDescricao(),
                filter.getData(),
                filter.getValor(),
                filter.getPageable()
        );
    }

    @Override
    public List<Receita> findByDateRange(LocalDate dateAsFirstDayOfMonth, LocalDate dateAsLastDayOfMonth) {
        return repository.findByDateRange(dateAsFirstDayOfMonth, dateAsLastDayOfMonth);
    }
}
