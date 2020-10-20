package org.openwilma.java.classes.schedule.wilmamodel;

import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("Id")
    private int id;

    @SerializedName("Caption")
    private String codeName;

    @SerializedName("LongCaption")
    private String name;

    @SerializedName("ScheduleVisible")
    private boolean scheduleVisible;

    public Room(int id, String codeName, String name, boolean scheduleVisible) {
        this.id = id;
        this.codeName = codeName;
        this.name = name;
        this.scheduleVisible = scheduleVisible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isScheduleVisible() {
        return scheduleVisible;
    }

    public void setScheduleVisible(boolean scheduleVisible) {
        this.scheduleVisible = scheduleVisible;
    }
}
