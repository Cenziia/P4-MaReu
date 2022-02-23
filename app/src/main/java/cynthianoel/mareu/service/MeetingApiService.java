package cynthianoel.mareu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cynthianoel.mareu.model.Employee;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.model.MeetingRoom;

public interface MeetingApiService {

    /**
     * Get all Meeting Rooms
     *
     * @return {@link List}
     */
    List<MeetingRoom> getMeetingRooms();

    /**
     * Get all Employees
     *
     * @return {@link List}
     */
    List<Employee> getEmployees();

    /**
     * Get all meetings
     *
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    ArrayList<Meeting> getMeetingsFilteredByDate(Date date);

    List<MeetingRoom> getAvailableMeetingRoomsList();

    void addMeeting(Meeting meeting);

    ArrayList<Meeting> getMeetingFilteredByRoom(String room);
}