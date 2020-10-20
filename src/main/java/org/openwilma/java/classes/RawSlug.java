package org.openwilma.java.classes;

import org.openwilma.java.enums.UserType;

public class RawSlug {
    private int id;
    private int type;
    private String slug;

    public RawSlug(int id, int type, String slug) {
        this.id = id;
        this.type = type;
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }



    public void setType(int type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
