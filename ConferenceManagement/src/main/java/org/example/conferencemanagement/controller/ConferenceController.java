package org.example.conferencemanagement.controller;

import org.example.conferencemanagement.entity.Conference;
import org.example.conferencemanagement.service.ConferenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping
    public ResponseEntity<Conference> createConference(@RequestBody Conference conference) {
        return ResponseEntity.ok(conferenceService.saveConference(conference));
    }

    @GetMapping
    public Page<Conference> getAllConferences(Pageable pageable) {
        return conferenceService.getAllConferences(pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Conference> getConferenceById(@PathVariable int id) {
        return ResponseEntity.ok(conferenceService.getConferenceById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Conference> updateConference(
            @PathVariable int id,
            @RequestBody Conference conference) {

        return ResponseEntity.ok(conferenceService.updateConference(id, conference));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConference(@PathVariable int id) {

        conferenceService.deleteConference(id);
        return ResponseEntity.ok("Conference deleted successfully");
    }
}