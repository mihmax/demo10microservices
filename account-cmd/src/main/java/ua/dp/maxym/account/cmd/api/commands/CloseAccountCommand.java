package ua.dp.maxym.account.cmd.api.commands;

import lombok.Data;
import ua.dp.maxym.cqrs.core.commands.BaseCommand;

@Data
public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
