package com.testapplication.demo.controller;

import com.testapplication.demo.dto.Testdto;
import com.testapplication.demo.entity.Employee;
import com.testapplication.demo.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final TestService testService;

    public EmployeeController(TestService testService){
        this.testService=testService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Welcome to Spring Boot!";

    }

    @GetMapping("/sumParam")
    public Integer sayHello(@RequestParam("a") int a,@RequestParam("b")int b) {
//        return "Hello, Welcome to Spring Boot!";
        return testService.sum(a,b);
    }

    @PostMapping("/sum")
    public Integer sumBody(@RequestBody Testdto testdto ){

        return testService.sum(testdto.getA(),testdto.getB());
    }

   @GetMapping("/getAll")
    public List<Employee> getAll(){
        return testService.getAll();
    }


}