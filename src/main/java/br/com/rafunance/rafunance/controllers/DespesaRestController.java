package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.models.dtos.DespesaDto;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.services.IDespesaService;
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
@RequestMapping("despesas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DespesaRestController {
    private final IDespesaService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<DespesaDto>> getAll() {
        return ResponseEntity.ok().body(service.findAll().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDto> getById(@PathVariable Long id) {
        return service.findByID(id)
                .map(obj -> ResponseEntity.ok().body(this.convertToDto(obj)))
                .orElseThrow(() -> new NotFoundException("Objeto não encontrado"));
    }

    @PostMapping
    public ResponseEntity<DespesaDto> insert(@RequestBody DespesaDto dto) {
        Despesa objectToSave = this.convertToEntity(dto);
        Despesa objectSaved = service.save(objectToSave);
        DespesaDto dtoResponse = this.convertToDto(objectSaved);
        return ResponseEntity.ok().body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaDto> update(@PathVariable Long id, @RequestBody DespesaDto dto) {
        Despesa objectToSave = this.convertToEntity(dto);
        Despesa objectSaved = service.update(objectToSave, id);
        DespesaDto dtoResponse = this.convertToDto(objectSaved);
        return ResponseEntity.ok().body(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DespesaDto>> getByDescricao(@RequestParam("desc") String desc) {
        return ResponseEntity.ok()
                .body(service.findByDesc(desc).stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<DespesaDto>> getByMonth(@PathVariable Integer year, @PathVariable Integer month) {
        LocalDate dateAsFirstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate dateAsLastDateOfMonth = dateAsFirstDayOfMonth.withDayOfMonth(dateAsFirstDayOfMonth.lengthOfMonth());

        return ResponseEntity.ok().body(service.findByDateRange(dateAsFirstDayOfMonth, dateAsLastDateOfMonth).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    private DespesaDto convertToDto(Despesa obj) {
        return mapper.map(obj, DespesaDto.class);
    }

    private Despesa convertToEntity(DespesaDto dto) {
        return mapper.map(dto, Despesa.class);
    }
}
