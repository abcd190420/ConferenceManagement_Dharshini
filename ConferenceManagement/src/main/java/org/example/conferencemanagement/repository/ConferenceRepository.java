package org.example.conferencemanagement.repository;

import org.example.conferencemanagement.entity.Conference;
import org.example.conferencemanagement.entity.ConferenceLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference,Integer> {
//    Conference findByConfernceId (Integer conferenceId);
}
