package br.ufpr.mscadastros.exceptions;

public class EmailException extends RuntimeException {
    public EmailException(String msg) {
        super(msg);
    }
}