package com.testapplication.demo.service;

import com.testapplication.demo.entity.Department;
import com.testapplication.demo.repository.DeptRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tools.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class DeptService {

    private final DeptRepository deptRepository;

    public DeptService (DeptRepository deptRepository, ObjectMapper objectMapper){
        this.deptRepository=deptRepository;
//        this.objectMapper = objectMapper;
    }

    public List<Department> getDept(){
        return deptRepository.findAll();
    }
//    private final ObjectMapper objectMapper;
//
//    public  ResponseEntity<String> getDept(){
//        System.out.println(objectMapper.writeValueAsString(deptRepository.findAll()));
//
//        return ResponseEntity.ok(objectMapper.writeValueAsString(deptRepository.findAll()));
//    }

    public List<Department> getDeptId(Integer id){
        var d= deptRepository.findByDeptId(id);
        return d;
    }

    public  List<Department> getDeptName(String name){
        return deptRepository.findByDeptName(name);
    }

    public Department saveDept( Department departments){

        var existDept  = deptRepository.findByDeptName(departments.getDeptName());
        if(existDept.isEmpty()){
            return deptRepository.save(departments);

        }
        else
        {
            throw new RuntimeException();
        }

    }

}
