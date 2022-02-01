package br.com.rafunance.rafunance.services;

import br.com.rafunance.rafunance.models.dtos.Resumo;

import java.time.LocalDate;

public interface IResumoService {
    Resumo getResumoByDateRange(LocalDate initialDate, LocalDate lastDate);
}
