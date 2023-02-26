package ua.dp.maxym.account.cmd;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.dp.maxym.account.cmd.api.commands.*;
import ua.dp.maxym.cqrs.core.infrastructure.CommandDispatcher;

@SpringBootApplication
@Slf4j
public class AccountCommandApplication {

    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    public AccountCommandApplication(CommandDispatcher commandDispatcher, CommandHandler commandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.commandHandler = commandHandler;
    }

    @PostConstruct
    public void registerHandlers() {
        log.atInfo().log("Registering handlers");
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountCommandApplication.class, args);
    }

}
