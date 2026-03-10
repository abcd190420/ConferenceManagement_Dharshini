package org.example.conferencemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ConferenceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceManagementApplication.class, args);
    }

}
