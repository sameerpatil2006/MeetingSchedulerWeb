package com.scheduler.repository;

import com.scheduler.data.Meeting;
import com.scheduler.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByOrganizer(String organizer);
}
