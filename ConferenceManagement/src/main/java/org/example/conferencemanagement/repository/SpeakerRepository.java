package org.example.conferencemanagement.repository;

import org.example.conferencemanagement.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Integer> {
}