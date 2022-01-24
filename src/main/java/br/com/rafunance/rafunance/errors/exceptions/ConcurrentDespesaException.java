package br.com.rafunance.rafunance.errors.exceptions;

public class ConcurrentDespesaException extends RuntimeException {
    public ConcurrentDespesaException(String msg) {
        super(msg);
    }
}
