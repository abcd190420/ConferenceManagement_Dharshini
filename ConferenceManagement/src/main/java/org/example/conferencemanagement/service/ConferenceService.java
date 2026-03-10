package org.example.conferencemanagement.service;

import org.example.conferencemanagement.entity.Conference;
import org.example.conferencemanagement.repository.ConferenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;

    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public Conference saveConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    public Page<Conference> getAllConferences(Pageable pageable) {
        return conferenceRepository.findAll(pageable);
    }


    public Conference getConferenceById(int id) {
        return conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found"));
    }

    public Conference updateConference(int id, Conference updatedConference) {

        Conference conference = getConferenceById(id);

        conference.setTitle(updatedConference.getTitle());
        conference.setDescription(updatedConference.getDescription());
        conference.setLocation(updatedConference.getLocation());
        conference.setStartsAt(updatedConference.getStartsAt());
        conference.setEndsAt(updatedConference.getEndsAt());

        return conferenceRepository.save(conference);
    }

    public void deleteConference(int id) {
        conferenceRepository.deleteById(id);
    }
}