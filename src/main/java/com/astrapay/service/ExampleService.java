package com.astrapay.service;

import com.astrapay.dto.ExampleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExampleService {
    public String sayHello(ExampleDto exampleDto) {
        return "Hello, " + exampleDto.getName() + "! Description: " + exampleDto.getDescription();
    }
}