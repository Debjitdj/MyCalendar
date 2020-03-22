package tech.a0by0.mycalendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TimePicker;

import java.util.Calendar;

import stanford.androidlib.SimpleActivity;

import static android.content.Context.MODE_PRIVATE;


public class Add_New_Event extends SimpleActivity {

    private int startHourMinute;
    private int endHourMinute;

    private int shour,ehour,sminute,eminute;

    private SQLiteDatabase events = null;
    private Button startingtimebutton, endingtimebutton;
    private EditText event_description_monthview;

    private RemoteViews remoteViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__event);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        $TV(R.id.Header_monthview).setText("Date: "+ date );

        initi();
        actionListener();
    }

    private void initi(){
        startingtimebutton = (Button) findViewById(R.id.startingtimebutton);
        endingtimebutton = (Button) findViewById(R.id.endingtimebutton);
        event_description_monthview = (EditText) findViewById(R.id.event_description_monthview);

    }

    private void actionListener(){

        startingtimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime("sTime");
            }
        });

        endingtimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime("eTime");
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void add_event_monthview(View view) {

        String StartingTime = startingtimebutton.getText().toString();
        String EndingTime = endingtimebutton.getText().toString();
        String EventDescription = event_description_monthview.getText().toString();

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");


        if(StartingTime.equals("Start Time")){
            toast("Please select Starting Time!");
        }
        else if(EndingTime.equals("End Time")){
            toast("Please select Ending Time!");
        }
        else if(EventDescription.isEmpty()){
            toast("Please add event Description!");
        }
        else if(startHourMinute > endHourMinute){
            toast("Starting time can't be greater than Ending time!");
        }
        else{
            events = this.openOrCreateDatabase("events",MODE_PRIVATE,null);

    //        events.execSQL("INSERT INTO UserEvents (Date, StartingTime, StartHour, StartMinute, EndingTime, EndHour, EndMinute, Description) VALUES ('"
    //                + date + "','" + StartingTime + "','" + shour + "','"+ sminute + "','"  + EndingTime + "','" + ehour + "','"+ eminute + "','" + EventDescription + "');");

            ContentValues values = new ContentValues();

            values.put("Date", date);
            values.put("StartingTime", StartingTime);
            values.put("StartHour", shour);
            values.put("StartMinute", sminute);
            values.put("EndingTime", EndingTime);
            values.put("EndHour", ehour);
            values.put("EndMinute", eminute);
            values.put("Description", EventDescription);

            long ID = events.insert("UserEvents",null,values);

            Calendar calend = Calendar.getInstance();
            calend.set(Calendar.MONTH,AppConstants.clickeddate.getMonth());
            calend.set(Calendar.YEAR,AppConstants.clickeddate.getYear()+1900);
            calend.set(Calendar.DAY_OF_MONTH,AppConstants.clickeddate.getDate());
            calend.set(Calendar.HOUR_OF_DAY,ehour);
            calend.set(Calendar.MINUTE,eminute);
            calend.set(Calendar.SECOND,0);

            Calendar calnow = Calendar.getInstance();

            if(calend.getTimeInMillis() > calnow.getTimeInMillis()){
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH,AppConstants.clickeddate.getMonth());
                cal.set(Calendar.YEAR,AppConstants.clickeddate.getYear()+1900);
                cal.set(Calendar.DAY_OF_MONTH,AppConstants.clickeddate.getDate());

                cal.set(Calendar.HOUR_OF_DAY,shour);
                cal.set(Calendar.MINUTE,sminute);
                cal.set(Calendar.SECOND,0);

                cal.add(Calendar.MINUTE,-30);

                remoteViews = new RemoteViews(getPackageName(),R.layout.notification);

                remoteViews.setImageViewResource(R.id.notification_icon,R.drawable.ic_event_available_black_24dp);
                remoteViews.setTextViewText(R.id.from,"From-");
                remoteViews.setTextViewText(R.id.to,"To-");
                remoteViews.setTextViewText(R.id.date,date);
                remoteViews.setTextViewText(R.id.description,EventDescription);
                remoteViews.setTextViewText(R.id.starting_time,StartingTime);
                remoteViews.setTextViewText(R.id.ending_time,EndingTime);

                Notification.Builder builder = new Notification.Builder(this);
                //    builder.setContentTitle("Event Notification!");
                //    builder.setContentText(EventDescription+"  "+StartingTime);
                builder.setSmallIcon(R.drawable.ic_event_available_black_24dp);
                builder.setContent(remoteViews);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
             //   builder.setVibrate(new long[] {1000, 1000, 1000, 1000, 1000});

                Notification notification = builder.build();

                Intent notificationIntent = new Intent(this, NotificationPublisher.class);
                notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, (int)ID);
                //     Toast.makeText(getApplicationContext(), Long.toString(cal.getTimeInMillis())+"  "+Long.toString(System.currentTimeMillis()),Toast.LENGTH_LONG).show();
                notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int)ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                long futureInMillis = cal.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
            }

            toast("Event added:"+date);

            finish();
        }
    }

    private void selectTime(final String flag) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if (flag.equals("sTime")) {
                    startHourMinute = (hour*60)+minute;
                    shour = hour;
                    sminute = minute;
                    startingtimebutton.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(minute)}));
                } else if (flag.equals("eTime")) {
                    endHourMinute = (hour*60)+minute;
                    ehour = hour;
                    eminute = minute;
                    endingtimebutton.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(minute)}));
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    public void back_monthview(View view) {

        toast("No Event added!");

        finish();
    }
}
