package cynthianoel.mareu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cynthianoel.mareu.R;
import cynthianoel.mareu.databinding.ActivityMeetingDetailBinding;
import cynthianoel.mareu.model.Meeting;

// Meeting detail activity will be used later
public class MeetingDetailActivity extends AppCompatActivity {

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    protected static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH'h'mm", Locale.FRANCE);
    private static final String MEETING_STRING = "meeting";
    private static final String TAG = MeetingDetailActivity.class.getSimpleName();
    ActivityMeetingDetailBinding binding;

    public static Intent newInstance(final Context context, final Meeting meeting) {
        Log.d(TAG, "> newInstance");
        Intent intent = new Intent(context, MeetingDetailActivity.class);
        Log.d(TAG, "meeting : " + meeting);
        intent.putExtra(MEETING_STRING, meeting);
        Log.d(TAG, "< newInstance");
        return intent;
    }

    private void initUI() {
        binding = ActivityMeetingDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.activity_name_meeting_detail);

        Intent intent = getIntent();
        Meeting meeting = intent.getParcelableExtra(MEETING_STRING);
        binding.meetingDetailSubject.setText(meeting.getSubject());
        Date mHour = meeting.getHourStart();
        String hour = hourFormat.format(mHour);
        binding.meetingDetailHour.setText(String.format(getString(R.string.datetime_string_format), getString(R.string.meeting_start_selected), hour));

        binding.meetingDetailParticipants.setText(meeting.getParticipants());
        binding.meetingDetailRoom.setText(meeting.getMeetingRoom());
        binding.meetingDetailDescription.setText(meeting.getDescription());
        Date mDate = meeting.getDate();
        String date = dateFormat.format(mDate);
        binding.meetingDetailDate.setText(String.format(getString(R.string.datetime_string_format), getString(R.string.meeting_detail_date), date));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

}
