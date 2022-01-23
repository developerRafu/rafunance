package br.com.rafunance.rafunance.errors.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public class StandartError {
    private String messsage;
    private HttpStatus status;
    private Integer code;
    private Long timestamp;
}
