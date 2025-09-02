package com.example.springsecurityfinastra.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="mod_data")
public class Employee {
    private String name;  
    @Id
    private String userId;
    @Column(nullable = false,unique = true)
    private String employeeId;  
    @Column(nullable = false,unique = true)
    private String emailId;
    @Column(nullable = false,unique = true)
    private Long phone;
    private String password;
    private Boolean delFlg;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
