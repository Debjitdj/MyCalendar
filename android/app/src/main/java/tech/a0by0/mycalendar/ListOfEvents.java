package tech.a0by0.mycalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import stanford.androidlib.SimpleActivity;

public class ListOfEvents extends SimpleActivity {

    private EventList myeventlist;
    private Button edit_event, delete_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_events);

   /*     <tech.a0by0.oxfordkabaddi.EventList
        android:id="@+id/myeventlist"
        android:layout_width="0dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        android:layout_height="wrap_content">   */

        initi();
        actionListeners();

        myeventlist.setEvents(AppConstants.clickeddate);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myeventlist.refreshEvents();
        AppConstants.clickedeventmodel = null;

    }

    private void initi(){

        edit_event = (Button) findViewById(R.id.edit_event);
        delete_events = (Button) findViewById(R.id.delete_events);

        myeventlist = (EventList) findViewById(R.id.myeventlist);

    }
    private void actionListeners() {

        delete_events.setVisibility(View.GONE);
        edit_event.setVisibility(View.GONE);

        myeventlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_events.setVisibility(View.GONE);
                edit_event.setVisibility(View.GONE);
            }
        });

        myeventlist.setOnEventClickListener(new OnEventClickListener() {
            @Override
            public void onClick(EventModel eventModel) {
                final Animation animation = new AlphaAnimation(1,0);
                animation.setDuration(500);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(1);
                animation.setRepeatMode(Animation.REVERSE);
                delete_events.setVisibility(View.VISIBLE);
                edit_event.setVisibility(View.VISIBLE);

                delete_events.startAnimation(animation);
                edit_event.startAnimation(animation);
            }
        });

    }


    public void backFromEventList(View view) {
        finish();
    }

    public void deleteEvent(View view) {
        if(AppConstants.clickedeventmodel == null || AppConstants.unclickedEvent){
            toast("Please Select an Event to delete!");
        }
        else if(AppConstants.clickedeventmodel.getEventType().equals("Default")){
            toast("Dafault event can't be deleted");
        }
        else if (!AppConstants.clickedeventmodel.getEventType().equals("Deleted")){
            SQLiteDatabase events = openOrCreateDatabase("events", MODE_PRIVATE, null);

            events.execSQL("DELETE FROM UserEvents WHERE ID="+Integer.toString(AppConstants.clickedeventmodel.getId())+";");

            AppConstants.clickedeventmodel = new EventModel("Deleted");

            myeventlist.refreshEvents();

            Intent notificationIntent = new Intent(this, NotificationPublisher.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AppConstants.clickedeventmodel.getId() , notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }

    public void editEvent(View view) {
        if(AppConstants.clickedeventmodel == null || AppConstants.unclickedEvent ){
            toast("Please Select an Event to Edit!");
        }
        else if(AppConstants.clickedeventmodel.getEventType().equals("Default")){
            toast("Dafault event can't be Updated");
        }
        else if (!AppConstants.clickedeventmodel.getEventType().equals("Updated") && !AppConstants.clickedeventmodel.getEventType().equals("Deleted")){

            final int dayy = AppConstants.clickeddate.getDate();
            final int monthh = AppConstants.clickeddate.getMonth();
            final int yearr = AppConstants.clickeddate.getYear();

            Intent intent = new Intent(this, EditEvent.class);
            intent.putExtra("date",Integer.toString(dayy)+"-"+Integer.toString(monthh+1)+"-"+Integer.toString(yearr+1900));
            startActivity(intent);

   /*         SQLiteDatabase events = openOrCreateDatabase("events", MODE_PRIVATE, null);

            events.execSQL("UPDATE UserEvents SET StartingTime = '" + AppConstants.clickedeventmodel.getStrStartTime() + "', EndingTime = '" + AppConstants.clickedeventmodel.getStrEndTime() + "', Description = '" + AppConstants.clickedeventmodel.getStrName() +"' WHERE ID="+Integer.toString(AppConstants.clickedeventmodel.getStrId())+";");

            */

            AppConstants.clickedeventmodel.setEventType("Updated");

            myeventlist.refreshEvents();
        }
    }
}

