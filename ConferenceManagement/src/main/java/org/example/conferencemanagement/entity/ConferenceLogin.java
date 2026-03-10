package org.example.conferencemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "conferenceLogin")
@Getter
@Setter
public class ConferenceLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false,unique = true)
    private String emailId;

}
