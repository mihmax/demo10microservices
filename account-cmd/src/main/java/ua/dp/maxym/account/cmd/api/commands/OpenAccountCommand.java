package ua.dp.maxym.account.cmd.api.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.dp.maxym.account.common.dto.AccountType;
import ua.dp.maxym.cqrs.core.commands.BaseCommand;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
