package org.openwilma.java.classes.responses;

import com.google.gson.annotations.SerializedName;
import org.openwilma.java.classes.schedule.wilmamodel.Reservation;
import org.openwilma.java.classes.schedule.wilmamodel.Term;

import java.util.List;

public class ScheduleResponse {

    @SerializedName("Schedule")
    private List<Reservation> reservations;

    @SerializedName("Terms")
    private List<Term> terms;

    public ScheduleResponse(List<Reservation> reservations, List<Term> terms) {
        this.reservations = reservations;
        this.terms = terms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
}
