package com.ltfullstack.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeModel {

    @NotBlank(message = "first name is mandatory")
    private String firstname;

    @NotBlank(message = "last name is mandatory")
    private String lastname;

    @NotBlank(message = "kin is mandatory")
    private String kin;
}
