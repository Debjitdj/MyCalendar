package tech.a0by0.mycalendar;

/**
 * Created by Debjit on 23-Oct-17.
 */

public class EventModel {

    private int Id;
    private String strDate;
    private String strStartTime;
    private String strEndTime;
    private String strName;
    private String eventType;  // "User" if added by user and "Default" otherwise
    private int image = -1;

    public EventModel(String eventType){
        this.eventType = eventType;
    }


    public EventModel(int Id, String strDate, String strStartTime, String strEndTime, String strName, String eventType) {
        this.Id = Id;
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strName = strName;
        this.eventType = eventType;
    }

    public EventModel(int strId, String strDate, String strStartTime, String strEndTime, String strName, int image, String eventType) {
        this.Id = strId;
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strName = strName;
        this.image = image;
        this.eventType = eventType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}

