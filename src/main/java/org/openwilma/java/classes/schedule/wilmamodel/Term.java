package org.openwilma.java.classes.schedule.wilmamodel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Term {

    @SerializedName("Name")
    private String name;

    @SerializedName("StartDate")
    private Date start;

    @SerializedName("EndDate")
    private Date end;

    public Term(String name, Date start, Date end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
