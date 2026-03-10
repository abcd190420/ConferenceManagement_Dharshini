package com.testapplication.demo.controller;

import com.testapplication.demo.entity.Department;
import com.testapplication.demo.entity.Employee;
import com.testapplication.demo.service.DeptService;
import com.testapplication.demo.service.EmpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/emp")
public class EmpController {

    private final ObjectMapper objectMapper;
    private  EmpService empService;
    public EmpController(EmpService empService, ObjectMapper objectMapper) {
        this.empService = empService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public List<Employee> getAllEmp() {
        return empService.getAllEmp();
    }

    @GetMapping("/id/{id}")
    public List<Employee> getEmpId(@PathVariable int id){
        return  empService.getEmpId(id);
    }

    @GetMapping("/name/{name}")
    public  List<Employee> getEmpName(@PathVariable String name){
        return empService.getEmpName(name);
    }

    @PostMapping()
    public ResponseEntity<String> saveEmp(@RequestBody Employee employee) {
        try{
           
                return ResponseEntity.ok(objectMapper.writeValueAsString(empService.saveEmp(employee)));
           
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping
    public  ResponseEntity<String> delEmp(@RequestBody Employee employee) {
        empService.delEmp(employee.getEmpId());
        return ResponseEntity.ok().body("Success");
    }

    @PutMapping
    public ResponseEntity<String> updateEmp(@RequestBody Employee employee) {
        try{
            return ResponseEntity.ok(objectMapper.writeValueAsString(empService.updateEmp(employee)));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
