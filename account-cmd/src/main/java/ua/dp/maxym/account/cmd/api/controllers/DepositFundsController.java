package ua.dp.maxym.account.cmd.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dp.maxym.account.cmd.api.commands.DepositFundsCommand;
import ua.dp.maxym.account.cmd.infrastructure.AggregateNotFoundException;
import ua.dp.maxym.account.common.dto.BaseResponse;
import ua.dp.maxym.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping("/api/v1/bankaccount/deposit")
@Slf4j
public class DepositFundsController {
    private final CommandDispatcher commandDispatcher;

    public DepositFundsController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
                                                     @RequestBody DepositFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Depositing funds to bank account completed successfully"),
                                        HttpStatus.OK);
        } catch (AggregateNotFoundException |
                 IllegalStateException e) {
            log.atWarn().log("Client made bad request {}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeError = String.format("Error while depositing funds for id %s", id);
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
