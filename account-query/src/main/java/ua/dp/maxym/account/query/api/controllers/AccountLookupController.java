package ua.dp.maxym.account.query.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.maxym.account.query.api.dto.AccountLookupResponse;
import ua.dp.maxym.account.query.api.dto.EqualityType;
import ua.dp.maxym.account.query.api.queries.FindAccountsByHolderQuery;
import ua.dp.maxym.account.query.api.queries.FindAccountsByIdQuery;
import ua.dp.maxym.account.query.api.queries.FindAccountsWithBalanceQuery;
import ua.dp.maxym.account.query.api.queries.FindAllAccountsQuery;
import ua.dp.maxym.account.query.domain.BankAccount;
import ua.dp.maxym.cqrs.core.infrastructure.QueryDispatcher;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
@Slf4j
public class AccountLookupController {
    private final QueryDispatcher queryDispatcher;

    public AccountLookupController(QueryDispatcher queryDispatcher) {
        this.queryDispatcher = queryDispatcher;
    }

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            if (accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                                                .accounts(accounts)
                                                .message(String.format("Successfully returned %s bank accounts",
                                                                       accounts.size()))
                                                .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to complete getAllAccounts request";
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountsByIdQuery(id));
            if (accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                                                .accounts(accounts)
                                                .message("Successfully returned bank account by id")
                                                .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to complete getAccountById request";
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByAccountHolder(
            @PathVariable("accountHolder") String accountHolder) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountsByHolderQuery(accountHolder));
            if (accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                                                .accounts(accounts)
                                                .message("Successfully returned bank account by holder")
                                                .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to complete getAccountByAccountHolder request";
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountsWithBalance(
            @PathVariable("equalityType") EqualityType equalityType, @PathVariable("balance") BigDecimal balance) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountsWithBalanceQuery(equalityType, balance));
            if (accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                                                .accounts(accounts)
                                                .message(String.format("Successfully returned %s bank accounts %s %s",
                                                                       accounts.size(), equalityType, balance))
                                                .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to complete getAccountByAccountHolder request";
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
