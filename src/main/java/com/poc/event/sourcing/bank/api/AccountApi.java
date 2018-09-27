package com.poc.event.sourcing.bank.api;

import com.poc.event.sourcing.bank.api.dto.AccountDto;
import com.poc.event.sourcing.bank.api.dto.DepositDto;
import com.poc.event.sourcing.bank.command.CreateAccountCommand;
import com.poc.event.sourcing.bank.command.DepositMoneyCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class AccountApi implements BaseVersion {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private EventStore eventStore;

    @PostMapping("/account")
    @ApiOperation(
            value = "Create a account",
            response = CompletableFuture.class,
            notes = "This operation create a bank account"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    code = 200,
                    message = "Success to create a bank account",
                    response = AccountDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "When have some expected error in data or account already exists in bank",
                    response = Error.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "When have some unexpected error",
                    response = Error.class
            )
    })
    public CompletableFuture<String> createAccount(@Valid @RequestBody AccountDto accountDto){
        String id = UUID.randomUUID().toString();
        return commandGateway.send(new CreateAccountCommand(id, accountDto.getName()));
    }

    @GetMapping("/account/{id}/events")
    @ApiOperation(
            value = "Get events occured in a account by account id",
            response = CompletableFuture.class,
            notes = "This operation get events from a account"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    code = 200,
                    message = "Success to get account events",
                    response = AccountDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "When have some expected error in data or account id not found",
                    response = Error.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "When have some unexpected error",
                    response = Error.class
            )
    })
    public List<?> getEvents(@PathVariable String id) {
        return eventStore.readEvents(id).asStream().map(Message::getPayload).collect(Collectors.toList());
    }

    @PutMapping(path = "/account/{accountId}/deposit")
    @ApiOperation(
            value = "Deposit money in a account by account id",
            response = CompletableFuture.class,
            notes = "This operation deposit money in a account"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    code = 200,
                    message = "Success to deposit",
                    response = AccountDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "When have some expected error in data or account id not found",
                    response = Error.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "When have some unexpected error",
                    response = Error.class
            )
    })
    public CompletableFuture<String> deposit(@PathVariable String accountId, @Valid @RequestBody DepositDto depositDto) {
        return commandGateway.send(new DepositMoneyCommand(accountId, depositDto.getAmount()));
    }
}
