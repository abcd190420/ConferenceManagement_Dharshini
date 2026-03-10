package org.example.conferencemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
        name = "registration",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"attendee_id", "session_id"})
        }
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    // reminder flag
    @Column(name = "reminder_sent")
    private boolean reminderSent = false;

    // registration time
    @Column(name = "created_at")
    private Instant createdAt;

    // reminder timestamp
    @Column(name = "reminder_sent_at")
    private Instant reminderSentAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }
    private Instant registeredAt;
}