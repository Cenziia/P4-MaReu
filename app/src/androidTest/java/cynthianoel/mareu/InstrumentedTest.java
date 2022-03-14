package cynthianoel.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsEqual.equalTo;

import static cynthianoel.utils.RecyclerViewItemCountAssertion.withItemCount;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.MeetingRoom;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.ui.MeetingListActivity;
import cynthianoel.utils.DeleteViewAction;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedTest {

    // This is fixed
    private static final int ITEMS_COUNT = 3;

    private MeetingListActivity mActivity;
    private MeetingApiService mMeetingApiService;

    // For Filter by date test
    int year = 2022;
    int month = 3;
    int day = 14;

    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityRule =
            new ActivityTestRule(MeetingListActivity.class, false, true);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, IsNull.notNullValue());
        mMeetingApiService = DI.getMeetingApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void A_myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.meetingListRecyclerView))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * On the menu, when we select to filter by date, items with this date are shown if there's one or more
     */
    @Test
    public void B_myMeetingsListMenu_filterAction_shouldFilterByDate() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtrer par date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.meetingListRecyclerView)).check((withItemCount(3)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void C_myMeetingsList_deleteAction_shouldRemoveItem() {

        // Given : We remove the element at position 1
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
       onView(withId(R.id.meetingListRecyclerView))
               .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 2
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(2));
    }

    /**
     * After some parameters selected, create a new meeting
     */
    @Test
    public void D_myMeetingCreatorActivity_shouldCreateANewMeeting() {

        // Start activity button click and start of Add activity
        onView(withId(R.id.startAddMeetingActivity)).perform(click());
        onView(withId(R.id.add_meeting_activity)).check(matches(isDisplayed()));

        // Select a date
        onView(withId(R.id.btn_date_picker)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,12,25));
        onView(withId(android.R.id.button1)).perform(click());

        // Select a start time
        onView(withId(R.id.btn_hour_picker_start)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(2,10));
        onView(withId(android.R.id.button1)).perform(click());

        // Select a end time
        onView(withId(R.id.btn_hour_picker_end)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(23,59));
        onView(withId(android.R.id.button1)).perform(click());

        // Add a subject/title to the meeting
        onView(withId(R.id.ti_ed_subject)).perform(replaceText("Subject"));

        // Select a room
        onView(withId(R.id.spinner_rooms_available)).perform(click());
        onData(anything()).atPosition(3).perform(click());

        // Add a chip participant and press enter
        onView(withId(R.id.ti_ed_participants)).perform(typeText("karina@gmail.com"), pressImeActionButton());

        // Close keyboard
        closeSoftKeyboard();

        // Press add button to add this meeting and go back to meeting list
        onView(withId(R.id.btn_add_meeting)).perform(click());
        //chek meeting is displayed
        onView(withId(R.id.meetingListRecyclerView)).check((matches(hasChildCount(3))));
    }

    /**
     * On the menu, when we select to filter by room, items with this room are shown if there's one or more
     */
    @Test
    public void E_myMeetingsListMenu_filterAction_shouldFilterByRoom() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtrer par salle")).perform(click());
        onView(withText("Luigi")).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.meetingListRecyclerView)).check((withItemCount(2)));
    }
}

