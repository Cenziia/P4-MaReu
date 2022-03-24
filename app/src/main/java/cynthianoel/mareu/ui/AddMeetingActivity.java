package cynthianoel.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cynthianoel.mareu.R;
import cynthianoel.mareu.databinding.ActivityAddMeetingBinding;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.model.MeetingRoom;
import cynthianoel.mareu.service.MeetingApiService;
import cynthianoel.mareu.utils.TimePickerFragment;

public class AddMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private final MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    ActivityAddMeetingBinding binding;
    String callback = "";
    private final String START_TIME = "for_start_time";
    private final String END_TIME = "for_end_time";
    private Spinner spinnerMeetingRooms;
    private StringBuilder participants;
    private Calendar mCalDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        addMeetingButtonListener();
        addParticipantsEmail();
        dateTimeListeners();
    }

    private void initUI() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.new_meeting);

        this.spinnerMeetingRooms = binding.spinnerRoomsAvailable;

        MeetingRoom[] meetingRooms = mMeetingApiService.getAvailableMeetingRoomsList().toArray(new MeetingRoom[0]);

        // (@resource) android.R.layout.simple_spinner_item:
        //   The resource ID for a layout file containing a TextView to use when instantiating views.
        //    (Layout for one ROW of Spinner)
        ArrayAdapter<MeetingRoom> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                meetingRooms);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerMeetingRooms.setAdapter(adapter);

        // When user select a List-Item.
        this.spinnerMeetingRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.roomsAvailableTxt.setText(R.string.add_meeting_room_selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.roomsAvailableTxt.setText(R.string.select_a_room);
            }
        });
    }

    private void addMeetingButtonListener() {
        binding.btnAddMeeting.setOnClickListener(v -> onSubmit());
    }

    // Listeners for date and times. Callback is used to differentiate start time and end time for Time picker
    private void dateTimeListeners() {
        binding.btnDatePicker.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(AddMeetingActivity.this, AddMeetingActivity.this, 2022, 1, 2);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.show();
        });
        binding.btnHourPickerStart.setOnClickListener(view -> {
            callback = START_TIME;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });
        binding.btnHourPickerEnd.setOnClickListener(view -> {
            callback = END_TIME;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        mCalDate = Calendar.getInstance();
        mCalDate.set(year, month, dayOfMonth);
        Date date1 = mCalDate.getTime();
        String date = dateFormat.format(date1);
        binding.btnDatePicker.setText(String.format(getString(R.string.datetime_string_format), getString(R.string.date), date));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if (callback.equalsIgnoreCase(START_TIME)) {
            mCalDate.set(Calendar.HOUR_OF_DAY, hour);
            mCalDate.set(Calendar.MINUTE, minute);
            mCalDate.set(Calendar.SECOND, 0);
            binding.btnHourPickerStart.setText(String.format(getString(R.string.datetime_string_format), getString(R.string.meeting_start_selected), getString(R.string.date_time, hour, minute)));
        } else if (callback.equalsIgnoreCase(END_TIME)) {
            binding.btnHourPickerEnd.setText(String.format(getString(R.string.datetime_string_format), getString(R.string.meeting_end_selected), getString(R.string.date_time, hour, minute)));
        }
        callback = "";
    }

    // Transform inputted text to a chip
    private void addChips() {
        Chip chip = new Chip(this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setCheckable(false);
        chip.setChipIconResource(R.drawable.ic_baseline_person_24);
        chip.setText(Objects.requireNonNull(binding.tiEdParticipants.getText()).toString());
        chip.setOnCloseIconClickListener(view -> binding.chipGroupParticipants.removeView(chip));
        binding.chipGroupParticipants.addView(chip);
        binding.tiEdParticipants.setText("");
    }

    // Check if participants' chip is an email
    private boolean checkingEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Add inputted text to a chip on chips group
    private void addParticipantsEmail() {
        binding.tiEdParticipants.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (checkingEmail(view.getText())) {
                    addChips();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.add_meeting_email_invalid), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }

    // Build and add a meeting
    private void onSubmit() {
        String subject = binding.tiEdSubject.getEditableText().toString();
        String meetingRoom = spinnerMeetingRooms.getSelectedItem().toString();
        String description = getString(R.string.add_meeting_meeting_description);

        checkErrors();

        Date mDate = mCalDate.getTime();
        // Will be used to eventually create slots later
        Date mTime = mCalDate.getTime();

        if (checkErrors()) {
            mMeetingApiService.addMeeting(new Meeting(subject, participants.toString(), meetingRoom, mTime, mTime, description, mDate, R.drawable.ic_baseline_circle_24));
            Toast.makeText(this, R.string.add_meeting_meeting_created, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Check if inputs are filled and if there's at least one participant's email
    private boolean checkErrors() {
        String date = binding.btnDatePicker.getText().toString();
        String hourStart = binding.btnHourPickerStart.getText().toString();
        String hourEnd = binding.btnHourPickerEnd.getText().toString();
        String subject = binding.tiEdSubject.getEditableText().toString();
        String meetingRoom = spinnerMeetingRooms.getSelectedItem().toString();

        if (date.isEmpty()) {
            binding.btnDatePicker.setError(getString(R.string.add_meeting_date_error));
            return false;
        }
        if (hourStart.isEmpty()) {
            binding.btnHourPickerStart.setError(getString(R.string.add_meeting_start_time_error));
            return false;
        }
        if (hourEnd.isEmpty()) {
            binding.btnHourPickerEnd.setError(getString(R.string.add_meeting_end_time_error));
            return false;
        }
        if (subject.isEmpty()) {
            binding.tiEdSubject.setError(getString(R.string.add_meeting_subject_error));
            return false;
        }
        if (meetingRoom.isEmpty()) {
            binding.roomsAvailableTxt.setError(getString(R.string.add_meeting_room_error));
            return false;
        }

        participants = new StringBuilder();
        for (int i = 0; i < binding.chipGroupParticipants.getChildCount(); i++) {
            String email = ((Chip) binding.chipGroupParticipants.getChildAt(i)).getText().toString();
            participants.append(email);
            participants.append(", ");
        }

        if (binding.chipGroupParticipants.getChildCount() < 1) {
            binding.tiEdParticipants.setError(getString(R.string.add_meeting_participant_email_error));
            return false;
        }
        else {
            return true;
        }
    }
}


