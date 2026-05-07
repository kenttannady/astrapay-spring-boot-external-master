package com.astrapay.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ExampleDto {
    @NotEmpty
    private String name;
    private String description;
}