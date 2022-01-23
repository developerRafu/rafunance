package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.models.filters.BaseFilter;
import br.com.rafunance.rafunance.repositories.ReceitaRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    public Page<Receita> findByFilter(BaseFilter filter) {
        throw new NotYetImplementedException("Método não implementado");
    }
}
