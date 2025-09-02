package com.example.springsecurityfinastra.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @GetMapping("/home")
    @PreAuthorize("hasRole('USER')")
    public String home(){
        return "Hello World";
    }
}
