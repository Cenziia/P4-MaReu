package cynthianoel.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Model object representing a Meeting Room
 */

public class MeetingRoom implements Parcelable {

    public static final Creator<MeetingRoom> CREATOR = new Creator<MeetingRoom>() {
        @Override
        public MeetingRoom createFromParcel(Parcel in) {
            return new MeetingRoom(in);
        }

        @Override
        public MeetingRoom[] newArray(int size) {
            return new MeetingRoom[size];
        }
    };
    /**
     * Identifier
     */
    private long id;
    /**
     * Full name
     */
    private String name;
    /**
     * Available
     */
    private boolean available;

    /**
     * Constructor
     *
     * @param id long
     * @param name String
     * @param available Boolean
     */
    public MeetingRoom(long id, String name, boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
    }

    protected MeetingRoom(Parcel in) {
        id = in.readLong();
        name = in.readString();
        available = in.readByte() != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRoom neighbour = (MeetingRoom) o;
        return Objects.equals(id, neighbour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeByte((byte) (available ? 1 : 0));
    }
}
