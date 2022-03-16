package cynthianoel.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.graphics.Color;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.List;

import cynthianoel.mareu.data.DummyMeetingGenerator;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.ui.MeetingListActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitTest {

    private MeetingApiService mMeetingApiService;

    @Before
    public void setup() {
        mMeetingApiService = DI.getMeetingApiService();
    }

    /**
    * Show meeting list
     */
    @Test
    public void testA_getMeetingsWithSuccess() {
        List<Meeting> meetings = mMeetingApiService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertEquals(meetings, expectedMeetings);
    }

    /**
     * Filter by room
     */
    @Test
    public void testB_getMeetingByRoom_withSuccess() {
        List<Meeting> meetingsByRoom1 = mMeetingApiService.getMeetingFilteredByRoom("Mario");
        assertFalse(meetingsByRoom1.isEmpty());
        List<Meeting> meetingsByRoom2 = mMeetingApiService.getMeetingFilteredByRoom("");
        assertTrue(meetingsByRoom2.isEmpty());
    }

    /**
     * Filter by date
     */
    @Test
    public void testC_getMeetingByDate_withSuccess() {
        List<Meeting> meetingsByDate1 = mMeetingApiService.getMeetingsFilteredByDate(Calendar.getInstance().getTime());
        assertFalse((meetingsByDate1.isEmpty()));
        List<Meeting> meetingsByDate2 = mMeetingApiService.getMeetingsFilteredByDate(MeetingListActivity.addDay(40));
        assertTrue((meetingsByDate2.isEmpty()));
    }

    /**
     * Delete a meeting
     */
    @Test
    public void testD_deleteNeighbourWithSuccess() {
        Meeting meetingToDelete = mMeetingApiService.getMeetings().get(0);
        mMeetingApiService.deleteMeeting(meetingToDelete);
        assertFalse(mMeetingApiService.getMeetings().contains(meetingToDelete));
    }

    /**
     * Create a meeting
     */
    @Test
    public void testE_createMeetingWithSuccess() {
        Meeting meetingToCreate = new Meeting("Réunion E", "test1@lamzone.com"+", "+"test2@lamzone.com", "Zelda",Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "Description", Calendar.getInstance().getTime(), Color.BLACK);
        mMeetingApiService.addMeeting(meetingToCreate);
        assertTrue(mMeetingApiService.getMeetings().contains(meetingToCreate));
    }
}