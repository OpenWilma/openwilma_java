package org.openwilma.java.classes.schedule.wilmamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {

    @SerializedName("Id")
    private int id;

    @SerializedName("courseId")
    private int courseId;

    @SerializedName("ShortCaption")
    private String shortCaption;

    @SerializedName("Caption")
    private String caption;

    @SerializedName("FullCaption")
    private String fullCaption;

    @SerializedName("Class")
    private String className;

    @SerializedName("Teachers")
    private List<Teacher> teachers;

    @SerializedName("Rooms")
    private List<Room> rooms;

    public Group(int id, int courseId, String shortCaption, String caption, String fullCaption, String className, List<Teacher> teachers, List<Room> rooms) {
        this.id = id;
        this.courseId = courseId;
        this.shortCaption = shortCaption;
        this.caption = caption;
        this.fullCaption = fullCaption;
        this.className = className;
        this.teachers = teachers;
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getShortCaption() {
        return shortCaption;
    }

    public void setShortCaption(String shortCaption) {
        this.shortCaption = shortCaption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFullCaption() {
        return fullCaption;
    }

    public void setFullCaption(String fullCaption) {
        this.fullCaption = fullCaption;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
