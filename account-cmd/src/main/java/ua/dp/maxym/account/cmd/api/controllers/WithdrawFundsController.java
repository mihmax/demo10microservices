package ua.dp.maxym.account.cmd.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dp.maxym.account.cmd.api.commands.WithdrawFundsCommand;
import ua.dp.maxym.account.cmd.infrastructure.AggregateNotFoundException;
import ua.dp.maxym.account.common.dto.BaseResponse;
import ua.dp.maxym.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping("/api/v1/bankaccount/withdraw")
@Slf4j
public class WithdrawFundsController {
    private final CommandDispatcher commandDispatcher;

    public WithdrawFundsController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String id,
                                                      @RequestBody WithdrawFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Withdrawing funds from bank account completed successfully"),
                                        HttpStatus.OK);
        } catch (AggregateNotFoundException |
                 IllegalStateException e) {
            log.atWarn().log("Client made bad request {}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeError = String.format("Error while withdrawing funds for id %s", id);
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
