package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.models.dtos.BaseDto;
import br.com.rafunance.rafunance.models.filters.BaseFilter;
import br.com.rafunance.rafunance.services.IBaseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BaseController<E, D, F extends BaseFilter> {

    protected IBaseService<E> service;
    protected ModelMapper mapper;

    protected BaseController(IBaseService<E> service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAll() {
        return ResponseEntity.ok().body(service.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.findById(id).map(obj -> {
            Object dto = this.convertToDto(obj);
            return ResponseEntity.ok().body(dto);
        }).orElseThrow(() -> new NotFoundException("Objeto não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody BaseDto dto) {
        Object entity = this.convertToEntity((D) dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                convertToDto(service.save((E) entity))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody D dto) {
        Object entity = this.convertToEntity(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                convertToDto(service.update((E) entity))
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("filter")
    public ResponseEntity<Page<Object>> getByFilter(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "valor", required = false) Double valor,
            @RequestParam(value = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        F filter = (F) new BaseFilter(id, descricao, valor, data, PageRequest.of(page, size));
        return ResponseEntity.ok().body(service.findByFilter(filter).map(this::convertToDto));
    }

    protected Object convertToDto(E entity) {
        return mapper.map(entity, Object.class);
    }

    protected Object convertToEntity(D dto) {
        return mapper.map(dto, Object.class);
    }

}
