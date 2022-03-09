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
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import static cynthianoel.utils.RecyclerViewItemCountAssertion.withItemCount;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.ui.MeetingListActivity;
import cynthianoel.utils.DeleteViewAction;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    // This is fixed
    private static int ITEMS_COUNT = 3;

    private MeetingListActivity mActivity;
    private MeetingApiService mMeetingApiService;

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
    public void myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.meetingListRecyclerView))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 1
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
       onView(withId(R.id.meetingListRecyclerView))
               .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 2
        onView(withId(R.id.meetingListRecyclerView)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * On the menu, when we select to filter by date, items with this date are shown if there's one or more
     */
    @Test
    public void myMeetingsListMenu_filterAction_shouldFilterByDate() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtrer par date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022, 3, 9));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.meetingListRecyclerView)).check((withItemCount(3)));
    }

    /**
     * On the menu, when we select to filter by room, items with this room are shown if there's one or more
     */
    @Test
    public void myMeetingsListMenu_filterAction_shouldFilterByRoom() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtrer par salle")).perform(click());
        onView(withText("Peach")).perform(click());
        onView(withId(R.id.meetingListRecyclerView)).check((withItemCount(1)));
    }

    /**
     *Meeting test created
     */
    @Test
    public void meetingTest_createdWithSuccess() {
        //test click on add fab and create activity is displayed
        onView(withId(R.id.list_meeting_fab_add)).perform(click());
        onView(withId(R.id.activity_add_meeting_layout)).check(matches(isDisplayed()));

        //test populate create meeting and create right back to list activity
        onView(withId(R.id.ti_ed_topic)).perform(replaceText("Topic test"));

        //chek return meeting list
        onView(withId(R.id.btn_picker_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,10,12));
        onView(withId(android.R.id.button1)).perform(click());

        //input room
        onView(withId(R.id.ti_ed_room)).perform(click());
        onData(equalTo("Paris")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(withId(R.id.ti_ed_participants)).perform(typeText("emailtest1@gmail.com"), pressImeActionButton());//press enter

        closeSoftKeyboard();

        //put button create
        onView(withId(R.id.button_create_meeting)).perform(click());
        //chek meeting is displayed
        onView(withId(R.id.list_meetings_recycler_view)).check((matches(hasChildCount(2))));
    }
}

