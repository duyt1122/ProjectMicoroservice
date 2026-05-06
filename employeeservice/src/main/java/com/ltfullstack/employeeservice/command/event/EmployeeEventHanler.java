package com.ltfullstack.employeeservice.command.event;

import com.ltfullstack.employeeservice.command.data.Employee;
import com.ltfullstack.employeeservice.command.data.EmployeeRepository;
import jakarta.ws.rs.NotFoundException;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeEventHanler {

    private final EmployeeRepository employeeRepository;

    public EmployeeEventHanler(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @EventHandler
    public void on(EmployeeCreateEvent event){
        Employee employee = new Employee();
        BeanUtils.copyProperties(event,employee);
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdateEvent event){
        Optional<Employee> employeeOptional = employeeRepository.findById(event.getId());
        Employee employee = employeeOptional.orElseThrow(() -> new NotFoundException("Employee not found"));
        employee.setFirstname(event.getFirstname());
        employee.setLastname(event.getLastname());
        employee.setKin(event.getKin());
        employee.setIsDisciplined(event.getIsDisciplined());
        employeeRepository.save(employee);
    }
}
