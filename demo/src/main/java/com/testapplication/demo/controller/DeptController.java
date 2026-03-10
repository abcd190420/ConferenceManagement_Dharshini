package com.testapplication.demo.controller;

import com.testapplication.demo.entity.Department;
import com.testapplication.demo.service.DeptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityReturnValueHandler;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dept")
public class DeptController {

    private DeptService deptService;

    public DeptController(DeptService deptService, ObjectMapper objectMapper) {
        this.deptService = deptService;
        this.objectMapper = objectMapper;
    }


//    public List<Department> getDept() {
//        return deptService.getDept();
//    }
    private final ObjectMapper objectMapper;
    @GetMapping()
    public ResponseEntity<String> getDept(){
        return ResponseEntity.ok(objectMapper.writeValueAsString(deptService.getDept()));
    }

    @GetMapping("/id/{id}")
    public List<Department> getDeptId(@PathVariable int id) {
        return deptService.getDeptId(id);
    }

    @GetMapping("/name/{name}")
    public List<Department> getDeptName(@PathVariable String name) {
        return deptService.getDeptName(name);
    }

    @PostMapping()
    public ResponseEntity<String> saveDept(@RequestBody Department department) {
        try{
            if(department!=null && !department.getDeptName().isBlank())
                return ResponseEntity.ok(objectMapper.writeValueAsString(deptService.saveDept(department)));
            else
                return ResponseEntity.badRequest().body("Empty Value");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
