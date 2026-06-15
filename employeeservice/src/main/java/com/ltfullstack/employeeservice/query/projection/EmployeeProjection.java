package com.ltfullstack.employeeservice.query.projection;

import com.ltfullstack.commonservice.model.EmployeeResponseCommandModel;
import com.ltfullstack.employeeservice.command.data.Employee;
import com.ltfullstack.employeeservice.command.data.EmployeeRepository;
import com.ltfullstack.employeeservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employeeservice.query.queries.GetAllEmployeeQuery;
import com.ltfullstack.commonservice.queries.GetDetailEmployeeModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {

    private final EmployeeRepository employeeRepository;

    public EmployeeProjection(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query){
        List<Employee> model = employeeRepository.findAllByIsDisciplined(query.isDisciplined());
        return model.stream().map(employee -> {
            EmployeeResponseModel md = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, md);
            return md;
        }).toList();
    }

    @QueryHandler
    public EmployeeResponseCommandModel handle(GetDetailEmployeeModel model) throws Exception{
     Employee employee = employeeRepository.findById(model.getId()).orElseThrow(() -> new Exception("Employee not found"));
        EmployeeResponseCommandModel md = new EmployeeResponseCommandModel();
     BeanUtils.copyProperties(employee, md);
     return md;
    }
}
