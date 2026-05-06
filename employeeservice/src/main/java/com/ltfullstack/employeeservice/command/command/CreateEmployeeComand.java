package com.ltfullstack.employeeservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeComand {

    @TargetAggregateIdentifier
    private String id;
    private String firstname;
    private String lastname;
    private String kin;
    private Boolean isDisciplined;
}
