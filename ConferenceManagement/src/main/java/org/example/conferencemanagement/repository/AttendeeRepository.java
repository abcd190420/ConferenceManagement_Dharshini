package org.example.conferencemanagement.repository;

import org.example.conferencemanagement.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {

    // Find attendee by email
    Optional<Attendee> findByEmail(String email);

    // Check if attendee exists by email
    boolean existsByEmail(String email);

}