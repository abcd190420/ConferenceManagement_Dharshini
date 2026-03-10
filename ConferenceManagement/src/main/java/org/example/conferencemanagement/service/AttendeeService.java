package org.example.conferencemanagement.service;

import org.example.conferencemanagement.entity.Attendee;
import org.example.conferencemanagement.entity.Registration;
import org.example.conferencemanagement.entity.Session;
import org.example.conferencemanagement.repository.AttendeeRepository;
import org.example.conferencemanagement.repository.RegistrationRepository;
import org.example.conferencemanagement.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final SessionRepository sessionRepository;
    private final RegistrationRepository registrationRepository;

    public AttendeeService(AttendeeRepository attendeeRepository,
                           SessionRepository sessionRepository,
                           RegistrationRepository registrationRepository) {

        this.attendeeRepository = attendeeRepository;
        this.sessionRepository = sessionRepository;
        this.registrationRepository = registrationRepository;
    }

    public Attendee registerAttendee(Integer sessionId, Attendee attendee) {

        // Check session
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found with id: " + sessionId));

        // Check capacity first
        long registrationCount = registrationRepository.countBySession(session);

        if (registrationCount >= session.getCapacity()) {
            throw new RuntimeException("Session capacity reached");
        }

        // Save attendee
        Attendee savedAttendee = attendeeRepository.save(attendee);

        // Check duplicate registration
        boolean exists = registrationRepository
                .existsBySessionAndAttendee(session, savedAttendee);

        if (exists) {
            throw new RuntimeException("Attendee already registered for this session");
        }

        // Create registration
        Registration registration = new Registration();
        registration.setSession(session);
        registration.setAttendee(savedAttendee);

        registrationRepository.save(registration);

        return savedAttendee;
    }

    /* -------- Get Attendees of a Session -------- */

    public List<Attendee> getSessionAttendees(Integer sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found"));

        return registrationRepository.findBySession(session)
                .stream()
                .map(Registration::getAttendee)
                .toList();
    }
}