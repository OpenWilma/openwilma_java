package org.openwilma.java.classes.schedule.wilmamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reservation {

    @SerializedName("ReservationID")
    private int reservationId;

    @SerializedName("ScheduleID")
    private int scheduleId;

    @SerializedName("Day")
    private int day;

    @SerializedName("Start")
    private String start;

    @SerializedName("End")
    private String end;

    @SerializedName("Color")
    private String hexColor;

    @SerializedName("X1")
    private int x1;
    @SerializedName("X2")
    private int x2;

    @SerializedName("Y1")
    private int y1;
    @SerializedName("Y2")
    private int y2;

    @SerializedName("Class")
    private String className;

    @SerializedName("Groups")
    private List<Group> groups;

    public Reservation(int reservationId, int scheduleId, int day, String start, String end, String hexColor, int x1, int x2, int y1, int y2, String className, List<Group> groups) {
        this.reservationId = reservationId;
        this.scheduleId = scheduleId;
        this.day = day;
        this.start = start;
        this.end = end;
        this.hexColor = hexColor;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.className = className;
        this.groups = groups;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
