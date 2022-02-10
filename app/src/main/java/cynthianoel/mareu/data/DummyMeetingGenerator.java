package cynthianoel.mareu.data;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cynthianoel.mareu.model.Meeting;

public abstract class DummyMeetingGenerator {



    // TODO : Ajouter les participants à la première réunion

    private static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Réunion A", "à remplacer", "Peach","14h00","", "Réunion au sujet de l'application MaRéu.", Calendar.getInstance().getTime()),
            new Meeting("Réunion B", "paul@lamzone.com"+", "+"viviane@lamzone.com", "Mario","16h00","","Réunion au sujet de l'application MaRéu.", Calendar.getInstance().getTime()),
            new Meeting("Réunion C", "amandine@lamzone.com"+", "+"luc@lamzone.com", "Luigi","19h00","","Réunion au sujet de l'application MaRéu.", Calendar.getInstance().getTime())
            );

    static List<Meeting> generateMeetings(){
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
