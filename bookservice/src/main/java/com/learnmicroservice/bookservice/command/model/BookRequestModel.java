package com.learnmicroservice.bookservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestModel {
    private String id;

    @Size(min = 2 , max = 30, message = "name must be between 2 and 30 characters")
    @NotBlank(message = "name is mandatary")
    private String name;

    @NotBlank(message = "author is mandatary")
    private String author;

    private Boolean isRealy;
}
