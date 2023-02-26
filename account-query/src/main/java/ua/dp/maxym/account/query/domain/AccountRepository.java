package ua.dp.maxym.account.query.domain;

import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountHolder(String accountHolder);

    List<BankAccount> findByBalanceGreaterThan(BigDecimal balance);
    List<BankAccount> findByBalanceLessThan(BigDecimal balance);
}
