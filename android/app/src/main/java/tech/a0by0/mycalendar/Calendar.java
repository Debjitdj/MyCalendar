package tech.a0by0.mycalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;

import stanford.androidlib.SimpleActivity;

import static android.content.Context.MODE_PRIVATE;


public class Calendar extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        final MyDynamicCalendar myCalendarmonthview = (MyDynamicCalendar) findViewById(R.id.myCalendarmonthview);

        getConstantsFromDB();

        myCalendarmonthview.showMonthViewWithBelowEvents();

        myCalendarmonthview.deleteAllEvent();
        myCalendarmonthview.create_events();

        myCalendarmonthview.refreshCalendar();


        myCalendarmonthview.getEventList(new GetEventListListener() {            // Get list of event with detail
            @Override
            public void eventList(ArrayList<EventModel> eventList) {

            }
        });

        configCalendar(myCalendarmonthview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        java.util.Calendar calnow = java.util.Calendar.getInstance();
        final MyDynamicCalendar myCalendarmonthview = (MyDynamicCalendar) findViewById(R.id.myCalendarmonthview);
        switch (item.getItemId()) {
            case R.id.gototdate:
                new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofYear, int dayofMonth){
                        myCalendarmonthview.setCalendarDate(dayofMonth,monthofYear+1,year);
                    }
                },calnow.get(java.util.Calendar.YEAR),calnow.get(java.util.Calendar.MONTH),calnow.get(java.util.Calendar.DAY_OF_MONTH)).show();
                return true;
            case R.id.gotopresentdate:

                myCalendarmonthview.goToCurrentDate();
                return true;
            case R.id.settingsPage:
                // Set up the intent
                Intent i = new Intent(this, MainActivity.class);
                // Launch It
                startActivity(i);
                return true;
            case R.id.About:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getConstantsFromDB(){
        if(!AppConstants.dataRetrievedFromDB) {

            SQLiteDatabase db = this.openOrCreateDatabase("events", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS AppConstants (name VARCHAR PRIMARY KEY, value VARCHAR);");

            Cursor cr = db.rawQuery("SELECT * FROM AppConstants WHERE name='StartingDayOfWeekSunday'", null);
            String value = "true";
            boolean startingDayOfWeekSunday = true;
            if (cr.moveToFirst()) {
                value = cr.getString(cr.getColumnIndex("value"));
                cr.close();
            }
            if (!value.equals("true"))
                startingDayOfWeekSunday = false;
            AppConstants.StartingDayOfWeekSunday = startingDayOfWeekSunday;
            AppConstants.dataRetrievedFromDB = true;

        }
    }

    private void configCalendar(MyDynamicCalendar myCalendarmonthview) {
        myCalendarmonthview.setHeaderBackgroundColor("#4255bd");
        myCalendarmonthview.setHeaderTextColor(ContextCompat.getColor(this, R.color.white));
        myCalendarmonthview.setNextPreviousIndicatorColor(ContextCompat.getColor(this, R.color.white));
        myCalendarmonthview.setWeekDayLayoutTextColor(ContextCompat.getColor(this, R.color.black));
        myCalendarmonthview.isSaturdayOff(true, -1, ContextCompat.getColor(this, R.color.red));
        myCalendarmonthview.isSundayOff(true, -1, ContextCompat.getColor(this, R.color.red));
        myCalendarmonthview.setExtraDatesOfMonthBackgroundColor("#d9d9d9");
        myCalendarmonthview.setExtraDatesOfMonthTextColor("#999999");
        myCalendarmonthview.setDatesOfMonthBackgroundColor("#ffffff");
        myCalendarmonthview.setDatesOfMonthTextColor("#111111");
   //     myCalendarmonthview.setCurrentDateTextColor("#4255bd"); // ff9900

        myCalendarmonthview.setCurrentDateBackgroundColor("#5D6D7E");
        myCalendarmonthview.setCurrentDateTextColor("#FFFFFF");

        myCalendarmonthview.setBelowMonthEventTextColor(ContextCompat.getColor(this, R.color.black));
        myCalendarmonthview.setBelowMonthEventDividerColor("#808080");
        myCalendarmonthview.setEventCellBackgroundColor("#D7BDE2");

        myCalendarmonthview.setClickedDateCellBackgroundColor("#FFCDD2");
        myCalendarmonthview.setClickedDateCellTextColor("#1B5E20");
        myCalendarmonthview.setClickedEventCellBackgroundColor("#FFCDD2");
        myCalendarmonthview.setClickedEventCellTextColor("#1B5E20");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        final MyDynamicCalendar myCalendarmonthview = (MyDynamicCalendar) findViewById(R.id.myCalendarmonthview); // = new MyDynamicCalendar(this);
        myCalendarmonthview.deleteAllEvent();
        myCalendarmonthview.create_events();
        myCalendarmonthview.refreshCalendar();

    }
    public void gotohomepage_monthview(View view) {
        finish();
    }

}
