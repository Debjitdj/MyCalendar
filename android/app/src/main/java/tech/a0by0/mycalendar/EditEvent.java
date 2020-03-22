package tech.a0by0.mycalendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TimePicker;

import java.util.Calendar;

import stanford.androidlib.SimpleActivity;

import static android.content.Context.MODE_PRIVATE;

public class EditEvent extends SimpleActivity {

    private int startHourMinute=0;
    private int endHourMinute=0;

    private int shour,ehour,sminute,eminute;

    private SQLiteDatabase events = null;
    private Button editstartingtimebutton, editendingtimebutton;
    private EditText edit_event_description_monthview;

    private RemoteViews remoteViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        $TV(R.id.Header_editEvent).setText("Date: "+ date );

        initi();
        actionListener();

    }

    private void initi(){
        startHourMinute=0;
        endHourMinute=0;
        editstartingtimebutton = (Button) findViewById(R.id.editstartingtimebutton);
        editendingtimebutton = (Button) findViewById(R.id.editendingtimebutton);
        edit_event_description_monthview = (EditText) findViewById(R.id.edit_event_description_monthview);

        editstartingtimebutton.setText(AppConstants.clickedeventmodel.getStrStartTime());
        editendingtimebutton.setText(AppConstants.clickedeventmodel.getStrEndTime());
        edit_event_description_monthview.setText(AppConstants.clickedeventmodel.getStrName());
    }

    private void actionListener(){

        editstartingtimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime("sTime");
            }
        });

        editendingtimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime("eTime");
            }
        });

    }


    private void selectTime(final String flag) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if (flag.equals("sTime")) {
                    startHourMinute = (hour*60)+minute;
                    shour = hour;
                    sminute = minute;
                    editstartingtimebutton.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(minute)}));
                } else if (flag.equals("eTime")) {
                    endHourMinute = (hour*60)+minute;
                    ehour = hour;
                    eminute = minute;
                    editendingtimebutton.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(minute)}));
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    public void eventedit(View view) {
        String EditedStartingTime = editstartingtimebutton.getText().toString();
        String EditedEndingTime = editendingtimebutton.getText().toString();
        String EditedEventDescription = edit_event_description_monthview.getText().toString();

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");


        if(startHourMinute == 0){
            String[] time = EditedStartingTime.split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            shour = hour;
            sminute = minute;
            startHourMinute = (hour*60)+minute;
        }
        if(endHourMinute == 0){
            String[] time = EditedEndingTime.split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            ehour = hour;
            eminute = minute;
            endHourMinute = (hour*60)+minute;
        }

        if(EditedStartingTime.isEmpty()){
            toast("Please select Starting Time!");
        }
        else if(EditedEndingTime.isEmpty()){
            toast("Please select Ending Time!");
        }
        else if(EditedEventDescription.isEmpty()){
            toast("Please add event Description!");
        }
        else if(startHourMinute > endHourMinute){
            toast("Starting time can't be greater than Ending time!");
        }
        else{
            //using database

            events = openOrCreateDatabase("events", MODE_PRIVATE, null);

            events.execSQL("UPDATE UserEvents SET StartingTime = '" + EditedStartingTime + "', StartHour = '" + Integer.toString(shour) + "', StartMinute = '" + Integer.toString(sminute) + "', EndingTime = '" + EditedEndingTime + "', EndHour = '" + Integer.toString(ehour) + "', EndMinute = '" + Integer.toString(eminute) + "', Description = '" + EditedEventDescription +"' WHERE ID="+Integer.toString(AppConstants.clickedeventmodel.getId())+";");


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
                remoteViews.setTextViewText(R.id.description,EditedEventDescription);
                remoteViews.setTextViewText(R.id.starting_time,EditedStartingTime);
                remoteViews.setTextViewText(R.id.ending_time,EditedEndingTime);


                Notification.Builder builder = new Notification.Builder(this);
                //    builder.setContentTitle("Event Notification!");
                //   builder.setContentText(EditedEventDescription+"  "+EditedStartingTime);
                builder.setSmallIcon(R.drawable.ic_event_available_black_24dp);
                builder.setContent(remoteViews);

                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
             //   builder.setVibrate(new long[] {1000, 1000, 1000, 1000, 1000});

                Notification notification = builder.build();

                Intent notificationIntent = new Intent(this, NotificationPublisher.class);
                notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, AppConstants.clickedeventmodel.getId());
                //     Toast.makeText(getApplicationContext(), Long.toString(cal.getTimeInMillis())+"  "+Long.toString(System.currentTimeMillis()),Toast.LENGTH_LONG).show();
                notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AppConstants.clickedeventmodel.getId(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                long futureInMillis = cal.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);

            }



  /*        AppConstants.clickedeventmodel.setStrStartTime(editstartingtimebutton.getText().toString());
            AppConstants.clickedeventmodel.setStrEndTime(editendingtimebutton.getText().toString());
            AppConstants.clickedeventmodel.setStrName(edit_event_description_monthview.getText().toString());
            AppConstants.clickedeventmodel.setEventType("Updated");

            Intent intent = getIntent();
            String date = intent.getStringExtra("date");   */

            toast("Event Edited:"+date);

            finish();
        }

    }

    public void back_from_edit_event(View view) {

        toast("Event Not Edited!");

        finish();

    }

}

