package com.testapplication.demo.repository;

import com.testapplication.demo.entity.Department;
import com.testapplication.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmpRepository extends JpaRepository<Employee,Integer> {
    List<Employee> findByEmpId (int dept_Id);
    List<Employee> findByEmpName (String dept_name);
}
