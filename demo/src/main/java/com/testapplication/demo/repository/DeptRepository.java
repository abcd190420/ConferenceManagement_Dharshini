package com.testapplication.demo.repository;

import com.testapplication.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Department,Integer> {

    List<Department> findByDeptId (int dept_Id);
    List<Department> findByDeptName (String dept_name);
}
