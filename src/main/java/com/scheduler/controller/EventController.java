package com.scheduler.controller;

import com.scheduler.data.Meeting;
import com.scheduler.data.MeetingAccept;
import com.scheduler.data.MeetingStatus;
import com.scheduler.data.User;
import com.scheduler.repository.MeetingAcceptRepository;
import com.scheduler.repository.MeetingRepository;
import com.scheduler.repository.MeetingStatusRepository;
import com.scheduler.repository.UserRepository;
import com.scheduler.response.MeetingResponse;
import com.scheduler.response.UserResponse;
import com.scheduler.util.ResponseEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class EventController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingAcceptRepository meetingAcceptRepository;

    @Autowired
    private MeetingStatusRepository meetingStatusRepository;

    @RequestMapping(value = "createMeeting", method = RequestMethod.POST)
    public String createMeeting(@RequestParam("organizer") String organizer, @RequestParam("title") String title, @RequestParam("priority") String priority, @RequestParam("duration") String duration,
                                @RequestParam("location") String location, @RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("attendees") String attendees) {


        Set<User> attendeesList = new HashSet<>();

        String[] attendeesId = attendees.split(",");
        for (String email : attendeesId) {
            attendeesList.add(userRepository.findByEmail(email));
        }


        String[] temp = time.split(",");
        String[] timeList = new String[5];

        for (int i = 0; i < temp.length; i++) {
            timeList[i] = temp[i];
        }

        Meeting meeting = new Meeting(organizer, title, priority, duration, location, date, timeList[0], timeList[1], timeList[2], timeList[3], timeList[4], attendeesList);
        meetingRepository.save(meeting);

        return "success";
    }

    @RequestMapping(value = "getMeetings", method = RequestMethod.GET)
    public List<MeetingResponse> getMyEvents(@RequestParam("organizer") String organizer) {
        ResponseEvaluator responseEvaluator = new ResponseEvaluator();

        List<Meeting> meetings = meetingRepository.findByOrganizer(organizer);
        List<MeetingResponse> result = new ArrayList<>();

        List<MeetingAccept> accepts = new ArrayList<>();
        for (Meeting m : meetings) {
            Set<UserResponse> attendeesResp = new HashSet<>();
            for (User u : m.getAttendees()) {
                List<MeetingAccept> meetingAccept = meetingAcceptRepository.findByMeetingIdAndAttendee(m.getMeetingid(), u.getUsername());
                UserResponse userResponse = new UserResponse(u.getFullname(), u.getEmail());
                if (meetingAccept.size() > 0) {
                    MeetingAccept accept = meetingAccept.get(0);
                    accepts.add(accept);
                    userResponse.setTime1(accept.isTime1());
                    userResponse.setTime2(accept.isTime2());
                    userResponse.setTime3(accept.isTime3());
                    userResponse.setTime4(accept.isTime4());
                    userResponse.setTime5(accept.isTime5());
                }
                attendeesResp.add(userResponse);
            }

            MeetingResponse res = new MeetingResponse(m.getMeetingid(), m.getOrganizer(), m.getTitle(), m.getPriority(), m.getDuration(), m.getLocation(), m.getMeetingDate(),
                    m.getTime1(), m.getTime2(), m.getTime3(), m.getTime4(), m.getTime5(), m.getFinalTime(), m.isFinalized(), attendeesResp);
            res.setPreferredTime(responseEvaluator.getPreferredTime(m, accepts));
            result.add(res);
        }

        return result;
    }

    @RequestMapping(value = "getInvitations", method = RequestMethod.GET)
    public List<MeetingResponse> getInvitations(@RequestParam("user") String user) {
        User userObj = userRepository.findByUsername(user);
        List<MeetingResponse> result = new ArrayList<>();
        for (Meeting m : userObj.getMeetings()) {

            List<MeetingAccept> accepted = meetingAcceptRepository.findByMeetingIdAndAttendee(m.getMeetingid(), user);
            if (!accepted.isEmpty()) {
                continue;  // response already submitted
            }
            Set<UserResponse> attendeesResp = new HashSet<>();
            for (User u : m.getAttendees()) {
                attendeesResp.add(new UserResponse(u.getFullname(), u.getEmail()));
            }
            MeetingResponse res = new MeetingResponse(m.getMeetingid(), m.getOrganizer(), m.getTitle(), m.getPriority(), m.getDuration(), m.getLocation(), m.getMeetingDate(),
                    m.getTime1(), m.getTime2(), m.getTime3(), m.getTime4(), m.getTime5(), m.getFinalTime(), m.isFinalized(), attendeesResp);
            result.add(res);
        }

        return result;
    }

    @RequestMapping(value = "acceptInvite", method = RequestMethod.POST)
    public String acceptInvitation(@RequestParam("username") String username, @RequestParam("meetingid") Long meetingid,
                                   @RequestParam("time1") boolean time1, @RequestParam("time2") boolean time2, @RequestParam("time3") boolean time3,
                                   @RequestParam("time4") boolean time4, @RequestParam("time5") boolean time5) {
        MeetingAccept meetingAccept = new MeetingAccept(meetingid, username, time1, time2, time3, time4, time5);
        meetingAcceptRepository.save(meetingAccept);
        return "success";
    }

    @RequestMapping(value = "finalize", method = RequestMethod.POST)
    public String finalizeMeeting(@RequestParam("meetingid") Long meetingid) {
        ResponseEvaluator responseEvaluator = new ResponseEvaluator();

        Meeting meeting = meetingRepository.findOne(meetingid);
        List<MeetingAccept> meetingAccepts = meetingAcceptRepository.findByMeetingId(meetingid);
        String preferredTime = responseEvaluator.getPreferredTime(meeting, meetingAccepts);

        meeting.setFinalized(true);
        meeting.setFinalTime(preferredTime);

        meetingRepository.save(meeting);

        return "success";
    }

    @RequestMapping(value = "syncMeetings", method = RequestMethod.GET)
    public List<MeetingResponse> syncMeetings(@RequestParam("user") String username) {
        User user = userRepository.findByUsername(username);
        Set<Meeting> meetings = user.getMeetings();

        List<MeetingResponse> result = new ArrayList<>();

        for (Meeting m : meetings) {
            if (!m.isFinalized())
                continue;

            List<MeetingStatus> meetingStatus = meetingStatusRepository.findByMeetingIdAndAttendee(m.getMeetingid(), user.getUsername());
            if (meetingStatus.size() > 0 && meetingStatus.get(0).isSynced())
                continue;

            MeetingResponse res = new MeetingResponse(m.getMeetingid(), m.getOrganizer(), m.getTitle(), m.getPriority(), m.getDuration(), m.getLocation(), m.getMeetingDate(),
                    m.getTime1(), m.getTime2(), m.getTime3(), m.getTime4(), m.getTime5(), m.getFinalTime(), m.isFinalized(), null);
            result.add(res);

            meetingStatusRepository.save(new MeetingStatus(m.getMeetingid(), user.getUsername(), true));
        }

        return result;
    }

}
