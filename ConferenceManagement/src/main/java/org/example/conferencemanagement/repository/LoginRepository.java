package org.example.conferencemanagement.repository;

import org.example.conferencemanagement.entity.ConferenceLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<ConferenceLogin,Long> {
    ConferenceLogin findByUserName (String userName);
    ConferenceLogin findByEmailId (String emailId);
}
