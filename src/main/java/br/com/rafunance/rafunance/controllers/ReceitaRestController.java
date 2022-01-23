package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.models.dtos.ReceitaDto;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.models.filters.BaseFilter;
import br.com.rafunance.rafunance.models.filters.ReceitaFilter;
import br.com.rafunance.rafunance.services.IReceitaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receitas")
public class ReceitaRestController extends BaseController<Receita, ReceitaDto, ReceitaFilter> {

    protected ReceitaRestController(
            @Autowired IReceitaService service,
            @Autowired ModelMapper mapper
    ) {
        super(service, mapper);
    }

}
