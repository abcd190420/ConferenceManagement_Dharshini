package org.example.conferencemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "speaker")
@Getter
@Setter
public class Speaker {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String bio;

    private String email;

    private String photoUrl;
}
