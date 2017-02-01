package com.scheduler.repository;

import com.scheduler.data.MeetingAccept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingAcceptRepository extends JpaRepository<MeetingAccept, Long> {
    List<MeetingAccept> findByMeetingIdAndAttendee(Long meetingId, String attendee);

    List<MeetingAccept> findByMeetingId(Long meetingId);
}
