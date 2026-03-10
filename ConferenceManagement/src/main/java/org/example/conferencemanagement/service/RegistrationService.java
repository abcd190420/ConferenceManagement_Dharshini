package org.example.conferencemanagement.service;

import org.example.conferencemanagement.entity.Attendee;
import org.example.conferencemanagement.entity.Registration;
import org.example.conferencemanagement.entity.Session;
import org.example.conferencemanagement.repository.AttendeeRepository;
import org.example.conferencemanagement.repository.RegistrationRepository;
import org.example.conferencemanagement.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final SessionRepository sessionRepository;
    private final AttendeeRepository attendeeRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               SessionRepository sessionRepository,
                               AttendeeRepository attendeeRepository) {
        this.registrationRepository = registrationRepository;
        this.sessionRepository = sessionRepository;
        this.attendeeRepository = attendeeRepository;
    }

    public Registration register(Integer sessionId, Integer attendeeId) {

        // Find session
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Find attendee
        Attendee attendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new RuntimeException("Attendee not found"));

        // Check duplicate registration
        if (registrationRepository.existsByAttendee_IdAndSession_Id(attendeeId, sessionId)) {
            throw new RuntimeException("Already registered for this session");
        }


        long count = registrationRepository.countBySession_Id(sessionId);

        if (count >= session.getCapacity()) {
            throw new RuntimeException("Session capacity reached");
        }


        Registration registration = new Registration();
        registration.setSession(session);
        registration.setAttendee(attendee);

        return registrationRepository.save(registration);
    }

    public Registration registerSession(Integer sessionId, Integer attendeeId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Attendee attendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new RuntimeException("Attendee not found"));

        Registration registration = new Registration();
        registration.setSession(session);
        registration.setAttendee(attendee);
        registration.setRegisteredAt(Instant.now());

        return registrationRepository.save(registration);
    }
}