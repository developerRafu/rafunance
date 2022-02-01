package br.com.rafunance.rafunance.models;

import br.com.rafunance.rafunance.errors.exceptions.InvalidCategoryException;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum DespesaCategoria {
    ALIMENTACAO(0, "Alimentação"),
    SAUDE(1, "Saúde"),
    MORADIA(2, "Moradia"),
    TRANSPORTE(3, "Transporte"),
    EDUCACAO(4, "Educação"),
    LAZER(5, "Lazer"),
    IMPREVISTOS(6, "Imprevistos"),
    OUTRO(7, "Outro");

    private int id;
    private String desc;

    DespesaCategoria(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static DespesaCategoria valueOf(Integer id) {
        try {
            return Arrays.stream(DespesaCategoria.values())
                    .filter(despesaCategoria -> id.equals(despesaCategoria.getId()))
                    .findFirst().orElseThrow(() -> new InvalidCategoryException("Categoria inválida"));
        } catch (NullPointerException ex) {
            return null;
        }
    }

}
