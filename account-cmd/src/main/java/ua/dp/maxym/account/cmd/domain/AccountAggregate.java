package ua.dp.maxym.account.cmd.domain;

import lombok.NoArgsConstructor;
import ua.dp.maxym.account.cmd.api.commands.OpenAccountCommand;
import ua.dp.maxym.account.common.events.AccountClosedEvent;
import ua.dp.maxym.account.common.events.AccountOpenedEvent;
import ua.dp.maxym.account.common.events.FundsDepositedEvent;
import ua.dp.maxym.account.common.events.FundsWithdrawnEvent;
import ua.dp.maxym.cqrs.core.domain.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private BigDecimal balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                                     .id(command.getId())
                                     .accountHolder(command.getAccountHolder())
                                     .accountType(command.getAccountType())
                                     .openingBalance(command.getOpeningBalance())
                                     .createdDate(LocalDateTime.now())
                                     .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.active = true;
        this.id = event.getId();
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(BigDecimal amount) {
        if (active==null || !active)
            throw new IllegalStateException("Funds cannot be deposited into closed account " + this.getId());
        if (amount == null)
            throw new IllegalStateException("Deposit amount must be not null");
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
            throw new IllegalStateException("Deposit amount must be greater than zero, but got " + amount);
        raiseEvent(FundsDepositedEvent.builder()
                                      .id(this.id)
                                      .depositedAmount(amount)
                                      .build());
    }

    public void apply(FundsDepositedEvent event) {
        id = event.getId();
        balance = balance.add(event.getDepositedAmount());
    }

    public void withdrawFunds(BigDecimal amount) {
        if (active==null || !active) throw new IllegalStateException("Funds cannot be withdrawn from closed account " + this.getId());
        if (amount == null)
            throw new IllegalStateException("Withdrawal amount must be not null");
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
            throw new IllegalStateException("Withdrawal amount must be greater than zero, but got " + amount);
        if (balance.compareTo(amount) < 0)
            throw new IllegalStateException("Withdrawal declined, insufficient funds. Requested " + amount + ", but balance is " + balance);

        raiseEvent(FundsWithdrawnEvent.builder()
                                      .id(this.id)
                                      .withdrawnAmount(amount)
                                      .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        id = event.getId();
        balance = balance.subtract(event.getWithdrawnAmount());
    }

    public void closeAccount() {
        if (active==null || !active) throw new IllegalStateException("Cannot close already closed account " + this.getId());
        raiseEvent(AccountClosedEvent.builder()
                                     .id(this.id)
                                     .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
