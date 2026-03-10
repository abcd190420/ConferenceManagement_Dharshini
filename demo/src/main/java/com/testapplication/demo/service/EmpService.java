package com.testapplication.demo.service;

import com.testapplication.demo.entity.Department;
import com.testapplication.demo.entity.Employee;
import com.testapplication.demo.repository.EmpRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {

    private final EmpRepository empRepository;
    public  EmpService (EmpRepository empRepository){
        this.empRepository=empRepository;
    }

    public List<Employee> getAllEmp(){
        return empRepository.findAll();
    }

    public List<Employee> getEmpId(Integer id){
        var e= empRepository.findByEmpId(id);
            return e;
    }

    public  List<Employee> getEmpName(String name){
        return empRepository.findByEmpName(name);
    }

    public  Employee saveEmp(Employee employee){
        return empRepository.save(employee);
    }

    public void delEmp (int empId){
        empRepository.deleteById(empId);
    }

    public Employee updateEmp (Employee employee){
      var existEmp = empRepository.findByEmpId(employee.getEmpId());
      if(existEmp.isEmpty())
          throw new RuntimeException();
      else
        return empRepository.save(employee);
    }

}
