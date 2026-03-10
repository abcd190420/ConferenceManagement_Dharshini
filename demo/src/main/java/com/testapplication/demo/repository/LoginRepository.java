package com.testapplication.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.testapplication.demo.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login,Integer> {

}
