package br.com.rafunance.rafunance.mocks;

import br.com.rafunance.rafunance.models.DespesaCategoria;
import br.com.rafunance.rafunance.models.entities.Despesa;

import java.time.LocalDate;

public class DespesaMockBuilder {
    public static Despesa getMock() {
        return new Despesa(1L, "Netflix", 65.0, LocalDate.now().withDayOfMonth(5), DespesaCategoria.LAZER);
    }
}
