package tech.a0by0.mycalendar;

/**
 * Created by Debjit on 23-Oct-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;


public class MyDynamicCalendar extends LinearLayout {

    private Context context;
    private AttributeSet attrs;
    private View rootView;

    public Date date;

    private RecyclerView recyclerView_dates, recyclerView_hours, recyclerView_show_events, recyclerView_month_view_below_events;
    private TextView tv_month_year, tv_mon, tv_tue, tv_wed, tv_thu, tv_fri, tv_sat, tv_ending_sun, tv_starting_sun;
    private ImageView iv_previous, iv_next;
    private LinearLayout test,eventList, parentLayout, ll_upper_part, ll_lower_part, ll_blank_space, ll_header_views, ll_month_year, ll_week_date, ll_week_day_layout, ll_dates, ll_month_view_below_events, ll_hours;

    private OnDateClickListener onDateClickListener;
    private OnEventClickListener onEventClickListener;
    private OnWeekDayViewClickListener onWeekDayViewClickListener;
    private GetEventListListener getEventListListener;
    private EventList eventlist;

    private ArrayList<DateModel> dateModelList;
    private DateListAdapter dateListAdapter;
    private ArrayList<EventModel> eventModelList;
    private EventListAdapter eventListAdapter;
    private ArrayList<String> hourList;
    private ArrayList<ShowEventsModel> showEventsModelList;
    private ShowWeekViewEventsListAdapter showWeekViewEventsListAdapter;
    private ShowDayViewEventsListAdapter showDayViewEventsListAdapter;
    private HourListAdapter hourListAdapter;

    private Button show_events, add_event;

    private SimpleDateFormat sdfMonthYear = new SimpleDateFormat("MMM - yyyy");
    private SimpleDateFormat sdfWeekDay = new SimpleDateFormat("dd MMM");
    private SimpleDateFormat sdfDayMonthYear = new SimpleDateFormat("EEEE,  dd - MMM - yyyy");

    private java.util.Calendar calendar = java.util.Calendar.getInstance();

//    private String strHeaderBackgroundColor, strHeaderTextColor;

    public MyDynamicCalendar(Context context) {
        super(context);

        this.context = context;

        if (!isInEditMode()) {
            init();
        }
    }

    public MyDynamicCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.attrs = attrs;

        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyDynamicCalendar, 0, 0);

        try {
//            strHeaderBackgroundColor = a.getString(R.styleable.MyCalendar_headerBackgroundColor);
//            strHeaderTextColor = a.getString(R.styleable.MyCalendar_headerTextColor);
        } finally {
            a.recycle();
        }


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.my_dynamic_calendar, this, true);

        recyclerView_dates = (RecyclerView) rootView.findViewById(R.id.recyclerView_dates);
        recyclerView_month_view_below_events = (RecyclerView) rootView.findViewById(R.id.recyclerView_month_view_below_events);
        iv_previous = (ImageView) rootView.findViewById(R.id.iv_previous);
        iv_next = (ImageView) rootView.findViewById(R.id.iv_next);
        parentLayout = (LinearLayout) rootView.findViewById(R.id.parentLayout);
        ll_upper_part = (LinearLayout) rootView.findViewById(R.id.ll_upper_part);
        ll_blank_space = (LinearLayout) rootView.findViewById(R.id.ll_blank_space);
        ll_header_views = (LinearLayout) rootView.findViewById(R.id.ll_header_views);
        ll_month_year = (LinearLayout) rootView.findViewById(R.id.ll_month_year);
        ll_week_date = (LinearLayout) rootView.findViewById(R.id.ll_week_date);
        test = (LinearLayout) rootView.findViewById(R.id.test);
        ll_week_day_layout = (LinearLayout) rootView.findViewById(R.id.ll_week_day_layout);
        ll_dates = (LinearLayout) rootView.findViewById(R.id.ll_dates);
        ll_month_view_below_events = (LinearLayout) rootView.findViewById(R.id.ll_month_view_below_events);
        tv_month_year = (TextView) rootView.findViewById(R.id.tv_month_year);
        tv_mon = (TextView) rootView.findViewById(R.id.tv_mon);
        tv_tue = (TextView) rootView.findViewById(R.id.tv_tue);
        tv_wed = (TextView) rootView.findViewById(R.id.tv_wed);
        tv_thu = (TextView) rootView.findViewById(R.id.tv_thu);
        tv_fri = (TextView) rootView.findViewById(R.id.tv_fri);
        tv_sat = (TextView) rootView.findViewById(R.id.tv_sat);
        tv_ending_sun = (TextView) rootView.findViewById(R.id.tv_ending_sun);
        tv_starting_sun = (TextView) rootView.findViewById(R.id.tv_starting_sun);
        show_events = (Button) rootView.findViewById(R.id.show_events);
        add_event = (Button) rootView.findViewById(R.id.add_event);


//        ll_header_views.setBackgroundColor(Color.parseColor(strHeaderBackgroundColor));
//        tv_month_year.setTextColor(Color.parseColor(strHeaderTextColor));


        actionListeners();
    }

    private void actionListeners() {


        show_events.setVisibility(View.GONE);
        add_event.setVisibility(View.GONE);


        iv_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }

            }
        });

        iv_previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }

            }
        });

        show_events.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                v.clearAnimation();
                if(AppConstants.clickeddate == null || AppConstants.unclickedDate){
                    Toast.makeText((Activity)getContext(), "Please select a date first!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getContext(), ListOfEvents.class);
                    ((Activity)getContext()).startActivityForResult(intent, 0);
                }


     /*           dateListAdapter.setOnMonthBellowEventsClick(new OnMonthBellowEventsDateClickListener() {
                    @Override
                    public void onClick(Date date) {

                        eventModelList = new ArrayList<>();
                        eventListAdapter = new EventListAdapter(context, eventModelList, "month");

                        LinearLayoutManager layoutManagerForShowEventList = new LinearLayoutManager(context);
                        recyclerView_month_view_below_events.setLayoutManager(layoutManagerForShowEventList);

                        recyclerView_month_view_below_events.setAdapter(eventListAdapter);

                        for (int i = 0; i < AppConstants.eventList.size(); i++) {
                            if (AppConstants.eventList.get(i).getStrDate().equals(AppConstants.sdfDate.format(date))) {
                                eventModelList.add(new EventModel(AppConstants.eventList.get(i).getStrDate(), AppConstants.eventList.get(i).getStrStartTime(), AppConstants.eventList.get(i).getStrEndTime(), AppConstants.eventList.get(i).getStrName()));
                            }
                        }

                        eventListAdapter.notifyDataSetChanged();
                    }
                });  */

            }
        });

        add_event.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                v.clearAnimation();
                // add exceptions
                if(AppConstants.clickeddate == null || AppConstants.unclickedDate){
                    Toast.makeText((Activity)getContext(), "Please select a date first!", Toast.LENGTH_SHORT).show();
                }
                else{
                    final int dayy = AppConstants.clickeddate.getDate();
                    final int monthh = AppConstants.clickeddate.getMonth();
                    final int yearr = AppConstants.clickeddate.getYear();

                    Intent intent = new Intent(getContext(), Add_New_Event.class);
                    intent.putExtra("date",Integer.toString(dayy)+"-"+Integer.toString(monthh+1)+"-"+Integer.toString((yearr%100)+2000));
                    ((Activity)getContext()).startActivityForResult(intent, 0);
                }

            }
        });


        ll_header_views.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });

        ll_month_year.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });

  /*      ll_upper_part.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });   */

  /*      ll_week_date.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });   */

  /*      ll_dates.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });   */

        test.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });

        recyclerView_dates.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });

 /*       ll_month_view_below_events.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        }); */

        recyclerView_month_view_below_events.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                if (AppConstants.isShowMonth) {
                    setMonthView("sub");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("sub");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("sub");
                } else if (AppConstants.isShowDay) {
                    setDayView("sub");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("sub");
                }
            }
            public void onSwipeLeft() {
                if (AppConstants.isShowMonth) {
                    setMonthView("add");
                } else if (AppConstants.isShowMonthWithBellowEvents) {
                    setMonthViewWithBelowEvents("add");
                } else if (AppConstants.isShowWeek) {
                    setWeekView("add");
                } else if (AppConstants.isShowDay) {
                    setDayView("add");
                } else if (AppConstants.isAgenda) {
                    setAgendaView("add");
                }
            }

        });



    }

    public void setCalendarBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            parentLayout.setBackgroundColor(Color.parseColor(color));
        }
    }

    public void setCalendarBackgroundColor(int color) {
        parentLayout.setBackgroundColor(color);
    }

    public void setHeaderBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            ll_header_views.setBackgroundColor(Color.parseColor(color));
        }
    }

    public void setHeaderBackgroundColor(int color) {
        ll_header_views.setBackgroundColor(color);
    }

    public void setHeaderTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            tv_month_year.setTextColor(Color.parseColor(color));
        }
    }

    public void setHeaderTextColor(int color) {
        tv_month_year.setTextColor(color);
    }

    public void setNextPreviousIndicatorColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            iv_previous.setColorFilter(Color.parseColor(color));
            iv_next.setColorFilter(Color.parseColor(color));
        }
    }

    public void setNextPreviousIndicatorColor(int color) {
        iv_previous.setColorFilter(color);
        iv_next.setColorFilter(color);
    }

    public void setWeekDayLayoutBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            ll_week_day_layout.setBackgroundColor(Color.parseColor(color));
        }
    }

    public void setWeekDayLayoutBackgroundColor(int color) {
        ll_week_day_layout.setBackgroundColor(color);
    }

    public void setWeekDayLayoutTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            tv_mon.setTextColor(Color.parseColor(color));
            tv_tue.setTextColor(Color.parseColor(color));
            tv_wed.setTextColor(Color.parseColor(color));
            tv_thu.setTextColor(Color.parseColor(color));
            tv_fri.setTextColor(Color.parseColor(color));
            if (!AppConstants.isSaturdayOff) {
                tv_sat.setTextColor(Color.parseColor(color));
            }
            if (!AppConstants.isSundayOff) {
                tv_ending_sun.setTextColor(Color.parseColor(color));
                tv_starting_sun.setTextColor(Color.parseColor(color));
            }
        }
    }

    public void setWeekDayLayoutTextColor(int color) {
        tv_mon.setTextColor(color);
        tv_tue.setTextColor(color);
        tv_wed.setTextColor(color);
        tv_thu.setTextColor(color);
        tv_fri.setTextColor(color);
        if (!AppConstants.isSaturdayOff) {
            tv_sat.setTextColor(color);
        }
        if (!AppConstants.isSundayOff) {
            tv_ending_sun.setTextColor(color);
            tv_starting_sun.setTextColor(color);
        }
    }

    public void isSaturdayOff(boolean b, String layoutcolor, String textcolor) {
        if (b) {
//            if (!TextUtils.isEmpty(layoutcolor) && !TextUtils.isEmpty(textcolor)) {
            AppConstants.isSaturdayOff = true;
            AppConstants.strSaturdayOffBackgroundColor = layoutcolor;
            AppConstants.strSaturdayOffTextColor = textcolor;
            tv_sat.setTextColor(Color.parseColor(textcolor));
//            }
        }
    }

    public void isSaturdayOff(boolean b, int layoutColor, int textColor) {
        if (b) {
            AppConstants.isSaturdayOff = true;
            AppConstants.saturdayOffBackgroundColor = layoutColor;
            AppConstants.saturdayOffTextColor = textColor;
            tv_sat.setTextColor(textColor);
        }
    }

    public void isSundayOff(boolean b, String layoutcolor, String textcolor) {
        if (b) {
            if (!TextUtils.isEmpty(layoutcolor) && !TextUtils.isEmpty(textcolor)) {
                AppConstants.isSundayOff = true;
                AppConstants.strSundayOffBackgroundColor = layoutcolor;
                AppConstants.strSundayOffTextColor = textcolor;
                tv_ending_sun.setTextColor(Color.parseColor(textcolor));
                tv_starting_sun.setTextColor(Color.parseColor(textcolor));
            }
        }
    }

    public void isSundayOff(boolean b, int layoutColor, int textColor) {
        if (b) {
            AppConstants.isSundayOff = true;
            AppConstants.sundayOffBackgroundColor = layoutColor;
            AppConstants.sundayOffTextColor = textColor;
            tv_ending_sun.setTextColor(textColor);
            tv_starting_sun.setTextColor(textColor);
        }
    }

    public void setExtraDatesOfMonthBackgroundColor(int color) {
        AppConstants.extraDatesBackgroundColor = color;
    }

    public void setExtraDatesOfMonthBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strExtraDatesBackgroundColor = color;
        }
    }

    public void setExtraDatesOfMonthTextColor(int color) {
        AppConstants.extraDatesTextColor = color;
    }

    public void setExtraDatesOfMonthTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strExtraDatesTextColor = color;
        }
    }

    public void setDatesOfMonthBackgroundColor(int color) {
        AppConstants.datesBackgroundColor = color;
    }

    public void setDatesOfMonthBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strDatesBackgroundColor = color;
        }
    }

    public void setDatesOfMonthTextColor(int color) {
        AppConstants.datesTextColor = color;
    }

    public void setDatesOfMonthTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strDatesTextColor = color;
        }
    }

    public void setCurrentDateBackgroundColor(int color) {
        AppConstants.currentDateBackgroundColor = color;
    }

    public void setCurrentDateBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strCurrentDateBackgroundColor = color;
        }
    }

    public void setCurrentDateTextColor(int color) {
        AppConstants.currentDateTextColor = color;
    }

    public void setCurrentDateTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strCurrentDateTextColor = color;
        }
    }

    public void setEventCellBackgroundColor(int color) {
        AppConstants.eventCellBackgroundColor = color;
    }

    public void setEventCellBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strEventCellBackgroundColor = color;
        }
    }

    public void setEventCellTextColor(int color) {
        AppConstants.eventCellTextColor = color;
    }

    public void setEventCellTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strEventCellTextColor = color;
        }
    }

    public void setClickedDateCellBackgroundColor(int color){
        AppConstants.ClickedDateCellBackgroundColor = color;
    }

    public void setClickedDateCellBackgroundColor(String color){
        AppConstants.strClickedDateCellBackgroundColor = color;
    }

    public void setClickedDateCellTextColor(int color){
        AppConstants.ClickedDateCellTextColor = color;
    }

    public void setClickedDateCellTextColor(String color){
        AppConstants.strClickedDateCellTextColor = color;
    }

    public void setClickedEventCellBackgroundColor(int color){
        AppConstants.ClickedEventCellBackgroundColor = color;
    }

    public void setClickedEventCellBackgroundColor(String color){
        AppConstants.strClickedEventCellBackgroundColor = color;
    }

    public void setClickedEventCellTextColor(int color){
        AppConstants.ClickedEventCellTextColor = color;
    }

    public void setClickedEventCellTextColor(String color){
        AppConstants.strClickedEventCellTextColor = color;
    }

    public void addEvent(int id, String date, String startTime, String endTime, String name, String eventType) {
        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(name)) {
            String tmpDate = GlobalMethods.convertDate(date, AppConstants.sdfDate, AppConstants.sdfDate);
            String tmpStartTime = GlobalMethods.convertDate(startTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
            String tmpEndTime = GlobalMethods.convertDate(endTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
            AppConstants.eventList.add(new EventModel(id, tmpDate, tmpStartTime, tmpEndTime, name, eventType));
        }
    }

    public void addEvent(int id, String date, String startTime, String endTime, String name, int image, String eventType) {
        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(name)) {
            String tmpDate = GlobalMethods.convertDate(date, AppConstants.sdfDate, AppConstants.sdfDate);
            String tmpStartTime = GlobalMethods.convertDate(startTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
            String tmpEndTime = GlobalMethods.convertDate(endTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
            AppConstants.eventList.add(new EventModel(id, tmpDate, tmpStartTime, tmpEndTime, name, image, eventType));
        }
    }

    public void getEventList(GetEventListListener getEventListListener) {
        this.getEventListListener = getEventListListener;
        this.getEventListListener.eventList(AppConstants.eventList);
    }

    public void updateEvent(int position, String date, String startTime, String endTime, String name) {
        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(name)) {
            String tmpDate = GlobalMethods.convertDate(date, AppConstants.sdfDate, AppConstants.sdfDate);
            String tmpStartTime = GlobalMethods.convertDate(startTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
            String tmpEndTime = GlobalMethods.convertDate(endTime, AppConstants.sdfHourMinute, AppConstants.sdfHourMinute);
            AppConstants.eventList.get(position).setStrDate(tmpDate);
            AppConstants.eventList.get(position).setStrStartTime(tmpStartTime);
            AppConstants.eventList.get(position).setStrEndTime(tmpEndTime);
            AppConstants.eventList.get(position).setStrName(name);
        }
        refreshCalendar();
    }

    public void deleteEvent(int position) {
        AppConstants.eventList.remove(position);
        refreshCalendar();
    }

    public void deleteAllEvent() {
        AppConstants.eventList.clear();
        refreshCalendar();
    }

    public void create_events(){
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.defaultevents));
        SQLiteDatabase events = getContext().openOrCreateDatabase("events",MODE_PRIVATE,null);
        while (scan.hasNextLine()){
            String line1 = scan.nextLine();
            String[] dayEvent = line1.split("\t");
            String line2 = scan.nextLine();
            String[] time = line2.split("\t");
            addEvent(-1,dayEvent[0], time[0], time[1], dayEvent[1], "Default");
        }

        events.execSQL("CREATE TABLE IF NOT EXISTS UserEvents (ID INTEGER PRIMARY KEY, Date VARCHAR, StartingTime VARCHAR, StartHour INTEGER, StartMinute INTEGER, EndingTime VARCHAR, EndHour INTEGER, EndMinute INTEGER, Description VARCHAR);");

        Cursor cr2 = events.rawQuery("SELECT * FROM UserEvents ORDER BY StartingTime", null);

        if(cr2.moveToFirst()){
            do{
                int id = cr2.getInt(cr2.getColumnIndex("ID"));
                String date = cr2.getString(cr2.getColumnIndex("Date"));
                String StartingTime = cr2.getString(cr2.getColumnIndex("StartingTime"));
      //          int StartHour = cr2.getInt(cr2.getColumnIndex("StartHour"));
        //        int StartMinute = cr2.getInt(cr2.getColumnIndex("StartMinute"));
                String EndingTime = cr2.getString(cr2.getColumnIndex("EndingTime"));
        //        int EndHour = cr2.getInt(cr2.getColumnIndex("EndHour"));
        //        int EndMinute = cr2.getInt(cr2.getColumnIndex("EndMinute"));
                String EventDescription = cr2.getString(cr2.getColumnIndex("Description"));

                addEvent(id, date, StartingTime, EndingTime, EventDescription, "User");

        /*        Notification.Builder builder = new Notification.Builder(getContext());
                builder.setContentTitle("An event in 30 minutes!");
                builder.setContentText(EventDescription+"  "+StartingTime);
                builder.setSmallIcon(R.drawable.ic_back_button);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);

                Notification notification = builder.build();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH,AppConstants.clickeddate.getMonth());
                cal.set(Calendar.YEAR,AppConstants.clickeddate.getYear()+1900);
                cal.set(Calendar.DAY_OF_MONTH,AppConstants.clickeddate.getDate());
                if(StartMinute>=30){
                    StartMinute = StartMinute - 30;
                }
                else{
                    StartHour = StartHour - 1;
                    StartMinute = 30 + StartMinute;
                }
                cal.set(Calendar.HOUR_OF_DAY,StartHour);
                cal.set(Calendar.MINUTE,StartMinute);

                //     Toast.makeText(getApplicationContext(), +"  "+Long.toString(System.currentTimeMillis()),Toast.LENGTH_LONG).show();


                Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
                notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, id);
                //     Toast.makeText(getApplicationContext(), Long.toString(cal.getTimeInMillis())+"  "+Long.toString(System.currentTimeMillis()),Toast.LENGTH_LONG).show();
                notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                long futureInMillis = cal.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);   */

            }while(cr2.moveToNext());
            cr2.close();
        }
    }

    public void setBelowMonthEventTextColor(int color) {
        AppConstants.belowMonthEventTextColor = color;
    }

    public void setBelowMonthEventTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strBelowMonthEventTextColor = color;
        }
    }

    public void setBelowMonthEventDividerColor(int color) {
        AppConstants.belowMonthEventDividerColor = color;
    }

    public void setBelowMonthEventDividerColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strBelowMonthEventDividerColor = color;
        }
    }

