package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.entities.BaseEntity;
import br.com.rafunance.rafunance.models.filters.BaseFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IBaseService<BaseEntity> {
    List<BaseEntity> findAll();
    Optional<BaseEntity> findById(Long id);
    BaseEntity save(BaseEntity obj);
    BaseEntity update(BaseEntity obj);
    void deleteById(Long id);
    Page<BaseEntity> findByFilter(BaseFilter filter);
}
