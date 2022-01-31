package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DespesaServiceImpl extends BaseService<Despesa, Long> implements IDespesaService {
    public DespesaServiceImpl(@Autowired DespesaRepository repository) {
        super.repository = repository;
    }
}
