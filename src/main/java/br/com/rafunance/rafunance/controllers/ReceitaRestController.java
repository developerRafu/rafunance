package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.models.dtos.ReceitaDto;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.models.filters.ReceitaFilter;
import br.com.rafunance.rafunance.services.IReceitaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas")
public class ReceitaRestController extends BaseController<Receita, ReceitaDto, ReceitaFilter> {

    private IReceitaService receitaService;

    protected ReceitaRestController(
            @Autowired IReceitaService service,
            @Autowired ModelMapper mapper
    ) {
        super(service, mapper);
        this.receitaService = (IReceitaService) super.service;
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<Object>> getByMonth(@PathVariable Integer ano, @PathVariable Integer mes) {
        LocalDate dateAsFirstDayOfMonth = LocalDate.of(ano, mes, 1);
        LocalDate dateAsLastDayOfMonth = dateAsFirstDayOfMonth.withDayOfMonth(dateAsFirstDayOfMonth.lengthOfMonth());
        return ResponseEntity.ok().body(
                receitaService.findByDateRange(dateAsFirstDayOfMonth, dateAsLastDayOfMonth)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList())
        );
    }
}
