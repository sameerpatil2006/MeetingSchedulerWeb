package com.scheduler.data;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long meetingid;
    private String organizer;
    private String title;
    private String priority;
    private String duration;
    private String location;
    private String meetingDate;
    private String time1;
    private String time2;
    private String time3;
    private String time4;
    private String time5;
    private String finalTime;
    private boolean isFinalized;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_meeting", joinColumns = @JoinColumn(name = "meeting_id", referencedColumnName = "meetingid"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userid"))
    private Set<User> attendees;

    public Meeting() {
    }

    public Meeting(String organizer, String title, String priority, String duration, String location, String meetingDate, String time1, String time2, String time3, String time4, String time5, Set<User> attendees) {
        this.organizer = organizer;
        this.title = title;
        this.priority = priority;
        this.duration = duration;
        this.location = location;
        this.meetingDate = meetingDate;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.attendees = attendees;
    }

    public Long getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(Long meetingid) {
        this.meetingid = meetingid;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public String getTime5() {
        return time5;
    }

    public void setTime5(String time5) {
        this.time5 = time5;
    }

    public Set<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<User> attendees) {
        this.attendees = attendees;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean finalized) {
        isFinalized = finalized;
    }
}
