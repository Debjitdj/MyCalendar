package tech.a0by0.mycalendar;

import java.util.Date;

/**
 * Created by Debjit on 23-Oct-17.
 */

public class DateModel {

    private Date dates;
    private String flag;
//    private boolean isCurrentDate;
//    private boolean isEventDate;

//    private ArrayList<Boolean> eventDate;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }
}
