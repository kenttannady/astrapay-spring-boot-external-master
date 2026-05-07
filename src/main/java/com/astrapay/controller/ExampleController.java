package com.astrapay.controller;

import com.astrapay.dto.ExampleDto;
import com.astrapay.exception.ExampleException;
import com.astrapay.service.ExampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "ExampleController")
@Slf4j
public class ExampleController {
    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/hello")
    @ApiOperation(value = "Say Hello")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = ExampleDto.class)
            }
    )
    public ResponseEntity<String> sayHello(@RequestParam String name, @RequestParam String description) {
        log.info("Incoming hello Request from " + name);

        try {
            ExampleDto exampleDto = new ExampleDto();
            exampleDto.setName(name);
            exampleDto.setDescription(description);

            return ResponseEntity.ok(exampleService.sayHello(exampleDto));
        } catch (ExampleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}