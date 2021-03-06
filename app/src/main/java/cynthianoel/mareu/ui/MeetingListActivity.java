package cynthianoel.mareu.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cynthianoel.mareu.R;
import cynthianoel.mareu.databinding.ActivityMeetingListBinding;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.utils.DatePickerFragment;

public class MeetingListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private static final SimpleDateFormat sdfHour = new SimpleDateFormat("HH", Locale.FRANCE);
    private final MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    private ActivityMeetingListBinding binding;
    private List<Meeting> mMeetings = new ArrayList<>();

    // Static methods used for color circle. Used on view holder.
    public static Date addDay(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date addHour(int hours) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    public static String today() {
        return sdfDate.format(addDay(0));
    }

    public static String tomorrow() {
        return sdfDate.format(addDay(1));
    }

    public static String currentHour() {
        return sdfHour.format(addHour(0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        startAddMeetingActivity();
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

    // Init menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Menu's options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
                mMeetings = mMeetingApiService.getMeetingFilteredByRoom(item.getTitle().toString());
                initRecyclerView();
                return true;
        }
    }

    private void initData() {
        mMeetings = new ArrayList<>(mMeetingApiService.getMeetings());
    }

    // Open AddMeetingActivity
    private void startAddMeetingActivity() {
        binding.startAddMeetingActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), AddMeetingActivity.class);
            startActivity(intent);
        });
    }

    public void resetFilter() {
        mMeetings = mMeetingApiService.getMeetings();
        initRecyclerView();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        mMeetings = mMeetingApiService.getMeetingsFilteredByDate(cal.getTime());
        initRecyclerView();
    }

}