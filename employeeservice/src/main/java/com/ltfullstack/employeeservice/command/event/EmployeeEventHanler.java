package com.ltfullstack.employeeservice.command.event;

import com.ltfullstack.employeeservice.command.data.Employee;
import com.ltfullstack.employeeservice.command.data.EmployeeRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
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
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(event.getId());
            Employee employee = employeeOptional.orElseThrow(() -> new Exception("Employee not found"));
            employee.setFirstname(event.getFirstname());
            employee.setLastname(event.getLastname());
            employee.setKin(event.getKin());
            employee.setIsDisciplined(event.getIsDisciplined());
            employeeRepository.save(employee);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @EventHandler
    @DisallowReplay
    public void on(EmployeeDeleteEvent event){
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(event.getId());
            Employee employee = employeeOptional.orElseThrow(() -> new Exception("Employee not found"));
            employeeRepository.delete(employee);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }

    }
}
