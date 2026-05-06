package com.ltfullstack.employeeservice.command.aggregate;

import com.ltfullstack.employeeservice.command.command.CreateEmployeeComand;
import com.ltfullstack.employeeservice.command.command.UpdateEmployeeComand;
import com.ltfullstack.employeeservice.command.event.EmployeeCreateEvent;
import com.ltfullstack.employeeservice.command.event.EmployeeUpdateEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class EmployeeAggregate {
    @AggregateIdentifier
    private String id;
    private String firstname;
    private String lastname;
    private String kin;
    private Boolean isDisciplined;

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeComand command){
        EmployeeCreateEvent event = new EmployeeCreateEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateEmployeeComand command){
        EmployeeUpdateEvent event = new EmployeeUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeCreateEvent event){
        this.id = event.getId();
        this.firstname = event.getFirstname();
        this.lastname = event.getLastname();
        this.kin = event.getKin();
        this.isDisciplined = event.getIsDisciplined();
    }

    @EventSourcingHandler
    public void on(EmployeeUpdateEvent event){
        this.id = event.getId();
        this.firstname = event.getFirstname();
        this.lastname = event.getLastname();
        this.kin = event.getKin();
        this.isDisciplined = event.getIsDisciplined();
    }
}
