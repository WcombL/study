package com.lei.controller;

import com.lei.activiti.ProcessEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private ProcessEngineService engineService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}