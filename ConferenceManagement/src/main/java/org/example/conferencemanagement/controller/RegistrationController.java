package org.example.conferencemanagement.controller;

import org.example.conferencemanagement.dto.RegisterRequest;
import org.example.conferencemanagement.entity.Registration;
import org.example.conferencemanagement.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<Registration> registerForSession(
            @PathVariable("id") Integer sessionId,
            @RequestBody RegisterRequest request) {

        Registration registration = registrationService.register(sessionId, request.getAttendeeId());

        return ResponseEntity.ok(registration);
    }

    @PostMapping("/{sessionId}/{attendeeId}")
    public ResponseEntity<?> registerSession(
            @PathVariable Integer sessionId,
            @PathVariable Integer attendeeId
    ) {

        return ResponseEntity.ok(
                registrationService.registerSession(sessionId, attendeeId)
        );
    }
}