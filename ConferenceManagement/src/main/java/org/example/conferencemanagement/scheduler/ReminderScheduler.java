package org.example.conferencemanagement.scheduler;

import org.example.conferencemanagement.entity.Registration;
import org.example.conferencemanagement.repository.RegistrationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ReminderScheduler {

    private final RegistrationRepository registrationRepository;

    public ReminderScheduler(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void sendSessionReminders() {

        System.out.println("Reminder Job Running at: " + Instant.now());

        Instant now = Instant.now();
        Instant window = now.plus(60, ChronoUnit.MINUTES);

        List<Registration> registrations =
                registrationRepository.findUpcomingSessionsWithoutReminder(now, window);

        for (Registration r : registrations) {

            System.out.println("Sending reminder to: " + r.getAttendee().getEmail());

            r.setReminderSent(true);
            r.setReminderSentAt(Instant.now());

            registrationRepository.save(r);
        }
    }
}