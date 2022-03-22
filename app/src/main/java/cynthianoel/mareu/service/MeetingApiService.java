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
     * @return List
     */
    List<MeetingRoom> getMeetingRooms();

    /**
     * Get all Employees
     * @return List
     */
    List<Employee> getEmployees();

    /**
     * Get all meetings
     * @return List
     */
    List<Meeting> getMeetings();

    /**
     * Get meetings filtered by date
     * @param date Date
     * @return List
     */
    ArrayList<Meeting> getMeetingsFilteredByDate(Date date);

    /**
     * Get available meetings list
     * @return List
     */
    List<MeetingRoom> getAvailableMeetingRoomsList();

    /**
     * Add a meeting
     * @param meeting Meeting
     */
    void addMeeting(Meeting meeting);

    /**
     * Delete a meeting
     * @param meeting Meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Get meetings filtered by room
     * @param room String
     * @return List
     */
    ArrayList<Meeting> getMeetingFilteredByRoom(String room);
}