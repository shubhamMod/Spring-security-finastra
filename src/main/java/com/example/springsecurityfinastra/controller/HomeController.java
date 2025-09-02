package com.example.springsecurityfinastra.controller;

import com.example.springsecurityfinastra.entity.Employee;
import com.example.springsecurityfinastra.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    EmployeeService  employeeService;
    public HomeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(){
        return "Hello World";
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> setData(@RequestBody Employee employee) {
        Employee setEmployee = employeeService.setEmployee(employee);
        Map<String , Object>mp=new HashMap<String, Object>();
        if(setEmployee!=null) {
            mp.put("UserId", setEmployee.getUserId());
            mp.put("Status", HttpStatus.OK );
            return ResponseEntity.ok(mp);
        }else {
            mp.put("Status",HttpStatus.BAD_REQUEST );
            mp.put("Message", "User not created");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mp);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> getLoginData(@RequestBody Employee employee) {
        Employee login = employeeService.getLogin(employee);
        Map<String, Object> response = new HashMap<>();
        if (login != null) {
            response.put("Status", "OK");
            response.put("Message", "Login Successful");
            response.put("Employee", login);
            return ResponseEntity.ok(response);
        } else {
            response.put("Status", "FAIL");
            response.put("Message", "User not found with userId: " + employee.getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