//    -------------------------- All methods of holiday --------------------------

    public void setHolidayCellClickable(boolean b) {
        if (b) {
            AppConstants.isHolidayCellClickable = true;
        }
    }

    public void setHolidayCellBackgroundColor(int color) {
        AppConstants.holidayCellBackgroundColor = color;
    }

    public void setHolidayCellBackgroundColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strHolidayCellBackgroundColor = color;
        }
    }

    public void setHolidayCellTextColor(int color) {
        AppConstants.holidayCellTextColor = color;
    }

    public void setHolidayCellTextColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            AppConstants.strHolidayCellTextColor = color;
        }
    }

    public void addHoliday(String date) {
        if (!TextUtils.isEmpty(date)) {
            String tmpDate = GlobalMethods.convertDate(date, AppConstants.sdfDate, AppConstants.sdfDate);
            AppConstants.holidayList.add(tmpDate);
        }
    }

//    -------------------------------------------------------------------------


    public void showMonthView() {
        setMonthView("");
    }

    private void setMonthView(String sign) {
        dateModelList = new ArrayList<>();
        dateListAdapter = new DateListAdapter(context, dateModelList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
        recyclerView_dates.setLayoutManager(gridLayoutManager);

        recyclerView_dates.setAdapter(dateListAdapter);

        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onClick(date);
                }
            }

            @Override
            public void onLongClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onLongClick(date);
                }
            }
        });

        AppConstants.isShowMonth = true;
        AppConstants.isShowMonthWithBellowEvents = false;
        AppConstants.isShowWeek = false;
        AppConstants.isShowDay = false;
        AppConstants.isAgenda = false;

        ll_upper_part.setVisibility(View.VISIBLE);
        ll_month_view_below_events.setVisibility(View.GONE);
        //   ll_lower_part.setVisibility(View.GONE);
        ll_blank_space.setVisibility(View.GONE);
        //   ll_hours.setVisibility(View.GONE);

        if (sign.equals("add")) {
            AppConstants.main_calendar.set(java.util.Calendar.MONTH, (AppConstants.main_calendar.get(java.util.Calendar.MONTH) + 1));
        } else if (sign.equals("sub")) {
            AppConstants.main_calendar.set(java.util.Calendar.MONTH, (AppConstants.main_calendar.get(java.util.Calendar.MONTH) - 1));
        }

        tv_month_year.setText(sdfMonthYear.format(AppConstants.main_calendar.getTime()));

        // set date start of month
        calendar.setTime(AppConstants.main_calendar.getTime());
        calendar.set((java.util.Calendar.DAY_OF_MONTH), 1);

        int monthBeginningCell = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 2;

        if (monthBeginningCell == -1) {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, -6);
        } else if (monthBeginningCell == 0) {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, -7);
        } else {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, -monthBeginningCell);
        }

        dateModelList.clear();

        while (dateModelList.size() < 42) {
            DateModel model = new DateModel();
            model.setDates(calendar.getTime());
            model.setFlag("month");
            dateModelList.add(model);

            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }

        dateListAdapter.notifyDataSetChanged();
    }

    public void showMonthViewWithBelowEvents() {
        setMonthViewWithBelowEvents("");
    }

    private void setMonthViewWithBelowEvents(String sign) {
        dateModelList = new ArrayList<>();
        dateListAdapter = new DateListAdapter(context, dateModelList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);

        recyclerView_month_view_below_events.setLayoutManager(gridLayoutManager);

        recyclerView_month_view_below_events.setAdapter(dateListAdapter);


        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onClick(date);
                }
            }

            @Override
            public void onLongClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onLongClick(date);
                }
            }
        });

        AppConstants.isShowMonth = false;
        AppConstants.isShowMonthWithBellowEvents = true;
        AppConstants.isShowWeek = false;
        AppConstants.isShowDay = false;
        AppConstants.isAgenda = false;

        ll_upper_part.setVisibility(View.VISIBLE);
        ll_month_view_below_events.setVisibility(View.VISIBLE);
        //   ll_lower_part.setVisibility(View.GONE);
        ll_blank_space.setVisibility(View.GONE);
        //   ll_hours.setVisibility(View.GONE);

        if (sign.equals("add")) {
            AppConstants.main_calendar.set(java.util.Calendar.MONTH, (AppConstants.main_calendar.get(java.util.Calendar.MONTH) + 1));
        } else if (sign.equals("sub")) {
            AppConstants.main_calendar.set(java.util.Calendar.MONTH, (AppConstants.main_calendar.get(java.util.Calendar.MONTH) - 1));
        }

        tv_month_year.setText(sdfMonthYear.format(AppConstants.main_calendar.getTime()));


        // set date start of month
        calendar.setTime(AppConstants.main_calendar.getTime());
        int numberOfDaysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        calendar.set((java.util.Calendar.DAY_OF_MONTH), 1);

        int monthBeginningCell = calendar.get(java.util.Calendar.DAY_OF_WEEK);

        int numberOfDaysInThisView = numberOfDaysInMonth;
        int extraDaysInTheBeginning;

        if(AppConstants.StartingDayOfWeekSunday){
            tv_ending_sun.setVisibility(View.GONE);
            tv_starting_sun.setVisibility(View.VISIBLE);

            extraDaysInTheBeginning = (monthBeginningCell - 1);

            numberOfDaysInThisView += extraDaysInTheBeginning;
        }
        else{
            tv_ending_sun.setVisibility(View.VISIBLE);
            tv_starting_sun.setVisibility(View.GONE);

            extraDaysInTheBeginning = (monthBeginningCell + 5) % 7;

            numberOfDaysInThisView += extraDaysInTheBeginning;

        }

        calendar.add(java.util.Calendar.DAY_OF_MONTH, (-1)*extraDaysInTheBeginning);

        if (numberOfDaysInThisView % 7 != 0){
            numberOfDaysInThisView += 7 - (numberOfDaysInThisView % 7);
        }


        dateModelList.clear();

        while (dateModelList.size() < numberOfDaysInThisView) {
            DateModel model = new DateModel();
            model.setDates(calendar.getTime());
            model.setFlag("month");
            dateModelList.add(model);

            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }

        dateListAdapter.notifyDataSetChanged();


        dateListAdapter.setOnMonthBellowEventsClick(new OnMonthBellowEventsDateClickListener() {
            @Override
            public void onClick(Date date) {

                final Animation animation = new AlphaAnimation(1,0);
                animation.setDuration(500);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(1);
                animation.setRepeatMode(Animation.REVERSE);
                show_events.setVisibility(View.VISIBLE);
                add_event.setVisibility(View.VISIBLE);

                show_events.startAnimation(animation);
                add_event.startAnimation(animation);

                AppConstants.clickeddate = date;
            }
        });
    }

    public void showWeekView() {
        setWeekView("");
    }

    private void setWeekView(String sign) {
        dateModelList = new ArrayList<>();
        dateListAdapter = new DateListAdapter(context, dateModelList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
        recyclerView_dates.setLayoutManager(gridLayoutManager);

        recyclerView_dates.setAdapter(dateListAdapter);

        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onClick(date);
                }
            }

            @Override
            public void onLongClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onLongClick(date);
                }
            }
        });

        AppConstants.isShowMonth = false;
        AppConstants.isShowMonthWithBellowEvents = false;
        AppConstants.isShowWeek = true;
        AppConstants.isShowDay = false;
        AppConstants.isAgenda = false;

        showEventsModelList = new ArrayList<>();
        showWeekViewEventsListAdapter = new ShowWeekViewEventsListAdapter(context, showEventsModelList);

        GridLayoutManager gridLayoutManagerForShowEventList = new GridLayoutManager(context, 7);
        recyclerView_show_events.setLayoutManager(gridLayoutManagerForShowEventList);

        recyclerView_show_events.setAdapter(showWeekViewEventsListAdapter);

        showWeekViewEventsListAdapter.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
            @Override
            public void onClick(String date, String time) {
                if (onWeekDayViewClickListener != null) {
                    onWeekDayViewClickListener.onClick(date, time);
                }
            }

            @Override
            public void onLongClick(String date, String time) {
                if (onWeekDayViewClickListener != null) {
                    onWeekDayViewClickListener.onLongClick(date, time);
                }
            }
        });

        ll_upper_part.setVisibility(View.VISIBLE);
        ll_month_view_below_events.setVisibility(View.GONE);
        ll_lower_part.setVisibility(View.VISIBLE);
        ll_blank_space.setVisibility(View.VISIBLE);
        ll_hours.setVisibility(View.VISIBLE);

        recyclerView_show_events.setVisibility(VISIBLE);

        setHourList();

