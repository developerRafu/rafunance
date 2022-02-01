package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.models.dtos.Resumo;
import br.com.rafunance.rafunance.services.IResumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("resumos")
public class ResumoRestController {

    private final IResumoService service;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<Resumo> getResumoByMonth(@PathVariable Integer year, @PathVariable Integer month){
        LocalDate dateAsFirstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate dateAsLastDayOfMonth = dateAsFirstDayOfMonth.withDayOfMonth(dateAsFirstDayOfMonth.lengthOfMonth());
        return ResponseEntity.ok().body(service.getResumoByDateRange(dateAsFirstDayOfMonth, dateAsLastDayOfMonth));
    }

}
