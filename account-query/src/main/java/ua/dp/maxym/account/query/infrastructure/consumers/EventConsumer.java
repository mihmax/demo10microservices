package ua.dp.maxym.account.query.infrastructure.consumers;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import ua.dp.maxym.account.common.events.AccountClosedEvent;
import ua.dp.maxym.account.common.events.AccountOpenedEvent;
import ua.dp.maxym.account.common.events.FundsDepositedEvent;
import ua.dp.maxym.account.common.events.FundsWithdrawnEvent;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
