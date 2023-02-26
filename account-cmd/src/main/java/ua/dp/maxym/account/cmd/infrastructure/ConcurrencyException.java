package ua.dp.maxym.account.cmd.infrastructure;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) {
        super(message);
    }
}
