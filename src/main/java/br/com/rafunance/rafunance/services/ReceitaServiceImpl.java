package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ReceitaServiceImpl extends BaseService<Receita, Long> implements IReceitaService {
    public ReceitaServiceImpl(@Autowired ReceitaRepository repository) {
        super.repository = repository;
    }
}
