package org.example.conferencemanagement.repository;

import org.example.conferencemanagement.entity.Attendee;
import org.example.conferencemanagement.entity.Registration;
import org.example.conferencemanagement.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface    RegistrationRepository extends JpaRepository<Registration, Integer> {

    // Check if attendee already registered for session
    boolean existsByAttendee_IdAndSession_Id(Integer attendeeId, Integer sessionId);

    // Count registrations for capacity check
    long countBySession_Id(Integer sessionId);

    // Entity based methods (recommended)
    boolean existsBySessionAndAttendee(Session session, Attendee attendee);

    long countBySession(Session session);

    // Get registrations of a session
    List<Registration> findBySession(Session session);

    // Get registrations by sessionId
    List<Registration> findBySession_Id(Integer sessionId);


    @Query("""
            SELECT r
            FROM Registration r
            WHERE r.reminderSent = false
            
           """)
    List<Registration> findUpcomingSessionsWithoutReminder(
            @Param("now") Instant now,
            @Param("window") Instant window
    );

}