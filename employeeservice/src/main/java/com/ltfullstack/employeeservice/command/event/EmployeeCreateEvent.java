package com.ltfullstack.employeeservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateEvent {
    private String id;
    private String firstname;
    private String lastname;
    private String kin;
    private Boolean isDisciplined;
}
