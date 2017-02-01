package com.scheduler.data;

import javax.persistence.*;

@Entity
@Table(name = "meeting_status")
public class MeetingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long meetingId;

    private String attendee;

    private boolean synced;

    public MeetingStatus() {
    }

    public MeetingStatus(Long meetingId, String attendee, boolean synced) {
        this.meetingId = meetingId;
        this.attendee = attendee;
        this.synced = synced;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getAttendee() {
        return attendee;
    }

    public void setAttendee(String attendee) {
        this.attendee = attendee;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }
}
