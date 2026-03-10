package org.example.conferencemanagement.service;

import org.example.conferencemanagement.entity.Speaker;
import org.example.conferencemanagement.repository.SpeakerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class SpeakerService {

    private final SpeakerRepository speakerRepository;

    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }


}