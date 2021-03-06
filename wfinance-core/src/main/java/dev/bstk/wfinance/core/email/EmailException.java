package dev.bstk.wfinance.core.email;

public class EmailException extends RuntimeException {

    public EmailException(final Throwable cause) {
        super("Nao foi possivél enviar email.", cause);
    }

}
