package com.example.imingen.workoutpal.models;

import java.util.Date;

/**
 * Created by Marius on 15.10.2017.
 */

public class Session {

    private int sessionId;
    private Date dateOfSession;
    private Run run;




    public int getSessionId() {
        return sessionId;
    }

    public Date getDateOfSession() {
        return dateOfSession;
    }

    public Run getRun() {
        return run;
    }


}
