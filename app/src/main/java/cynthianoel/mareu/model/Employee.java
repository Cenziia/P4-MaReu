package cynthianoel.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Model object representing a Employee for later
 */

public class Employee implements Parcelable {

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
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
     * Email address
     */
    private String email;
    /**
     * Available
     */
    private boolean available;

    /**
     * Constructor
     *
     * @param id long
     * @param name String
     * @param email String
     * @param available Boolean
     */
    public Employee(long id, String name, String email, boolean available) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.available = available;
    }

    protected Employee(Parcel in) {
        id = in.readLong();
        name = in.readString();
        email = in.readString();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee neighbour = (Employee) o;
        return Objects.equals(id, neighbour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", name = '" + name + '\'' +
                ", email = '" + email + '\'' +
                ", available = '" + available + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeByte((byte) (available ? 1 : 0));
    }
}
