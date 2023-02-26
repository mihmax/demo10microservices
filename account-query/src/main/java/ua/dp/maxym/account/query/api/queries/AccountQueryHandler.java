package ua.dp.maxym.account.query.api.queries;

import org.springframework.stereotype.Service;
import ua.dp.maxym.account.query.domain.AccountRepository;
import ua.dp.maxym.account.query.domain.BankAccount;
import ua.dp.maxym.cqrs.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler {
    private final AccountRepository accountRepository;

    public AccountQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsByIdQuery query) {
        var bankAccountOptional = accountRepository.findById(query.getId());
        if (bankAccountOptional.isEmpty())
            return new ArrayList<>();

        List<BaseEntity> bankAccountList = new ArrayList<>(1);
        bankAccountList.add(bankAccountOptional.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsByHolderQuery query) {
        var bankAccountOptional = accountRepository.findByAccountHolder(query.getAccountHolder());
        if (bankAccountOptional.isEmpty())
            return new ArrayList<>();

        List<BaseEntity> bankAccountList = new ArrayList<>(1);
        bankAccountList.add(bankAccountOptional.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
        Iterable<BankAccount> bankAccounts =
                switch (query.getEqualityType()) {
                    case GREATER_THAN -> accountRepository.findByBalanceGreaterThan(query.getBalance());
                    case LESS_THAN -> accountRepository.findByBalanceLessThan(query.getBalance());
                };
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }
}