//        setWeekView("");

        if (sign.equals("add")) {
            AppConstants.main_calendar.set(java.util.Calendar.DAY_OF_MONTH, (AppConstants.main_calendar.get(java.util.Calendar.DAY_OF_MONTH) + 7));
        } else if (sign.equals("sub")) {
            AppConstants.main_calendar.set(java.util.Calendar.DAY_OF_MONTH, (AppConstants.main_calendar.get(java.util.Calendar.DAY_OF_MONTH) - 7));
        }


        // set date start of month
        calendar.setTime(AppConstants.main_calendar.getTime());

        int monthBeginningCell = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 2;
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -monthBeginningCell);

        String weekStartDay = sdfWeekDay.format(calendar.getTime());

        dateModelList.clear();

        while (dateModelList.size() < 7) {
            DateModel model = new DateModel();
            model.setDates(calendar.getTime());
            model.setFlag("week");
            dateModelList.add(model);

            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }

        dateListAdapter.notifyDataSetChanged();

        calendar.add(java.util.Calendar.DAY_OF_MONTH, -1);

        String weekEndDay = sdfWeekDay.format(calendar.getTime());

        tv_month_year.setText(String.format("%s - %s", weekStartDay, weekEndDay));


        showEventsModelList.clear();

        int count7 = 1;

        calendar.add(java.util.Calendar.DAY_OF_MONTH, -6);

        java.util.Calendar calendar_set_hours = java.util.Calendar.getInstance();
        calendar_set_hours.set(java.util.Calendar.HOUR_OF_DAY, 0);

        while (showEventsModelList.size() < 168) {
            if (count7 < 7) {
                ShowEventsModel model = new ShowEventsModel();
                model.setDates(calendar.getTime());
                model.setHours(AppConstants.sdfHour.format(calendar_set_hours.getTime()) + ":00");
                showEventsModelList.add(model);

                calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

                count7++;
            } else {
                ShowEventsModel model = new ShowEventsModel();
                model.setDates(calendar.getTime());
                model.setHours(AppConstants.sdfHour.format(calendar_set_hours.getTime()) + ":00");
                showEventsModelList.add(model);

                calendar.add(java.util.Calendar.DAY_OF_MONTH, -6);

                calendar_set_hours.add(java.util.Calendar.HOUR_OF_DAY, 1);

                count7 = 1;
            }
        }

        showWeekViewEventsListAdapter.notifyDataSetChanged();
    }

    public void showDayView() {
        setDayView("");
    }

    private void setDayView(String sign) {
        showEventsModelList = new ArrayList<>();
        showDayViewEventsListAdapter = new ShowDayViewEventsListAdapter(context, showEventsModelList);

        LinearLayoutManager layoutManagerForShowEventList = new LinearLayoutManager(context);
        recyclerView_show_events.setLayoutManager(layoutManagerForShowEventList);

        recyclerView_show_events.setAdapter(showDayViewEventsListAdapter);

        showDayViewEventsListAdapter.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
            @Override
            public void onClick(String date, String time) {
                if (onWeekDayViewClickListener != null) {
                    onWeekDayViewClickListener.onClick(date, time);
                }
            }

            @Override
            public void onLongClick(String date, String time) {
                if (onWeekDayViewClickListener != null) {
                    onWeekDayViewClickListener.onLongClick(date, time);
                }
            }
        });

        AppConstants.isShowMonth = false;
        AppConstants.isShowMonthWithBellowEvents = false;
        AppConstants.isShowWeek = false;
        AppConstants.isShowDay = true;
        AppConstants.isAgenda = false;

        ll_upper_part.setVisibility(View.GONE);
        ll_month_view_below_events.setVisibility(View.GONE);
        ll_lower_part.setVisibility(View.VISIBLE);
        ll_blank_space.setVisibility(View.VISIBLE);
        ll_hours.setVisibility(View.VISIBLE);

        recyclerView_show_events.setVisibility(VISIBLE);

        setHourList();

