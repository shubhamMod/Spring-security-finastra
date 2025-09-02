package com.example.springsecurityfinastra.repo;

import com.example.springsecurityfinastra.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,String> {
    Employee findByEmployeeId(String employeeId);
    Optional<Employee> findByUserIdIgnoreCase(String userId);

    Employee findByEmailIdIgnoreCase(String emailId);

    Optional<Employee> findByPhone(Long phone);

    @Query(value = "SELECT * FROM shubham_emp WHERE del_flg=true", nativeQuery = true)
    List<Employee> findDeletedEmployees();

    @Query(value = "SELECT * FROM shubham_emp WHERE del_flg=false", nativeQuery = true)
    List<Employee> findActiveEmployees();
}
