package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.filters.BaseFilter;
import br.com.rafunance.rafunance.repositories.DespesaRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DespesaServiceImpl implements IDespesaService {

    @Autowired
    private DespesaRepository repository;

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
    public Page<Despesa> findByFilter(BaseFilter filter) {
        throw new NotYetImplementedException("Método não implementado");
    }
}
