package org.openwilma.java.classes;

import com.google.gson.annotations.SerializedName;
import org.openwilma.java.enums.UserType;

public class Role extends User {

    @SerializedName("slug")
    private String slug;
    @SerializedName("classTeacher")
    private String classTeacher;
    @SerializedName("className")
    private String className;

    public Role(String name, UserType type, int id, String slug, String classTeacher, String className, String school) {
        super(name, type, id, school);
        this.slug = slug;
        this.classTeacher = classTeacher;
        this.className = className;
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
}
