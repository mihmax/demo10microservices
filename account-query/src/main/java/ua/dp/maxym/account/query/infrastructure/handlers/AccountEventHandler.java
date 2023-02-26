package ua.dp.maxym.account.query.infrastructure.handlers;

import org.springframework.stereotype.Service;
import ua.dp.maxym.account.common.events.AccountClosedEvent;
import ua.dp.maxym.account.common.events.AccountOpenedEvent;
import ua.dp.maxym.account.common.events.FundsDepositedEvent;
import ua.dp.maxym.account.common.events.FundsWithdrawnEvent;
import ua.dp.maxym.account.query.domain.AccountRepository;
import ua.dp.maxym.account.query.domain.BankAccount;

@Service
public class AccountEventHandler implements EventHandler {

    private final AccountRepository accountRepository;

    public AccountEventHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .creationDate(event.getCreatedDate())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()) return;

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance.add(event.getDepositedAmount());
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()) return;

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance.subtract(event.getWithdrawnAmount());
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
