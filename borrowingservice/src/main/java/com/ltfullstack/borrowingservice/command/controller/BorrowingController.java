package com.ltfullstack.borrowingservice.command.controller;

import com.ltfullstack.borrowingservice.command.command.CreateBorrowingComand;
import com.ltfullstack.borrowingservice.command.model.BorrowingCreateModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingController {

    private final CommandGateway commandGateway;

    public BorrowingController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createBorrowing(@RequestBody BorrowingCreateModel model){
        CreateBorrowingComand command = new CreateBorrowingComand(UUID.randomUUID().toString(), model.getBookId(), model.getEmployeeId(),new Date());
        return commandGateway.sendAndWait(command);
    }
}
