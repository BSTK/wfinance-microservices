package dev.bstk.wfinance.core.exception;

public class EmailException extends RuntimeException {

    public EmailException(final Throwable cause) {
        super("Nao foi possivél enviar email.", cause);
    }

}
