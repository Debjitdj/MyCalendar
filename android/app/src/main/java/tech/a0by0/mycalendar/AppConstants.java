package tech.a0by0.mycalendar;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Debjit on 23-Oct-17.
 */

public class AppConstants {

    public static ArrayList<EventModel> eventList = new ArrayList<>();
    public static ArrayList<String> holidayList = new ArrayList<>();

    public static java.util.Calendar main_calendar = java.util.Calendar.getInstance();

    public static SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
    public static SimpleDateFormat sdfHourMinute = new SimpleDateFormat("HH:mm");

    public static boolean isShowMonth = false;
    public static boolean isShowMonthWithBellowEvents = false;
    public static boolean isShowWeek = false;
    public static boolean isShowDay = false;
    public static boolean isAgenda = false;

    public static boolean isSaturdayOff = false;
    public static boolean isSundayOff = false;
    public static boolean isHolidayCellClickable = false;

    public static String strSaturdayOffBackgroundColor = "null";
    public static int saturdayOffBackgroundColor = -1;

    public static String strSaturdayOffTextColor = "null";
    public static int saturdayOffTextColor = -1;

    public static String strSundayOffBackgroundColor = "null";
    public static int sundayOffBackgroundColor = -1;

    public static String strSundayOffTextColor = "null";
    public static int sundayOffTextColor = -1;

    public static String strExtraDatesBackgroundColor = "null";
    public static int extraDatesBackgroundColor = -1;

    public static String strExtraDatesTextColor = "null";
    public static int extraDatesTextColor = -1;

    public static String strDatesBackgroundColor = "null";
    public static int datesBackgroundColor = -1;

    public static String strDatesTextColor = "null";
    public static int datesTextColor = -1;

    public static String strCurrentDateBackgroundColor = "null";
    public static int currentDateBackgroundColor = -1;

    public static String strCurrentDateTextColor = "null";
    public static int currentDateTextColor = -1;

    public static String strEventCellBackgroundColor = "null";
    public static int eventCellBackgroundColor = -1;

    public static String strEventCellTextColor = "null";
    public static int eventCellTextColor = -1;

    public static String strBelowMonthEventTextColor = "null";
    public static int belowMonthEventTextColor = -1;

    public static String strBelowMonthEventDividerColor = "null";
    public static int belowMonthEventDividerColor = -1;

    public static String strHolidayCellBackgroundColor = "null";
    public static int holidayCellBackgroundColor = -1;

    public static String strHolidayCellTextColor = "null";
    public static int holidayCellTextColor = -1;

    public static String strClickedDateCellBackgroundColor = "null";
    public static int ClickedDateCellBackgroundColor = -1;

    public static String strClickedDateCellTextColor = "null";
    public static int ClickedDateCellTextColor = -1;

    public static String strClickedEventCellBackgroundColor = "null";
    public static int ClickedEventCellBackgroundColor = -1;

    public static String strClickedEventCellTextColor = "null";
    public static int ClickedEventCellTextColor = -1;

    public static Date clickeddate;

    public static EventModel clickedeventmodel;

    public static View v = null;
    public static View olddatell = null;
    public static View oldeventll = null;
    public static DateModel olddatemodel = null;

    public static Boolean unclickedEvent = false;
    public static Boolean unclickedDate = false;

    public static boolean StartingDayOfWeekSunday;
    public static boolean dataRetrievedFromDB = false;

}