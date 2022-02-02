package br.com.rafunance.rafunance.mocks;

import br.com.rafunance.rafunance.models.entities.Receita;

import java.time.LocalDate;

public class ReceitaMockBuilder {
    public static Receita getMock() {
        return new Receita(1L, "Salário", 4000.0, LocalDate.now().withDayOfMonth(5));
    }
}
