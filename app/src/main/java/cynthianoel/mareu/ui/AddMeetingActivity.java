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
    ActivityAddMeetingBinding binding;
    private final MeetingApiService mMeetingApiService = DI.getMeetingApiService();

    private Spinner spinnerMeetingRooms;

    private StringBuilder participants;

    String callback = "";

    private Calendar mCalDate;
    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

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

        Objects.requireNonNull(getSupportActionBar()).setTitle("Nouvelle Réunion");

        this.spinnerMeetingRooms = (Spinner) binding.spinnerRoomsAvailable;

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
                        MeetingRoom meetingRoom = (MeetingRoom) adapter.getItem(position);
                binding.roomsAvailableTxt.setText("Salle sélectionnée : ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.roomsAvailableTxt.setText("Sélectionner une salle");
            }
        });
    }

    private void addMeetingButtonListener() {
        binding.btnAddMeeting.setOnClickListener(v -> {
            onSubmit();
        });
    }

        private void dateTimeListeners() {
            binding.btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePicker = new DatePickerDialog(AddMeetingActivity.this, AddMeetingActivity.this, 2022, 1, 2);
                    datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePicker.show();
                }
            });
        binding.btnHourPickerStart.setOnClickListener(view -> {
            callback = "for_start_time";
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });
            binding.btnHourPickerEnd.setOnClickListener(view -> {
                callback = "for_end_time";
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timePicker");
            });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        mCalDate = Calendar.getInstance();
        mCalDate.set(year, month, dayOfMonth);;
        Date date1 = mCalDate.getTime();
        String date = dateFormat.format(date1);
        binding.btnDatePicker.setText(String.format("%s%s", "Date : ", date));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if (callback.equalsIgnoreCase("for_start_time") ) {
            mCalDate.set(Calendar.HOUR_OF_DAY, hour);
            mCalDate.set(Calendar.MINUTE, minute);
            mCalDate.set(Calendar.SECOND, 0);
            binding.btnHourPickerStart.setText(String.format("%s%s", "Début : ",getString(R.string.date_time, hour, minute)));
        }
        else if (callback.equalsIgnoreCase("for_end_time")) {
            binding.btnHourPickerEnd.setText(String.format("%s%s", "Fin : ",getString(R.string.date_time, hour, minute)));
        }
        callback = "";
    }

    private void addChips() {
        Chip chip = new Chip(this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setCheckable(false);
        chip.setChipIconResource(R.drawable.ic_baseline_delete_24);
        chip.setText(Objects.requireNonNull(binding.tiEdParticipants.getText()).toString());
        chip.setOnCloseIconClickListener(view -> binding.chipGroupParticipants.removeView(chip));
        binding.chipGroupParticipants.addView(chip);
        binding.tiEdParticipants.setText("");
    }

    private boolean checkingEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void addParticipantsEmail() {
        binding.tiEdParticipants.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (checkingEmail(view.getText())) {
                    addChips();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.email_invalid), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }

    private void onSubmit() {
        String date = binding.btnDatePicker.getText().toString();
        String hourStart = binding.btnHourPickerStart.getText().toString();
        String hourEnd = binding.btnHourPickerEnd.getText().toString();
        String subject = binding.tiEdSubject.getEditableText().toString();
        String meetingRoom = spinnerMeetingRooms.getSelectedItem().toString();
        String description = "description";

        participants = new StringBuilder();
        for (int i = 0; i < binding.chipGroupParticipants.getChildCount(); i++) {
            String email = ((Chip) binding.chipGroupParticipants.getChildAt(i)).getText().toString();
            participants.append(email);
            participants.append(", ");
        }

        if (date.isEmpty()) {
            binding.btnDatePicker.setError("Please choose a date");
        }
        if (hourStart.isEmpty()) {
            binding.btnHourPickerStart.setError("Please choose a start time");
        }
        if (hourEnd.isEmpty()) {
            binding.btnHourPickerEnd.setError("Please choose a end time");
        }
        if (subject.isEmpty()) {
            binding.tiEdSubject.setError("Please type a subject");
        }
        if (meetingRoom.isEmpty()) {
            binding.roomsAvailableTxt.setError("Please choose a room");
        }

        Date mDate = mCalDate.getTime();
        Date mTime = mCalDate.getTime();

        mMeetingApiService.addMeeting(new Meeting(subject, participants.toString(), meetingRoom, mTime, mTime, description, mDate, R.drawable.ic_baseline_circle_24));
        Toast.makeText(this, "Réunion créée !", Toast.LENGTH_SHORT).show();
        finish();

    }
}


