package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.models.dtos.ReceitaDto;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.services.BaseService;
import br.com.rafunance.rafunance.services.DespesaServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("receitas")
public class ReceitaRestController extends BaseController<Receita, ReceitaDto, Long> {
    @Autowired
    public ReceitaRestController(DespesaServiceImpl service, ModelMapper mapper) {
        super(service, mapper);
    }
}
