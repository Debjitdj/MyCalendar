package tech.a0by0.mycalendar;

/**
 * Created by Debjit on 23-Oct-17.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;

public class EventList extends LinearLayout {

    private Context context;
    private AttributeSet attrs;
    private View rootView;

//    private LinearLayout activity_list_of_events;

    private RecyclerView recyclerView_show_events;

//    private Button edit_event, delete_events;

    private OnEventClickListener onEventClickListener;




    private ArrayList<EventModel> eventModelList;
    private EventListAdapter eventListAdapter;




    public EventList(Context context){
        super(context);

        this.context = context;

        if (!isInEditMode()) {
            init();
        }
    }

    public EventList(Context context, AttributeSet attrs){
        super(context, attrs);

        this.context = context;
        this.attrs = attrs;

        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.event_list, this, true);

//        activity_list_of_events = (LinearLayout) findViewById(R.layout.activity_list_of_events);

        recyclerView_show_events = (RecyclerView) rootView.findViewById(R.id.recyclerView_show_events);

        //       edit_event = (Button) activity_list_of_events.findViewById(R.id.edit_event);
        //       delete_events = (Button) activity_list_of_events.findViewById(R.id.delete_events);

    }



    public void setEvents(Date date) {
        setEvents("",date);
    }

    private void setEvents(String sign, Date date){

        eventModelList = new ArrayList<>();
        eventListAdapter = new EventListAdapter(context, eventModelList, "month");

        LinearLayoutManager layoutManagerForShowEventList = new LinearLayoutManager(context);
        recyclerView_show_events.setLayoutManager(layoutManagerForShowEventList);

        recyclerView_show_events.setAdapter(eventListAdapter);

        for (int i = 0; i < AppConstants.eventList.size(); i++) {
            if (AppConstants.eventList.get(i).getStrDate().equals(AppConstants.sdfDate.format(date))) {
                eventModelList.add(new EventModel(AppConstants.eventList.get(i).getId(), AppConstants.eventList.get(i).getStrDate(), AppConstants.eventList.get(i).getStrStartTime(), AppConstants.eventList.get(i).getStrEndTime(), AppConstants.eventList.get(i).getStrName(), AppConstants.eventList.get(i).getEventType()));
            }
        }

        eventListAdapter.notifyDataSetChanged();

        eventListAdapter.setOnEventClickListener(new OnEventClickListener() {
            public void onClick(EventModel model) {

                if(onEventClickListener != null) {
                    onEventClickListener.onClick(model);
                }

                //               delete_events.setVisibility(View.VISIBLE);
                //               edit_event.setVisibility(View.VISIBLE);

                AppConstants.clickedeventmodel = model;
            }
        });
    }

    public void refreshEvents(){

        AppConstants.eventList.clear();
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.defaultevents));
        SQLiteDatabase events = getContext().openOrCreateDatabase("events",MODE_PRIVATE,null);

        while (scan.hasNextLine()){
            String line1 = scan.nextLine();
            String[] dayEvent = line1.split("\t");
            String line2 = scan.nextLine();
            String[] time = line2.split("\t");

            if (!TextUtils.isEmpty(dayEvent[0]) && !TextUtils.isEmpty(time[0]) && !TextUtils.isEmpty(time[1]) && !TextUtils.isEmpty(dayEvent[1])) {
                String tmpDate = GlobalMethods.convertDate(dayEvent[0], AppConstants.sdfDate, AppConstants.sdfDate);
                String tmpStartTime = GlobalMethods.convertDate(time[0], AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
                String tmpEndTime = GlobalMethods.convertDate(time[1], AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
                AppConstants.eventList.add(new EventModel(-1, tmpDate, tmpStartTime, tmpEndTime, dayEvent[1], "Default"));
            }
        }

        //       events.execSQL("CREATE TABLE IF NOT EXISTS UserEvents (ID INTEGER PRIMARY KEY, Date VARCHAR, StartingTime VARCHAR, EndingTime VARCHAR, Description VARCHAR);");

        Cursor cr2 = events.rawQuery("SELECT * FROM UserEvents ORDER BY StartingTime", null);

        if(cr2.moveToFirst()){
            do{
                int id = cr2.getInt(cr2.getColumnIndex("ID"));
                String strdate = cr2.getString(cr2.getColumnIndex("Date"));
                String StartingTime = cr2.getString(cr2.getColumnIndex("StartingTime"));
                String EndingTime = cr2.getString(cr2.getColumnIndex("EndingTime"));
                String EventDescription = cr2.getString(cr2.getColumnIndex("Description"));

                if (!TextUtils.isEmpty(strdate) && !TextUtils.isEmpty(StartingTime) && !TextUtils.isEmpty(EndingTime) && !TextUtils.isEmpty(EventDescription)) {
                    String tmpDate = GlobalMethods.convertDate(strdate, AppConstants.sdfDate, AppConstants.sdfDate);
                    String tmpStartTime = GlobalMethods.convertDate(StartingTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
                    String tmpEndTime = GlobalMethods.convertDate(EndingTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
                    AppConstants.eventList.add(new EventModel(id, tmpDate, tmpStartTime, tmpEndTime, EventDescription, "User"));
                }

            }while(cr2.moveToNext());
            cr2.close();
        }

        setEvents("",AppConstants.clickeddate);
    }

    public void setOnEventClickListener(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }

}

