package org.example.conferencemanagement.controller;

import org.example.conferencemanagement.dto.RegisterRequest;
//import org.example.conferencemanagement.entity.Attendee;
import org.example.conferencemanagement.entity.Session;
import org.example.conferencemanagement.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /* -------- Create Session -------- */

    @PostMapping("/conferences/{conferenceId}/sessions")
    public Session createSession(
            @PathVariable Integer conferenceId,
            @RequestBody Session session) {

        return sessionService.createSession(conferenceId, session);
    }

    /* -------- Get Sessions by Conference -------- */

    @GetMapping("/conferences/{conferenceId}/sessions")
    public List<Session> getSessionsByConference(
            @PathVariable Integer conferenceId) {

        return sessionService.getSessionsByConference(conferenceId);
    }

    /* -------- Delete Session -------- */

    @DeleteMapping("/sessions/{sessionId}")
    public String deleteSession(@PathVariable Integer sessionId) {

        sessionService.deleteSession(sessionId);

        return "Session deleted successfully";
    }

    /* -------- Register Attendee to Session -------- */

    @PostMapping("/sessions/{sessionId}/register")
    public String registerSession(
            @PathVariable Integer sessionId,
            @RequestBody RegisterRequest request) {

        sessionService.registerSession(sessionId, request.getAttendeeId());

        return "Session registered successfully";
    }


}