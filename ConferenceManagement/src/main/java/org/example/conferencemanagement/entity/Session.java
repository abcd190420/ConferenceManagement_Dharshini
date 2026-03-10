package org.example.conferencemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//import java.time.Instant;
import java.util.List;
//import java.util.UUID;

@Entity
@Table(name = "session")
@Getter
@Setter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "speaker_id", nullable = true)
    private Speaker speaker;

    @Enumerated(EnumType.STRING)
    private Track track;

    private Integer capacity;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Registration> registrations;
}