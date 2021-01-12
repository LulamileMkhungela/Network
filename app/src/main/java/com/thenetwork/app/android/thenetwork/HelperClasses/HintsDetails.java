package com.thenetwork.app.android.thenetwork.HelperClasses;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class HintsDetails {

    @ServerTimestamp
    private Date hintTime;
    private String hintedBy;

    public HintsDetails() {
        //Do not delete
    }

    public HintsDetails(String hintedBy) {
        this.hintTime = null;
        this.hintedBy = hintedBy;
    }

    public Date getHintTime() {
        return hintTime;
    }

    public String getHintedBy() {
        return hintedBy;
    }
}
