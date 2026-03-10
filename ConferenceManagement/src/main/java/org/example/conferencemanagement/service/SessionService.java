package org.example.conferencemanagement.service;

import org.example.conferencemanagement.entity.*;
import org.example.conferencemanagement.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ConferenceRepository conferenceRepository;
    private final AttendeeRepository attendeeRepository;
    private final RegistrationRepository registrationRepository;

    public SessionService(SessionRepository sessionRepository,
                          ConferenceRepository conferenceRepository,
                          AttendeeRepository attendeeRepository,
                          RegistrationRepository registrationRepository) {

        this.sessionRepository = sessionRepository;
        this.conferenceRepository = conferenceRepository;
        this.attendeeRepository = attendeeRepository;
        this.registrationRepository = registrationRepository;
    }

    /* -------- Create Session -------- */

    public Session createSession(Integer conferenceId, Session session) {

        try {

            Conference conference = conferenceRepository.findById(conferenceId)
                    .orElseThrow(() ->
                            new RuntimeException("Conference not found with id: " + conferenceId));

            session.setConference(conference);

            return sessionRepository.save(session);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Session> getSessionsByConference(Integer conferenceId) {

        return sessionRepository.findByConferenceId(conferenceId);
    }

    /* -------- Delete Session -------- */

    public void deleteSession(Integer sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        sessionRepository.delete(session);
    }

    public void registerSession(Integer sessionId, Integer attendeeId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Attendee attendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new RuntimeException("Attendee not found"));

        // Check capacity
        long count = registrationRepository.countBySession(session);

        if (count >= session.getCapacity()) {
            throw new RuntimeException("Session capacity reached");
        }

        // Check duplicate registration
        boolean exists = registrationRepository
                .existsBySessionAndAttendee(session, attendee);

        if (exists) {
            throw new RuntimeException("Attendee already registered for this session");
        }

        Registration registration = new Registration();
        registration.setSession(session);
        registration.setAttendee(attendee);

        registrationRepository.save(registration);
    }

    /* -------- Get Attendees of a Session -------- */

    public List<Attendee> getSessionAttendees(Integer sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        return registrationRepository.findBySession(session)
                .stream()
                .map(Registration::getAttendee)
                .toList();
    }
    public Attendee registerAttendee(Integer sessionId, Attendee attendee) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found with id: " + sessionId));

        // Check session capacity
        long registrationCount = registrationRepository.countBySession_Id(sessionId);

        if (registrationCount >= session.getCapacity()) {
            throw new RuntimeException("Session capacity reached");
        }

        // Save attendee
        Attendee savedAttendee = attendeeRepository.save(attendee);

        // Check duplicate registration
        boolean exists = registrationRepository
                .existsByAttendee_IdAndSession_Id(savedAttendee.getId(), sessionId);

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
}