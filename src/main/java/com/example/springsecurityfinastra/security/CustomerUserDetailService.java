package com.example.springsecurityfinastra.security;

import com.example.springsecurityfinastra.entity.Employee;
import com.example.springsecurityfinastra.repo.EmployeeRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    public CustomerUserDetailService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> customer = employeeRepo.findByUserIdIgnoreCase(username);
        if (customer.isEmpty()) {
            try {
                long mobile = Long.parseLong(username);
                customer = employeeRepo.findByPhone(mobile);
            } catch (NumberFormatException e) {
                throw new UsernameNotFoundException("Invalid email or mobile: " + username);
            }
        }
        if (customer.isEmpty()) {
            throw new UsernameNotFoundException("Customer not found with email or mobile: " + username);
        }
        return new CustomUserDetails(customer.get());
    }
}
