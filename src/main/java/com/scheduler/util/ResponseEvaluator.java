package com.scheduler.util;

import com.scheduler.data.Meeting;
import com.scheduler.data.MeetingAccept;

import java.util.List;

/**
 * Created by shantanu on 18/01/17.
 */
public class ResponseEvaluator {
    public String getPreferredTime(Meeting meeting, List<MeetingAccept> accepts) {
        int countTime[] = new int[]{0, 0, 0, 0, 0};
        for (MeetingAccept accept : accepts) {
            if (accept.isTime1())
                countTime[0]++;
            if (accept.isTime2())
                countTime[1]++;
            if (accept.isTime3())
                countTime[2]++;
            if (accept.isTime4())
                countTime[3]++;
            if (accept.isTime5())
                countTime[4]++;

        }

        switch (getLargestIndex(countTime)) {
            case 0:
                return meeting.getTime1();
            case 1:
                return meeting.getTime2();
            case 2:
                return meeting.getTime3();
            case 3:
                return meeting.getTime4();
            case 4:
                return meeting.getTime5();
        }
        return "";
    }

    private int getLargestIndex(int count[]) {
        int largestIndex = 0, largest = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] > largest) {
                largest = count[i];
                largestIndex = i;
            }
        }
        return largestIndex;
    }
}
