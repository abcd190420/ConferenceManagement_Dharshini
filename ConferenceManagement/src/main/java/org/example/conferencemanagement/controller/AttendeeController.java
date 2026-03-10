package org.example.conferencemanagement.controller;

import org.example.conferencemanagement.entity.Attendee;
import org.example.conferencemanagement.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin("*")
public class AttendeeController {

    private final SessionService sessionService;

    public AttendeeController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/{sessionId}/attendees")
    public Attendee registerAttendee(
            @PathVariable Integer sessionId,
            @RequestBody Attendee attendee) {

        return sessionService.registerAttendee(sessionId, attendee);
    }

    @GetMapping("/{sessionId}/attendees")
    public List<Attendee> getAttendees(@PathVariable Integer sessionId) {
        return sessionService.getSessionAttendees(sessionId);
    }
}