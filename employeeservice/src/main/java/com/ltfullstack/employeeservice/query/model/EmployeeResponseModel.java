package com.ltfullstack.employeeservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseModel {
    private String id;
    private String firstname;
    private String lastname;
    private String kin;
    private Boolean isDisciplined;
}
