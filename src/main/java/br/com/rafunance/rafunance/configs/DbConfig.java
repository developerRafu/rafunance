package br.com.rafunance.rafunance.configs;

import br.com.rafunance.rafunance.models.DespesaCategoria;
import br.com.rafunance.rafunance.models.entities.Despesa;
import br.com.rafunance.rafunance.models.entities.Receita;
import br.com.rafunance.rafunance.repositories.DespesaRepository;
import br.com.rafunance.rafunance.repositories.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Profile("dev")
@Configuration
public class DbConfig {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    @Bean(name = "Fill db")
    public boolean fillDb() {
        Receita receita = new Receita(null, "Salário", 4000.0, LocalDate.of(2022, 2, 1));
        Despesa despesa = new Despesa(null, "Prime", 10.0, LocalDate.of(2022, 2, 1), DespesaCategoria.LAZER);
        Despesa despesa2 = new Despesa(null, "Netflix", 10.0, LocalDate.of(2022, 2, 1), DespesaCategoria.LAZER);

        receitaRepository.save(receita);
        despesaRepository.saveAll(Arrays.asList(despesa, despesa2));

        return true;
    }
}
