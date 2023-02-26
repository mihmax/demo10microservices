package ua.dp.maxym.account.cmd.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.maxym.account.cmd.api.commands.OpenAccountCommand;
import ua.dp.maxym.account.cmd.api.dto.OpenAccountResponse;
import ua.dp.maxym.account.common.dto.BaseResponse;
import ua.dp.maxym.cqrs.core.infrastructure.CommandDispatcher;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bankaccount")
@Slf4j
public class OpenAccountController {
    private final CommandDispatcher commandDispatcher;

    public OpenAccountController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account created successfully", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.atWarn().log("Client made bad request {}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeError = String.format("Error while creating new bank account for id %s", id);
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
