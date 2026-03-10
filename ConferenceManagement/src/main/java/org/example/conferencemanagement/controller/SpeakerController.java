package org.example.conferencemanagement.controller;

import org.example.conferencemanagement.entity.Speaker;
import org.example.conferencemanagement.repository.SpeakerRepository;
//import org.example.conferencemanagement.service.SpeakerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/speakers")
@CrossOrigin("*")
public class SpeakerController {

//    private final SpeakerService speakerService;
    private final SpeakerRepository speakerRepository;
    public SpeakerController(SpeakerRepository speakerRepository) {
//        this.speakerService = speakerService;
        this.speakerRepository = speakerRepository;
    }

    @PostMapping
    public Speaker createSpeaker(
            @RequestParam("name") String name,
            @RequestParam("bio") String bio,
            @RequestParam("photo") MultipartFile photo
    ) throws IOException {

        String fileName = photo.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);

        Files.createDirectories(path.getParent());
        Files.write(path, photo.getBytes());

        Speaker speaker = new Speaker();
        speaker.setName(name);
        speaker.setBio(bio);
        speaker.setPhotoUrl("uploads/" + fileName);

        return speakerRepository.save(speaker);
    }


    @GetMapping
    public List<Speaker> getSpeakers() {
        return speakerRepository.findAll();
    }
}