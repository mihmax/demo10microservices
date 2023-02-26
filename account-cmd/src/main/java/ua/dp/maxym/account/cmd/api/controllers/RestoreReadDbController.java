package ua.dp.maxym.account.cmd.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.maxym.account.cmd.api.commands.RestoreReadDbCommand;
import ua.dp.maxym.account.common.dto.BaseResponse;
import ua.dp.maxym.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping("/api/v1/restoreReadDb")
@Slf4j
public class RestoreReadDbController {
    private final CommandDispatcher commandDispatcher;

    public RestoreReadDbController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDb() {
        try {
            commandDispatcher.send(new RestoreReadDbCommand());
            return new ResponseEntity<>(new BaseResponse("Read DB Restore request complement successfully"),
                                        HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.atWarn().log("Client made bad request {}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeError = "Error while processing request to restore read database";
            log.atError().log(safeError);
            log.atError().log(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
