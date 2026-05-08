package com.ltfullstack.employeeservice.command.controller;

import com.ltfullstack.employeeservice.command.command.CreateEmployeeComand;
import com.ltfullstack.employeeservice.command.command.DeleteEmployeeComand;
import com.ltfullstack.employeeservice.command.command.UpdateEmployeeComand;
import com.ltfullstack.employeeservice.command.model.CreateEmployeeModel;
import com.ltfullstack.employeeservice.command.model.UpdateEmployeeModel;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

    private final CommandGateway commandGateway;

    public EmployeeCommandController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addEmployee(@Valid @RequestBody CreateEmployeeModel model){
        CreateEmployeeComand comand = new CreateEmployeeComand(UUID.randomUUID().toString(),model.getFirstname(), model.getLastname(), model.getKin(), false);
        return commandGateway.sendAndWait(comand);
    }

    @PutMapping("/{employeeId}")
    public String updateEmployee(@Valid @RequestBody UpdateEmployeeModel model, @PathVariable String employeeId){
        UpdateEmployeeComand command = new UpdateEmployeeComand(employeeId, model.getFirstname(), model.getLastname(), model.getKin(), model.getIsDisciplined());
        return commandGateway.sendAndWait(command);
    }
    @Hidden
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId){
        DeleteEmployeeComand comand = new DeleteEmployeeComand(employeeId);
       return commandGateway.sendAndWait(comand);
    }
    @PostConstruct
    public void init() {
        System.out.println("EmployeeController LOADED");
    }
}
