package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.models.dtos.DespesaDto;
import br.com.rafunance.rafunance.models.dtos.ReceitaDto;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.services.IReceitaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("receitas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReceitaRestController {
    private final IReceitaService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ReceitaDto>> getAll() {
        return ResponseEntity.ok().body(service.findAll().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDto> getById(@PathVariable Long id) {
        return service.findByID(id)
                .map(obj -> ResponseEntity.ok().body(this.convertToDto(obj)))
                .orElseThrow(() -> new NotFoundException("Objeto não encontrado"));
    }

    @PostMapping
    public ResponseEntity<ReceitaDto> insert(@RequestBody ReceitaDto dto) {
        Receita objectToSave = this.convertToEntity(dto);
        Receita objectSaved = service.save(objectToSave);
        ReceitaDto dtoResponse = this.convertToDto(objectSaved);
        return ResponseEntity.ok().body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaDto> update(@PathVariable Long id, @RequestBody ReceitaDto dto) {
        Receita objectToSave = this.convertToEntity(dto);
        Receita objectSaved = service.update(objectToSave, id);
        ReceitaDto dtoResponse = this.convertToDto(objectSaved);
        return ResponseEntity.ok().body(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ReceitaDto>> getByDescricao(@RequestParam("desc") String desc) {
        return ResponseEntity.ok()
                .body(service.findByDesc(desc).stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<ReceitaDto>> getByMonth(@PathVariable Integer year, @PathVariable Integer month) {
        LocalDate dateAsFirstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate dateAsLastDateOfMonth = dateAsFirstDayOfMonth.withDayOfMonth(dateAsFirstDayOfMonth.lengthOfMonth());

        return ResponseEntity.ok().body(service.findByDateRange(dateAsFirstDayOfMonth, dateAsLastDateOfMonth).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    private ReceitaDto convertToDto(Receita obj) {
        return mapper.map(obj, ReceitaDto.class);
    }

    private Receita convertToEntity(ReceitaDto dto) {
        return mapper.map(dto, Receita.class);
    }
}
