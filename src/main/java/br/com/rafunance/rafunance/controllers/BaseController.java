package br.com.rafunance.rafunance.controllers;

import br.com.rafunance.rafunance.errors.exceptions.NotFoundException;
import br.com.rafunance.rafunance.services.BaseService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

public class BaseController<T, D, I> {
    protected BaseService<T, I> service;
    protected ModelMapper mapper;

    public BaseController(BaseService<T, I> service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<D>> findAll() {
        return ResponseEntity.ok().body(
                (List<D>) service.findAll()
                        .stream()
                        .map(this::convertToDTo).collect(Collectors.toList()));
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable ID id) {
        return service.findById(id).map(obj -> {
            var dto = this.convertToDTo(obj);
            return ResponseEntity.ok().body(dto);
        }).orElseThrow(() -> new NotFoundException("Objeto não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody Object dto) {
        var obj = convertToEntity(dto);
        return ResponseEntity.ok().body(service.save((T) obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable ID id, @RequestBody T dto) {
        var obj = convertToEntity(dto);
        return ResponseEntity.ok().body(service.update(id, (T) obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    private Object convertToEntity(Object dto) {
        return mapper.map(dto, Object.class);
    }

    private Object convertToDTo(Object obj) {
        return mapper.map(obj, Object.class);
    }
}
