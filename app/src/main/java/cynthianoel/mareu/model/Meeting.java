package cynthianoel.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * Model object representing a Meeting
 */

public class Meeting implements Parcelable {
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
    private String subject, participants, meetingRoom, description;
    private long date;
    private long hourStart, hourEnd;
    private int circleColor;


    /**
     * Constructor
     *
     * @param subject String
     * @param participants String
     * @param meetingRoom String
     * @param hourStart Date
     * @param hourEnd Date
     * @param description String
     * @param date Date
     * @param circleColor int
     */
    public Meeting(String subject, String participants, String meetingRoom, Date hourStart, Date hourEnd, String description, Date date, int circleColor) {

        this.subject = subject;
        this.participants = participants;
        this.meetingRoom = meetingRoom;
        this.hourStart = hourStart.getTime();
        this.hourEnd = hourEnd.getTime();
        this.description = description;
        this.date = date.getTime();
        this.circleColor = circleColor;
    }

    protected Meeting(Parcel in) {
        subject = in.readString();
        participants = in.readString();
        meetingRoom = in.readString();
        description = in.readString();
        hourStart = in.readLong();
        hourEnd = in.readLong();
        date = in.readLong();
        circleColor = in.readInt();
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

    public Date getHourStart() {
        return new Date(hourStart);
    }

    public void setHourStart(Date hourStart1) {
        hourStart = hourStart1.getTime();
    }

    public Date getHourEnd() {
        return new Date(hourEnd);
    }

    public void setHourEnd(Date hourEnd1) {
        hourEnd = hourEnd1.getTime();
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

    public int getCircleColor() { return circleColor;}

    public void setCircleColor(int circleColor) { this.circleColor = circleColor; }

    @NonNull
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
        dest.writeLong(hourStart);
        dest.writeLong(hourEnd);
        dest.writeLong(date);
    }
}
