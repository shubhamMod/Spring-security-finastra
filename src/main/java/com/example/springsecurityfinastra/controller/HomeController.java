package com.example.springsecurityfinastra.controller;

import com.example.springsecurityfinastra.entity.Employee;
import com.example.springsecurityfinastra.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
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
    public ResponseEntity<Map<String, Object>> getLoginData(@RequestBody Employee employee, HttpServletRequest  request) {

        Map<String, Object> response = new HashMap<>();
      try{
          Employee login = employeeService.getLogin(employee);
            response.put("Status", "OK");
            response.put("Message", "Login Successful");
            response.put("Employee", login);
          HttpSession session = request.getSession(true);
          session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

          return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("Status", "FAIL");
            response.put("Message", "User not found with userId: "+employee.getUserId() );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }




    @GetMapping("/fetching")
    public List<Employee> allData() {
        return employeeService.all();
    }

    @GetMapping("/fetchingSorted/{field}")
    public List<Employee> allData(@PathVariable String field) {
        return employeeService.allSorted(field);
    }

    @GetMapping("/block")
    public List<Employee> allBlockData() {
        return employeeService.blockEmployee();
    }
    @GetMapping("/active")
    public List<Employee> allActiveData() {
        return employeeService.activeEmployee();
    }



    @GetMapping("/check/{userId}")
    public ResponseEntity<Map<String, Object>> setUpdate(@PathVariable String userId) {

        Map<String, Object> response = new HashMap<>();
        try{
            Employee employee = employeeService.setMod(userId);
            response.put("Status", "OK");
            response.put("Message", "User found with userId: " + userId);
            response.put("employee", employee);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("Status", "BAD_REQUEST");
            response.put("Message", " User not found with userId: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> getDelete(@PathVariable String userId) {
        boolean setDel = employeeService.setDel(userId);
        if(setDel) {
            return ResponseEntity.ok(" Employee deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(" user_id doesnot exist");
    }



    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateEmployee(@RequestBody Employee emp) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee updated = employeeService.updateEmployee(emp);
            response.put("Status", "OK");
            response.put("Message", " Employee updated successfully!");
            response.put("employee", updated);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Status", "BAD_REQUEST");
            response.put("Message", " Update failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }



}
