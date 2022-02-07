package cynthianoel.mareu.ui;

import static java.util.Objects.*;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.Calendar;

import cynthianoel.mareu.R;
import cynthianoel.mareu.databinding.ActivityMeetingListBinding;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.utils.DatePickerFragment;

public class MeetingListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityMeetingListBinding binding;
    private ArrayList<Meeting> mMeetings = new ArrayList<>();
    private final MeetingApiService mMeetingApiService = DI.getMeetingApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        startAddMeetingActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        MeetingListActivityAdapter meetingListActivityAdapter = new MeetingListActivityAdapter(mMeetings);
        binding.meetingListRecyclerView.setAdapter(meetingListActivityAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NotifyDataSetChanged")
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
                mMeetings.clear();
                mMeetings.addAll(mMeetingApiService.getMeetingFilteredByRoom(item.getTitle().toString()));
                requireNonNull(requireNonNull(binding.meetingListRecyclerView).getAdapter()).notifyDataSetChanged();
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

    @SuppressLint("NotifyDataSetChanged")
    public void resetFilter() {
        mMeetings.clear();
        mMeetings.addAll(mMeetingApiService.getMeetings());
        requireNonNull(requireNonNull(binding.meetingListRecyclerView).getAdapter()).notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        mMeetings.clear();
        mMeetings.addAll(mMeetingApiService.getMeetingsFilteredByDate(cal.getTime()));
        requireNonNull(binding.meetingListRecyclerView.getAdapter()).notifyDataSetChanged();
    }
}