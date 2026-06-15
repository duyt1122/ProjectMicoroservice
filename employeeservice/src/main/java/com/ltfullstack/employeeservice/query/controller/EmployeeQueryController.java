package com.ltfullstack.employeeservice.query.controller;

import com.ltfullstack.commonservice.model.EmployeeResponseCommandModel;
import com.ltfullstack.employeeservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employeeservice.query.queries.GetAllEmployeeQuery;
import com.ltfullstack.commonservice.queries.GetDetailEmployeeModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
@Slf4j
public class EmployeeQueryController {

    private final QueryGateway queryGateway;

    public EmployeeQueryController(QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }

    @Operation(
            summary = "Get List Employee",
            description = "Get endpoint for employee with filter",
            responses ={
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized / Invalid Token"
                    )
            }
    )
    @GetMapping
    public List<EmployeeResponseModel> getAllEmployee(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined){
        log.info("Calling to GetAllEmployee");
     List<EmployeeResponseModel> list = queryGateway.query(new GetAllEmployeeQuery(isDisciplined), ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
     return list;
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseCommandModel getDetailEmployee(@PathVariable String employeeId){
        return queryGateway.query(new GetDetailEmployeeModel(employeeId), ResponseTypes.instanceOf(EmployeeResponseCommandModel.class)).join();
    }

}
