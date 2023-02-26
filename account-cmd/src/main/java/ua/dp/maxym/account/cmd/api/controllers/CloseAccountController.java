package ua.dp.maxym.account.cmd.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.maxym.account.cmd.api.commands.CloseAccountCommand;
import ua.dp.maxym.account.cmd.api.dto.OpenAccountResponse;
import ua.dp.maxym.account.common.dto.BaseResponse;
import ua.dp.maxym.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping("/api/v1/bankaccount")
@Slf4j
public class CloseAccountController {
    private final CommandDispatcher commandDispatcher;

    public CloseAccountController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(name = "id") String id) {
        var command = new CloseAccountCommand(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account closed successfully", id),
                                        HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.atWarn().log("Client made bad request {}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeError = String.format("Error while closing bank account for id %s", id);
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
