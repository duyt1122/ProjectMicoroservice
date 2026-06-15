package com.ltfullstack.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseCommandModel {
    private String id;
    private String firstname;
    private String lastname;
    private String kin;
    private Boolean isDisciplined;
}
