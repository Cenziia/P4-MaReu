package cynthianoel.mareu.model;

import static java.lang.System.out;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

/**
 * Model object representing a Meeting Room
 */

public class Meeting implements Parcelable {
    private String subject, participants, meetingRoom, description, hourStart, hourEnd;
    private Date mDate;

    /**
     * Constructor
     *
     * @param subject
     * @param participants
     * @param meetingRoom
     * @param hourStart
     * @param hourEnd
     * @param description
     */
    public Meeting(String subject, String participants, String meetingRoom, String hourStart, String hourEnd, String description, Date mDate) {

        this.subject = subject;
        this.participants = participants;
        this.meetingRoom = meetingRoom;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.description = description;
        this.mDate = new Date();
    }

    protected Meeting(Parcel in) {
        subject = in.readString();
        participants = in.readString();
        meetingRoom = in.readString();
        description = in.readString();
        hourStart = in.readString();
        hourEnd = in.readString();
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
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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
                ", date = '" + mDate + '\'' +
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
        //dest.writeString(String.valueOf(mDate));
        dest.writeString(hourStart);
        dest.writeString(hourEnd);
        //dest.writeLong(mDate != null ? mDate.getDate() : -1);

    }
}
