package br.com.rafunance.rafunance.models.entities;

import br.com.rafunance.rafunance.models.DespesaCategoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TAB_DESPESA")
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Double valor;
    @Column(nullable = false)
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private DespesaCategoria categoria;
}
