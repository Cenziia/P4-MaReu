package cynthianoel.mareu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cynthianoel.mareu.databinding.ActivityMeetingDetailBinding;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;

public class MeetingDetailActivity extends AppCompatActivity {

    ActivityMeetingDetailBinding binding;
    private final MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    private static final String MEETING_STRING = "meeting";
    private static final String TAG = MeetingDetailActivity.class.getSimpleName();

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
        binding.meetingDetailDate.setText((CharSequence) meeting.getDate());
        binding.meetingDetailHour.setText(meeting.getHourStart());
        binding.meetingDetailParticipants.setText(meeting.getParticipants());
        binding.meetingDetailRoom.setText(meeting.getMeetingRoom());
        binding.meetingDetailDescription.setText(meeting.getDescription());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

}
