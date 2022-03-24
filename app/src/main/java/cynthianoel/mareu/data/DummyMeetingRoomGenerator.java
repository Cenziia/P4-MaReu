package cynthianoel.mareu.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cynthianoel.mareu.model.MeetingRoom;


public abstract class DummyMeetingRoomGenerator {

    public static final List<MeetingRoom> DUMMY_MEETING_ROOMS = Arrays.asList(
            new MeetingRoom(0,"Skype",true),
            new MeetingRoom(1, "Peach", true),
            new MeetingRoom(2,"Mario", true),
            new MeetingRoom(3, "Luigi", true),
            new MeetingRoom(4, "Yoshi", false),
            new MeetingRoom(5, "Toad", true),
            new MeetingRoom(6, "Zelda", true),
            new MeetingRoom(7, "Link", true),
            new MeetingRoom(8, "Impa", true),
            new MeetingRoom(9, "Epona", true)
        );

    static List<MeetingRoom> generateMeetingRooms() {
        return new ArrayList<>(DUMMY_MEETING_ROOMS);
    }

}
