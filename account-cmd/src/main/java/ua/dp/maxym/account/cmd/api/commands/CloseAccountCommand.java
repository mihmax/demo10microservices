package ua.dp.maxym.account.cmd.api.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.dp.maxym.cqrs.core.commands.BaseCommand;

@EqualsAndHashCode(callSuper = true)
@Data
public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
