package ua.dp.maxym.account.query;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.dp.maxym.account.query.api.queries.*;
import ua.dp.maxym.cqrs.core.infrastructure.QueryDispatcher;

@SpringBootApplication
public class AccountQueryApplication {
    private final QueryDispatcher queryDispatcher;
    private final QueryHandler queryHandler;

    public AccountQueryApplication(QueryDispatcher queryDispatcher, QueryHandler queryHandler) {
        this.queryDispatcher = queryDispatcher;
        this.queryHandler = queryHandler;
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountsByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountsByHolderQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountsWithBalanceQuery.class, queryHandler::handle);
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountQueryApplication.class, args);
    }

}
