package cynthianoel.mareu.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import cynthianoel.mareu.model.Employee;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.model.MeetingRoom;
import cynthianoel.mareu.service.MeetingApiService;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<MeetingRoom> meetingRooms = DummyMeetingRoomGenerator.generateMeetingRooms();
    private final List<Employee> employees = DummyEmployeeGenerator.generateEmployees();
    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

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

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /*@Override
    public boolean roomIsAvailable(String meetingRoom, Date mDate, DateTimeFormatter time) {
        for (Meeting meeting : meetings) {
            if (meeting.getMeetingRoom().equals(meetingRoom) && meeting.getDate().equals(mDate)) {
                DateTimeFormatter timeMeetingStart = DateTimeFormatter.parse(meeting.getHourStart(),
                        DateTimeFormatter);
                DateTimeFormatter timeMeetingEnd = DateTimeFormatter.parse(meeting.getHourEnd(),
                        DateTimeFormatter);
                if (time.(timeMeetingStart) && time.isBefore(timeMeetingEnd)) {
                    return false;
                }
            }
        }return true;
    }*/
}

