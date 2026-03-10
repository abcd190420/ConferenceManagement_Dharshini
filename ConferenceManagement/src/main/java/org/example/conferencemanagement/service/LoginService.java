package org.example.conferencemanagement.service;

import org.example.conferencemanagement.entity.ConferenceLogin;
import org.example.conferencemanagement.repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class LoginService {

    private final LoginRepository loginrepository;
    private PasswordEncoder encoder;

    public LoginService(LoginRepository loginrepository, PasswordEncoder encoder) {
        this.loginrepository = loginrepository;
        this.encoder = encoder;
    }

    public ConferenceLogin registerUser(ConferenceLogin conferencelogin){

        var userExist = loginrepository.findByUserName(conferencelogin.getUserName());
        var emailExist = loginrepository.findByEmailId(conferencelogin.getEmailId());

        if(userExist == null && emailExist == null){

            conferencelogin.setPassword(
                    encoder.encode(conferencelogin.getPassword())
            );

            return loginrepository.save(conferencelogin);
        }

        throw new RuntimeException("User or Email already exists");
    }


    public ConferenceLogin loginUser(String userName, String password){

        ConferenceLogin user = loginrepository.findByUserName(userName);

        if(user == null){
            throw new RuntimeException("User not found");
        }

        if(!encoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}