package net.financeiro.exceptions;

public class FkNaoEncontradaException extends RuntimeException {
    public FkNaoEncontradaException(String message) {
        super(message);
    }
}
