package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.repositories.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ReceitaServiceImpl implements IReceitaService {

    private ReceitaRepository repository;

    @Override
    public List<Receita> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Receita> findByID(Long id) {
        return repository.findById(id);
    }

    @Override
    public Receita save(Receita obj) {
        return repository.save(obj);
    }

    @Override
    public Receita update(Receita obj, Long id) {
        boolean theresIsEntity = findByID(id).isPresent();
        if (!theresIsEntity) {
            throw new NotFoundException("Objeto não encontrado");
        }
        return repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Receita> findByDesc(String desc) {
        return repository.findByDescricao(desc);
    }

    @Override
    public List<Receita> findByDateRange(LocalDate dateAsFirstDayOfMonth, LocalDate dateAsLastDateOfMonth) {
        return repository.findByDateRange(dateAsFirstDayOfMonth, dateAsLastDateOfMonth);
    }
}
