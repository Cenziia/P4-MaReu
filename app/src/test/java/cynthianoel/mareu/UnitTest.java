package cynthianoel.mareu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cynthianoel.mareu.data.DummyMeetingGenerator;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.ui.MeetingListActivity;
import cynthianoel.mareu.ui.MeetingListActivityAdapter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class UnitTest {

    MeetingApiService mMeetingApiService;
    RecyclerView.Adapter mAdapter;

    @Before
    public void setup() {
        mMeetingApiService = DI.getMeetingApiService();
    }

    /**
    * Show meeting list
     */
    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = mMeetingApiService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertEquals(meetings, expectedMeetings);
    }

    /**
     * Delete a meeting
     */
    @Test
    public void deleteNeighbourWithSuccess() {
        //int meetingToDelete = mAdapter.getItemViewType(1);
        Meeting meetingToDelete = mMeetingApiService.getMeetings().get(0);
        System.out.println("MEETING A SUPPRIMER"+meetingToDelete);
        System.out.println("LISTE DES MEETINGS"+ mMeetingApiService.getMeetings());
        mMeetingApiService.deleteMeeting(meetingToDelete);
        assertFalse(mMeetingApiService.getMeetings().contains(meetingToDelete));
        System.out.println("LISTE APRES SUPPRESSION" + mMeetingApiService.getMeetings());
    }

    /**
     * Create a meeting
     */
    @Test
    public void createMeetingWithSuccess() {
        Meeting meetingToCreate = new Meeting("Réunion E", "test1@lamzone.com"+", "+"test2@lamzone.com", "Zelda",Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "Description", Calendar.getInstance().getTime(), Color.BLACK);
        mMeetingApiService.addMeeting(meetingToCreate);
        System.out.println(mMeetingApiService.getMeetings());
        assertTrue(mMeetingApiService.getMeetings().contains(meetingToCreate));
    }


    /**
     * Filter by room
     */
    @Test
    public void getMeetingByRoom_withSuccess() {
        ArrayList<Meeting> meetingsByRoom1 = mMeetingApiService.getMeetingFilteredByRoom("Peach");
        System.out.println("MEETING FILTER 1 => " + meetingsByRoom1 + " <=");
        assertFalse(meetingsByRoom1.isEmpty());
        ArrayList<Meeting> meetingsByRoom2 = mMeetingApiService.getMeetingFilteredByRoom("");
        System.out.println("MEETING FILTER 2 => " + meetingsByRoom2 + " <=");
        assertTrue(meetingsByRoom2.isEmpty());
    }

    /**
     * Filter by date
     */
    @Test
    public void getMeetingByDate_withSuccess() {
        ArrayList<Meeting> meetingsByDate1 = mMeetingApiService.getMeetingsFilteredByDate(Calendar.getInstance().getTime());
        System.out.println("MEETING FILTER 1 => " + meetingsByDate1 + " <=");
        assertFalse((meetingsByDate1.isEmpty()));
        ArrayList<Meeting> meetingsByDate2 = mMeetingApiService.getMeetingsFilteredByDate(MeetingListActivity.addDay(40));
        System.out.println("MEETING FILTER 2 => " + meetingsByDate2 + " <=");
        assertTrue((meetingsByDate2.isEmpty()));
    }
}