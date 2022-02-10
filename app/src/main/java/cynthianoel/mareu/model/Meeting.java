package cynthianoel.mareu.model;

import static java.lang.System.out;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

import cynthianoel.mareu.ui.AddMeetingActivity;

/**
 * Model object representing a Meeting Room
 */

public class Meeting implements Parcelable {
    private String subject, participants, meetingRoom, description, hourStart, hourEnd;
    //private Date date;
    private long date;

    /**
     * Constructor
     *
     * @param subject
     * @param participants
     * @param meetingRoom
     * @param hourStart
     * @param hourEnd
     * @param description
     * @param date
     */
    public Meeting(String subject, String participants, String meetingRoom, String hourStart, String hourEnd, String description, Date date) {

        this.subject = subject;
        this.participants = participants;
        this.meetingRoom = meetingRoom;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.description = description;
        this.date = date.getTime();
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public Date getDate() {
        return new Date(date);
    }

    public void setDate(Date date1) {
        date = date1.getTime();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Meeting{" +
                "subject = '" + subject + '\'' +
                ", participants = '" + participants + '\'' +
                ", meetingRoom = '" + meetingRoom + '\'' +
                ", hour = '" + hourStart + '\'' +
                ", hour = '" + hourEnd + '\'' +
                ", date = '" + date + '\'' +
                ", description = '" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subject);
        dest.writeString(participants);
        dest.writeString(meetingRoom);
        dest.writeString(description);
        //dest.writeString(String.valueOf(date));
        dest.writeString(hourStart);
        dest.writeString(hourEnd);
        //dest.writeLong(date);
        //dest.writeLong(date != null ? date.getTime() : -1);
       //dest.writeLong(date.getTime());
        //dest.writeSerializable(date);
        //dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeLong(date);
        System.out.println("OUZIBUFKUVYTVTFCTRFCTUYVYGVYGVYFCYVCYGVICYCYVYVGVKUVYTVIYVTVYJVYVYGV"+date);
    }

    protected Meeting(Parcel in) {
        subject = in.readString();
        participants = in.readString();
        meetingRoom = in.readString();
        description = in.readString();
        hourStart = in.readString();
        hourEnd = in.readString();
        //date = (Date) in.readSerializable();
        //date = new Date(in.readLong());
        //long tmpDate = in.readLong();
        //this.date = tmpDate == -1 ? null : new Date(tmpDate);
        date = in.readLong();


    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };
}
