package br.com.rafunance.rafunance.models.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto {
    private Long id;
    @NotNull
    private String descricao;
    @NotNull
    private Double valor;
    @NotNull
    private LocalDate data;
}
