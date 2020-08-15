package org.openwilma.java.classes.responses;

import com.google.gson.annotations.SerializedName;
import org.openwilma.java.classes.errors.WilmaError;

public class JSONErrorResponse {

    @SerializedName("error")
    private WilmaError wilmaError;
}
