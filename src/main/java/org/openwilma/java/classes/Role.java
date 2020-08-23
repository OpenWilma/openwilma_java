package org.openwilma.java.classes;

import com.google.gson.annotations.SerializedName;

public class Role {

    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("slug")
    private String slug;
    @SerializedName("classTeacher")
    private String classTeacher;
    @SerializedName("className")
    private String className;
    @SerializedName("school")
    private String school;

    public Role(String name, int type, int id, String slug, String classTeacher, String className, String school) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.slug = slug;
        this.classTeacher = classTeacher;
        this.className = className;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
