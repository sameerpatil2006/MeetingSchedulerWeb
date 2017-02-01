package com.scheduler.repository;

import com.scheduler.data.MeetingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingStatusRepository extends JpaRepository<MeetingStatus, Long> {
    List<MeetingStatus> findByMeetingIdAndAttendee(Long meetingId, String attendee);
}
