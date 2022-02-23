package cynthianoel.mareu.data;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cynthianoel.mareu.model.Meeting;

public abstract class DummyMeetingGenerator {

    protected static Calendar calA = getCalA();
    protected static Calendar calB = getCalB();
    protected static Calendar calC = getCalC();

    private static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Réunion A", "maxime@lamzone.com"+", "+"alex@lamzone.com", "Peach",calA.getTime(), Calendar.getInstance().getTime(), "Réunion au sujet de l'application MaRéu.", Calendar.getInstance().getTime(), Color.parseColor("#FF03FBC9")),
            new Meeting("Réunion B", "paul@lamzone.com"+", "+"viviane@lamzone.com", "Mario",calB.getTime(),Calendar.getInstance().getTime(),"Réunion au sujet de l'application MaRéu.", Calendar.getInstance().getTime(), Color.RED),
            new Meeting("Réunion C", "amandine@lamzone.com"+", "+"luc@lamzone.com", "Luigi",calC.getTime(),Calendar.getInstance().getTime(),"Réunion au sujet de l'application MaRéu.", Calendar.getInstance().getTime(), Color.YELLOW)
            );

    static List<Meeting> generateMeetings(){
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    public static Calendar getCalA() {
        calA = Calendar.getInstance();
        calA.set(Calendar.HOUR_OF_DAY,14);
        calA.set(Calendar.MINUTE,0);
        calA.set(Calendar.SECOND, 0);
        return calA;
    }

    public static Calendar getCalB() {
        calB = Calendar.getInstance();
        calB.set(Calendar.HOUR_OF_DAY,16);
        calB.set(Calendar.MINUTE,0);
        calB.set(Calendar.SECOND, 0);
        return calB;
    }

    public static Calendar getCalC() {
        calC = Calendar.getInstance();
        calC.set(Calendar.HOUR_OF_DAY,19);
        calC.set(Calendar.MINUTE,0);
        calC.set(Calendar.SECOND, 0);
        return calC;
    }

}
