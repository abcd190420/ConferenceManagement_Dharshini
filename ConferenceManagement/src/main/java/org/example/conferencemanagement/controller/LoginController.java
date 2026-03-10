package org.example.conferencemanagement.controller;

import jakarta.servlet.http.HttpSession;
import org.example.conferencemanagement.entity.ConferenceLogin;
import org.example.conferencemanagement.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;


@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class LoginController {
    private LoginService loginService;
    private final ObjectMapper objectMapper;
    public LoginController (LoginService loginService, ObjectMapper objectMapper){
        this.loginService=loginService;
        this.objectMapper=objectMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody ConferenceLogin conferenceLogin) {
        try{
            if(!conferenceLogin.getEmailId().isBlank() &&!conferenceLogin.getUserName().isBlank()&&!conferenceLogin.getPassword().isBlank()&&!conferenceLogin.getRole().isBlank())
                return ResponseEntity.ok(objectMapper.writeValueAsString(loginService.registerUser(conferenceLogin)));
            else
                return ResponseEntity.badRequest().body("Empty Value");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ConferenceLogin> login(@RequestBody ConferenceLogin conferenceLogin, HttpSession session) {
        try{
            if(conferenceLogin.getUserName().isBlank() ||
                    conferenceLogin.getPassword().isBlank()){

                return ResponseEntity.badRequest().body(null);
            }

            ConferenceLogin user = loginService.loginUser(
                    conferenceLogin.getUserName(),
                    conferenceLogin.getPassword()
            );

            session.setAttribute("username", user.getUserName());
            session.setAttribute("role", user.getRole());
            session.setAttribute("user", user);

            return ResponseEntity.ok(user);


        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/logout")
    public  ResponseEntity<String> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

}
