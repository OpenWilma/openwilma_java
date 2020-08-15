package org.openwilma.java.classes.errors;

import com.google.gson.annotations.SerializedName;

public class WilmaError extends Error {

    @SerializedName("id")
    private String errorID;
    @SerializedName("description")
    private String description;
    @SerializedName("whatnext")
    private String whatsnext;

    public WilmaError(String message, String errorID, String description, String whatsnext) {
        super(message, ErrorType.WilmaError);
        this.errorID = errorID;
        this.description = description;
        this.whatsnext = whatsnext;
    }

    public String getErrorID() {
        return errorID;
    }

    public void setErrorID(String errorID) {
        this.errorID = errorID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWhatsnext() {
        return whatsnext;
    }

    public void setWhatsnext(String whatsnext) {
        this.whatsnext = whatsnext;
    }
}
