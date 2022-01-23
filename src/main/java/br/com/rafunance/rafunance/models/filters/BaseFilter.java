package br.com.rafunance.rafunance.models.filters;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class BaseFilter {

    private Long id;
    private String descricao;
    private Double valor;
    private LocalDate data;

    Pageable pageable;

    protected BaseFilter() {
        pageable = PageRequest.of(0, 10);
    }

    public void setPage(int page) {
        pageable = PageRequest.of(page, pageable.getPageSize());
    }

    public void setSize(int size) {
        pageable = PageRequest.of(pageable.getPageNumber(), size);
    }

    public int getPage() {
        return pageable.getPageNumber();
    }

    public int getSize() {
        return pageable.getPageSize();
    }

}
