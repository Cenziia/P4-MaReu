package cynthianoel.mareu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cynthianoel.mareu.data.DummyMeetingGenerator;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;
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
}