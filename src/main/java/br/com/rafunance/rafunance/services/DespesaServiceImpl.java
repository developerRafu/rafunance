package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.repositories.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DespesaServiceImpl implements IDespesaService {

    private final DespesaRepository repository;

    @Override
    public List<Despesa> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Despesa> findByID(Long id) {
        return repository.findById(id);
    }

    @Override
    public Despesa save(Despesa obj) {
        return repository.save(obj);
    }

    @Override
    public Despesa update(Despesa obj, Long id) {
        boolean theresIsEntity = findByID(id).isPresent();
        if (!theresIsEntity) {
            throw new NotFoundException("Objeto não encontrado");
        }
        return repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        boolean theresIsEntity = findByID(id).isPresent();
        if (!theresIsEntity) {
            throw new NotFoundException("Objeto não encontrado");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Despesa> findByDesc(String desc) {
        return repository.findByDescricao(desc);
    }

    @Override
    public List<Despesa> findByDateRange(LocalDate initialDate, LocalDate lastDate) {
        return repository.findByDateRange(initialDate, lastDate);
    }
}
