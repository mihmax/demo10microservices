package ua.dp.maxym.account.query.infrastructure.handlers;

import ua.dp.maxym.account.common.events.AccountClosedEvent;
import ua.dp.maxym.account.common.events.AccountOpenedEvent;
import ua.dp.maxym.account.common.events.FundsDepositedEvent;
import ua.dp.maxym.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
