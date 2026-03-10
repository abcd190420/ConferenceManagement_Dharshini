package org.example.conferencemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conference")
@Getter
@Setter
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    private String location;

    private Instant startsAt;

    private Instant endsAt;
}