package org.openwilma.java.classes;

import com.google.gson.annotations.SerializedName;
import org.openwilma.java.enums.UserType;

public class User {
    @SerializedName("name")
    public String name;
    @SerializedName("type")
    public UserType type;
    @SerializedName("id")
    public int id;
    @SerializedName("school")
    public String school;

    public User(String name, UserType type, int id, String school) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.school = school;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
