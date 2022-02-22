package cynthianoel.mareu.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cynthianoel.mareu.R;
import cynthianoel.mareu.databinding.ActivityMeetingListBinding;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.utils.DatePickerFragment;

public class MeetingListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static Meeting mMeeting;
    private ActivityMeetingListBinding binding;
    private List<Meeting> mMeetings = new ArrayList<>();
    private final MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    //private Meeting mMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        startAddMeetingActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
ajouterJour();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFilter();
    }

    private void initUI() {
        binding = ActivityMeetingListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.meetingListRecyclerView.setLayoutManager(layoutManager);
        MeetingListActivityAdapter meetingListActivityAdapter = new MeetingListActivityAdapter((ArrayList<Meeting>) mMeetings);
        binding.meetingListRecyclerView.setAdapter(meetingListActivityAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.dateFilter):
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
                return true;
            case (R.id.roomFilter):
                return true;
            case (R.id.resetFilter):
                resetFilter();
                return true;
            default:
                //mMeetings.clear();
                //mMeetings.addAll(mMeetingApiService.getMeetingFilteredByRoom(item.getTitle().toString()));
                mMeetings = mMeetingApiService.getMeetingFilteredByRoom(item.getTitle().toString());
                //requireNonNull(requireNonNull(binding.meetingListRecyclerView).getAdapter()).notifyDataSetChanged();
                initRecyclerView();
                return true;
        }
    }

    private void initData() {;
        mMeetings = new ArrayList<>(mMeetingApiService.getMeetings());
    }

    private void startAddMeetingActivity() {
        binding.startAddMeetingActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), AddMeetingActivity.class);
            startActivity(intent);
        });

    }

    /*public void coloredCircle() {
        if (mMeeting.getDate() == Calendar.getInstance().getTime() && mMeeting.getHour() < ) {

        }
    }*/

    public void resetFilter() {
        //setCircle();
        mMeetings = mMeetingApiService.getMeetings();
        initRecyclerView();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        //mMeetings.clear();
        //mMeetings.addAll(mMeetingApiService.getMeetingsFilteredByDate(cal.getTime()));
        mMeetings = mMeetingApiService.getMeetingsFilteredByDate(cal.getTime());
        //requireNonNull(binding.meetingListRecyclerView.getAdapter()).notifyDataSetChanged();
        initRecyclerView();
    }


    /*public void main(String, String) try {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.FRANCE);
        Date firstDate = sdf.parse(Calendar.getInstance().getTime().toString());
        Date secondDate = sdf.parse(mMeeting.getHourStart().toString());

        long diff = requireNonNull(secondDate).getTime() - requireNonNull(firstDate).getTime();

        TimeUnit time = TimeUnit.DAYS;
        long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
        System.out.println("The difference in days is : " + diffrence);
    }*/

    /*static void
    findDifference(String start_date,
                   String end_date) {

        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.FRANCE);

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calucalte time difference
            // in milliseconds
            assert d2 != null;
            assert d1 != null;
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            System.out.print(
                    "Difference "
                            + "between two dates is: " + difference_In_Time);
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main()
    {
        // Given start Date
        String start_date
                = Calendar.getInstance().getTime().toString();;

        // Given end Date
        String end_date
                = mMeeting.getDate().toString();

        // Function Call
        findDifference(start_date, end_date);
    }*/
    /*public void setCircle(Meeting mMeeting) {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        //mMeeting.getHourStart().compareTo(date);
        //int hourNow = HOUR_OF_DAY;
        //String hourMeeting = mMeeting.getHourStart().toString();
        //int hour = Integer.parseInt(hourMeeting.substring(0, 2));
        //if (hourNow < hour) {
        //    findViewById(R.id.circleImg).setBackgroundColor(12);
        //}
        //System.out.println("Maintenant : " + hourNow + " ET APRES : " + hour);
        //long diff = date.getTime() - mMeeting.getHourStart().getTime();

        //if (diff < 1)
        //findViewById(R.id.circleImg).setBackgroundColor(getResources().getColor(R.color.black));

        for (mMeeting i : mMeetings)
    }*/

    public static Date ajouterJour() {
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date.getTime());
        int nbJour = 5;
        cal.add(Calendar.DATE, nbJour);
        System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAATTTTTTTTTTTTTTEEEEEEEEEEEEEEEEUUUUUUUUUUUUHHHHHHHH"+cal.getTime().toString());
        return cal.getTime();
    }

}