package com.scheduler.data;

import javax.persistence.*;

@Entity
@Table(name = "meeting_accept")
public class MeetingAccept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long meetingId;
    private String attendee;
    private boolean time1;
    private boolean time2;
    private boolean time3;
    private boolean time4;
    private boolean time5;

    public MeetingAccept() {
    }

    public MeetingAccept(Long meetingId, String attendee, boolean time1, boolean time2, boolean time3, boolean time4, boolean time5) {
        this.meetingId = meetingId;
        this.attendee = attendee;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
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

    public boolean isTime1() {
        return time1;
    }

    public void setTime1(boolean time1) {
        this.time1 = time1;
    }

    public boolean isTime2() {
        return time2;
    }

    public void setTime2(boolean time2) {
        this.time2 = time2;
    }

    public boolean isTime3() {
        return time3;
    }

    public void setTime3(boolean time3) {
        this.time3 = time3;
    }

    public boolean isTime4() {
        return time4;
    }

    public void setTime4(boolean time4) {
        this.time4 = time4;
    }

    public boolean isTime5() {
        return time5;
    }

    public void setTime5(boolean time5) {
        this.time5 = time5;
    }
}
