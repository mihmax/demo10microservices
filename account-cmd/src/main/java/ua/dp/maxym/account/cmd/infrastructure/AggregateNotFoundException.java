package ua.dp.maxym.account.cmd.infrastructure;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
