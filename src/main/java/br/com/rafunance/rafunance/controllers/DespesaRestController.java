package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.models.dtos.DespesaDto;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.filters.DespesaFilter;
import br.com.rafunance.rafunance.services.IDespesaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("despesas")
public class DespesaRestController extends BaseController<Despesa, DespesaDto, DespesaFilter> {
    protected DespesaRestController(
            @Autowired IDespesaService service,
            @Autowired ModelMapper mapper
    ) {
        super(service, mapper);
    }
}
