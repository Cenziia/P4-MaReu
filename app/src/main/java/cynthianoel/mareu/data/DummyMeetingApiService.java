package cynthianoel.mareu.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.model.MeetingRoom;
import cynthianoel.mareu.service.MeetingApiService;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<MeetingRoom> meetingRooms = DummyMeetingRoomGenerator.generateMeetingRooms();
    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    /**
     * Get meetings' list
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * Get a list of meetings filtered by date
     */
    @Override
    public ArrayList<Meeting> getMeetingsFilteredByDate(Date date) {
        ArrayList<Meeting> meetingsByDate = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        for (int i = 0; i < meetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(meetings.get(i).getDate());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) meetingsByDate.add(meetings.get(i));
        }
        return meetingsByDate;
    }

    /**
     * Get a list of meetings filtered by room
     */
    @Override
    public ArrayList<Meeting> getMeetingFilteredByRoom(String room) {
        ArrayList<Meeting> meetingsByRoom = new ArrayList<>();
        for(Meeting i : meetings) {
            if (i.getMeetingRoom().equals(room)) {
                meetingsByRoom.add(i);
            }
        }
        return meetingsByRoom;
    }

    /**
     * Get a dynamic list of available meetings' rooms
     */
    @Override
    public List<MeetingRoom> getAvailableMeetingRoomsList() {
        ArrayList<MeetingRoom> meetingsAvailable = new ArrayList<>();

        for (MeetingRoom i : meetingRooms) {
            if (i.getAvailable()) {
                meetingsAvailable.add(i);
            }
        }
                return meetingsAvailable;
        }

    /**
     * Add a meeting
     */
    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * Delete a meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
}

