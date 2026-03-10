package org.example.conferencemanagement.repository;

import org.example.conferencemanagement.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    List<Session> findByConferenceId(Integer conferenceId);

}