package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.errors.exceptions.ConcurrentDespesaException;
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
        verifyIfExistsConcurrentDespesa(obj);
        return repository.save(obj);
    }

    private void verifyIfExistsConcurrentDespesa(Despesa obj) {
        LocalDate dateAsFirstDayOfMonth = obj.getData().withDayOfMonth(1);
        LocalDate dateAsLastDayOfMonth = dateAsFirstDayOfMonth.withDayOfMonth(dateAsFirstDayOfMonth.lengthOfMonth());
        boolean thereIsConcurrentDespesa = !repository.findByDateRangeAndDescricaoAndId(dateAsFirstDayOfMonth, dateAsLastDayOfMonth, obj.getDescricao(), null).isEmpty();
        if (thereIsConcurrentDespesa) {
            throw new ConcurrentDespesaException("Despesa já cadastrada");
        }
    }

    @Override
    public Despesa update(Despesa obj, Long id) {
        verifyIfExistsObject(id);
        return repository.save(obj);
    }

    private void verifyIfExistsObject(Long id) {
        boolean thereIsEntity = findByID(id).isPresent();
        if (!thereIsEntity) {
            throw new NotFoundException("Objeto não encontrado");
        }
    }

    @Override
    public void deleteById(Long id) {
        verifyIfExistsObject(id);
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