//        setDayView("");

        if (sign.equals("add")) {
            AppConstants.main_calendar.set(java.util.Calendar.DAY_OF_MONTH, (AppConstants.main_calendar.get(java.util.Calendar.DAY_OF_MONTH) + 1));
        } else if (sign.equals("sub")) {
            AppConstants.main_calendar.set(java.util.Calendar.DAY_OF_MONTH, (AppConstants.main_calendar.get(java.util.Calendar.DAY_OF_MONTH) - 1));
        }

        tv_month_year.setText(sdfDayMonthYear.format(AppConstants.main_calendar.getTime()));

        // set date start of month
        calendar.setTime(AppConstants.main_calendar.getTime());

        dateModelList.clear();

        java.util.Calendar calendar_set_hours = java.util.Calendar.getInstance();
        calendar_set_hours.set(java.util.Calendar.HOUR_OF_DAY, 0);

        showEventsModelList.clear();

        for (int i = 0; i < hourList.size(); i++) {
            ShowEventsModel model = new ShowEventsModel();
            model.setDates(calendar.getTime());
            model.setHours(AppConstants.sdfHour.format(calendar_set_hours.getTime()) + ":00");
            showEventsModelList.add(model);

            calendar_set_hours.add(java.util.Calendar.HOUR_OF_DAY, 1);
        }

        showDayViewEventsListAdapter.notifyDataSetChanged();
    }

    public void showAgendaView() {
        setAgendaView("");
    }

    private void setAgendaView(String sign) {
        dateModelList = new ArrayList<>();
        dateListAdapter = new DateListAdapter(context, dateModelList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
        recyclerView_dates.setLayoutManager(gridLayoutManager);

        recyclerView_dates.setAdapter(dateListAdapter);

        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onClick(date);
                }
            }

            @Override
            public void onLongClick(Date date) {
                if (onDateClickListener != null) {
                    onDateClickListener.onLongClick(date);
                }
            }
        });

        AppConstants.isShowMonth = false;
        AppConstants.isShowMonthWithBellowEvents = false;
        AppConstants.isShowWeek = false;
        AppConstants.isShowDay = false;
        AppConstants.isAgenda = true;

        ll_upper_part.setVisibility(View.VISIBLE);
        ll_month_view_below_events.setVisibility(View.GONE);
        ll_lower_part.setVisibility(View.VISIBLE);
        ll_blank_space.setVisibility(View.GONE);
        ll_hours.setVisibility(View.GONE);


        if (sign.equals("add")) {
            AppConstants.main_calendar.set(java.util.Calendar.DAY_OF_MONTH, (AppConstants.main_calendar.get(java.util.Calendar.DAY_OF_MONTH) + 7));
        } else if (sign.equals("sub")) {
            AppConstants.main_calendar.set(java.util.Calendar.DAY_OF_MONTH, (AppConstants.main_calendar.get(java.util.Calendar.DAY_OF_MONTH) - 7));
        }


        // set date start of month
        calendar.setTime(AppConstants.main_calendar.getTime());

        int monthBeginningCell = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 2;
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -monthBeginningCell);

        String weekStartDay = sdfWeekDay.format(calendar.getTime());

        dateModelList.clear();

        while (dateModelList.size() < 7) {
            DateModel model = new DateModel();
            model.setDates(calendar.getTime());
            model.setFlag("week");
            dateModelList.add(model);

            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }

        dateListAdapter.notifyDataSetChanged();

        calendar.add(java.util.Calendar.DAY_OF_MONTH, -1);

        String weekEndDay = sdfWeekDay.format(calendar.getTime());

        tv_month_year.setText(String.format("%s - %s", weekStartDay, weekEndDay));


        recyclerView_show_events.setVisibility(GONE);

        dateListAdapter.setOnMonthBellowEventsClick(new OnMonthBellowEventsDateClickListener() {
            @Override
            public void onClick(Date date) {

                recyclerView_show_events.setVisibility(VISIBLE);

                eventModelList = new ArrayList<EventModel>();
                eventListAdapter = new EventListAdapter(context, eventModelList, "month");

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recyclerView_show_events.setLayoutManager(linearLayoutManager);

                recyclerView_show_events.setAdapter(eventListAdapter);

                for (int i = 0; i < AppConstants.eventList.size(); i++) {
                    if (AppConstants.eventList.get(i).getStrDate().equals(AppConstants.sdfDate.format(date))) {
                        eventModelList.add(new EventModel(AppConstants.eventList.get(i).getId(), AppConstants.eventList.get(i).getStrDate(), AppConstants.eventList.get(i).getStrStartTime(), AppConstants.eventList.get(i).getStrEndTime(), AppConstants.eventList.get(i).getStrName(), AppConstants.eventList.get(i).getEventType()));
                    }
                }

                eventListAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setHourList() {

        hourList = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            hourList.add(String.format("%02d", i));
        }

        hourListAdapter = new HourListAdapter(context, hourList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView_hours.setLayoutManager(layoutManager);

        recyclerView_hours.setAdapter(hourListAdapter);

    }


    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }

    public void setOnWeekDayViewClickListener(OnWeekDayViewClickListener onWeekDayViewClickListener) {
        this.onWeekDayViewClickListener = onWeekDayViewClickListener;
    }

    public void goToCurrentDate() {
        AppConstants.main_calendar = java.util.Calendar.getInstance();

        if (AppConstants.isShowMonth) {
            setMonthView("");
        } else if (AppConstants.isShowMonthWithBellowEvents) {
            setMonthViewWithBelowEvents("");
        } else if (AppConstants.isShowWeek) {
            setWeekView("");
        } else if (AppConstants.isShowDay) {
            setDayView("");
        } else if (AppConstants.isAgenda) {
            setAgendaView("");
        }
    }

    public void refreshCalendar() {
        if (AppConstants.isShowMonth) {
            setMonthView("");
        } else if (AppConstants.isShowMonthWithBellowEvents) {
            setMonthViewWithBelowEvents("");
        } else if (AppConstants.isShowWeek) {
            setWeekView("");
        } else if (AppConstants.isShowDay) {
            setDayView("");
        } else if (AppConstants.isAgenda) {
            setAgendaView("");
        }
    }

    public void setCalendarDate(int date, int month, int year) {
        AppConstants.main_calendar.set(java.util.Calendar.YEAR, year);
        AppConstants.main_calendar.set(java.util.Calendar.MONTH, month - 1);
        AppConstants.main_calendar.set(java.util.Calendar.DAY_OF_MONTH, date);

        refreshCalendar();
    }

}



