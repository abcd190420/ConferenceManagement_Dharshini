package com.testapplication.demo.service;

import com.testapplication.demo.entity.Employee;
import com.testapplication.demo.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private EmpRepository testRepository;

    public int sum(int a,int b){
        return a+b;
    }

    public List<Employee> getAll(){
        var l = testRepository.findAll();
        return l;
    }

//    public Employee getByName(String name) {
//        return testRepository.findByName(name);
//    }
}