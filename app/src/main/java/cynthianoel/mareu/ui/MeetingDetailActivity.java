package cynthianoel.mareu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cynthianoel.mareu.databinding.ActivityMeetingDetailBinding;
import cynthianoel.mareu.model.Meeting;

public class MeetingDetailActivity extends AppCompatActivity {

    ActivityMeetingDetailBinding binding;
    private static final String MEETING_STRING = "meeting";
    private static final String TAG = MeetingDetailActivity.class.getSimpleName();

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    protected static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH'h'mm", Locale.FRANCE);

    public static Intent newInstance(final Context context, final Meeting meeting) {
        Log.d(TAG, "> newInstance");
        Intent intent = new Intent(context, MeetingDetailActivity.class);
        Log.d(TAG, "meeting : " + meeting);
        intent.putExtra(MEETING_STRING, (Parcelable) meeting);
        Log.d(TAG, "< newInstance");
        return intent;
    }

    private void initUI() {
        binding = ActivityMeetingDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setTitle("Détails Réunion");

        Intent intent = getIntent();
        Meeting meeting = (Meeting) intent.getParcelableExtra(MEETING_STRING);
        binding.meetingDetailSubject.setText(meeting.getSubject());
        Date mHour = meeting.getHourStart();
        String hour = hourFormat.format(mHour);
        binding.meetingDetailHour.setText(String.format("%s%s", " Début : ", hour));

        binding.meetingDetailParticipants.setText(meeting.getParticipants());
        binding.meetingDetailRoom.setText(meeting.getMeetingRoom());
        binding.meetingDetailDescription.setText(meeting.getDescription());
        Date mDate = meeting.getDate();
        String date = dateFormat.format(mDate);
        binding.meetingDetailDate.setText(String.format("%s%s", "Date : ", date));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

}
