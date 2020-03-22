package tech.a0by0.mycalendar;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Debjit on 23-Oct-17.
 */

public class DateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DateModel> dateModelList;

    private java.util.Calendar calendar1 = java.util.Calendar.getInstance();
    private Date date_current_date = calendar1.getTime();

    private OnMonthBellowEventsDateClickListener onMonthBellowEventsDateClickListener;
    private OnDateClickListener onDateClickListener;



    public DateListAdapter(Context context, ArrayList<DateModel> dateModelList) {
        this.context = context;
        this.dateModelList = dateModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_date, parent, false);
        AppConstants.v = v;
        return new DateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DateModel dateModel = dateModelList.get(position);

        DateViewHolder dateViewHolder = (DateViewHolder) holder;

        dateViewHolder.setDates(dateModel);

    }

    @Override
    public int getItemCount() {
        return dateModelList.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_month_date, tv_week_date, tv_event_simbol;
        private LinearLayout ll_sub_parrent;


        public DateViewHolder(View itemView) {
            super(itemView);
            tv_month_date = (TextView) itemView.findViewById(R.id.tv_month_date);
            tv_week_date = (TextView) itemView.findViewById(R.id.tv_week_date);
            ll_sub_parrent = (LinearLayout) itemView.findViewById(R.id.ll_sub_parrent);
            tv_event_simbol = (TextView) itemView.findViewById(R.id.tv_event_simbol);

        }

        public void colorset(final DateModel model,final View v){
            Date date_current_month_date = AppConstants.main_calendar.getTime();

            tv_month_date = (TextView) v.findViewById(R.id.tv_month_date);
            tv_week_date = (TextView) v.findViewById(R.id.tv_week_date);
            ll_sub_parrent = (LinearLayout) v.findViewById(R.id.ll_sub_parrent);
            tv_event_simbol = (TextView) v.findViewById(R.id.tv_event_simbol);

            // set month view dates
            if (model.getFlag().equals("month")) {
                tv_week_date.setVisibility(View.GONE);
                tv_month_date.setVisibility(View.VISIBLE);

                tv_month_date.setText(String.valueOf(model.getDates().getDate()));

                // set extra dates of month color & current dates of month color
                if (model.getDates().getMonth() == date_current_month_date.getMonth() && model.getDates().getYear() == date_current_month_date.getYear()) {

                    if (AppConstants.datesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.datesBackgroundColor);
                    }

                    if (!AppConstants.strDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                    }

                    if (AppConstants.datesTextColor != -1) {
                        tv_month_date.setTextColor(AppConstants.datesTextColor);
                    }

                    if (!AppConstants.strDatesTextColor.equals("null")) {
                        tv_month_date.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                    } else {
                        tv_month_date.setTextColor(context.getResources().getColor(R.color.black));
                    }

                } else {

                    if (AppConstants.extraDatesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.extraDatesBackgroundColor);
                    }

                    if (!AppConstants.strExtraDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strExtraDatesBackgroundColor));
                    }

                    if (AppConstants.extraDatesTextColor != -1) {
                        tv_month_date.setTextColor(AppConstants.extraDatesTextColor);
                    }

                    if (!AppConstants.strExtraDatesTextColor.equals("null")) {
                        tv_month_date.setTextColor(Color.parseColor(AppConstants.strExtraDatesTextColor));
                    } else {
                        tv_month_date.setTextColor(context.getResources().getColor(R.color.whiteDark8));
                    }

                }

                // set all saturday color
                if (AppConstants.isSaturdayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Saturday")) {
                        if (AppConstants.saturdayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.saturdayOffBackgroundColor);
                        }

                        if (!AppConstants.strSaturdayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSaturdayOffBackgroundColor));
                        }

                        if (AppConstants.saturdayOffTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.saturdayOffTextColor);
                        }

                        if (!AppConstants.strSaturdayOffTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strSaturdayOffTextColor));
                        }

                    }
                }

                // set all sunday color
                if (AppConstants.isSundayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Sunday")) {

                        if (AppConstants.sundayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.sundayOffBackgroundColor);
                        }

                        if (!AppConstants.strSundayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSundayOffBackgroundColor));
                        }

                        if (AppConstants.sundayOffTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.sundayOffTextColor);
                        }

                        if (!AppConstants.strSundayOffTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strSundayOffTextColor));
                        }

                    }
                }

                // set holiday color
                for (int i = 0; i < AppConstants.holidayList.size(); i++) {
                    if (AppConstants.holidayList.get(i).equals(AppConstants.sdfDate.format(model.getDates()))) {
                        if (AppConstants.holidayCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.holidayCellBackgroundColor);
                        }

                        if (!AppConstants.strHolidayCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strHolidayCellBackgroundColor));
                        }

                        if (AppConstants.holidayCellTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.holidayCellTextColor);
                        }

                        if (!AppConstants.strHolidayCellTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strHolidayCellTextColor));
                        }

                        if (AppConstants.isHolidayCellClickable) {
                            ll_sub_parrent.setClickable(true);
                            ll_sub_parrent.setEnabled(true);
                        } else {
                            ll_sub_parrent.setClickable(false);
                            ll_sub_parrent.setEnabled(false);
                        }

                    }
                }

                // set event color
                for (int i = 0; i < AppConstants.eventList.size(); i++) {
                    if (AppConstants.eventList.get(i).getStrDate().equals(AppConstants.sdfDate.format(model.getDates()))) {
                        tv_event_simbol.setVisibility(View.VISIBLE);

                        if (AppConstants.eventList.get(i).getImage() != -1) {
                            tv_event_simbol.setBackgroundResource(AppConstants.eventList.get(i).getImage());
                        } else {
                            tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                        }

                        if (AppConstants.eventCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                        }

                        if (!AppConstants.strEventCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strEventCellBackgroundColor));
                        }

                        if (AppConstants.eventCellTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.eventCellTextColor);
                        }

                        if (!AppConstants.strEventCellTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        }

                    } else {
                        tv_event_simbol.setVisibility(View.VISIBLE);
                    }
                }

                // set current date color
                if (model.getDates().getDate() == date_current_date.getDate() && model.getDates().getMonth() == date_current_date.getMonth() && model.getDates().getYear() == date_current_date.getYear()) {

                    if (AppConstants.currentDateBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.currentDateBackgroundColor);
                    }

                    if (!AppConstants.strCurrentDateBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strCurrentDateBackgroundColor));
                    }

                    if (AppConstants.currentDateTextColor != -1) {
                        tv_month_date.setTextColor(AppConstants.currentDateTextColor);
                    }

                    if (!AppConstants.strCurrentDateTextColor.equals("null")) {
                        tv_month_date.setTextColor(Color.parseColor(AppConstants.strCurrentDateTextColor));
                    } else {
                        tv_month_date.setTextColor(Color.BLUE);
                    }

                }

            }
            // set week view dates
            else if (model.getFlag().equals("week")) {
                tv_month_date.setVisibility(View.GONE);
                tv_week_date.setVisibility(View.VISIBLE);

                tv_week_date.setText(String.valueOf(model.getDates().getDate()));

                // set extra dates of month color & current dates of month color
                if (model.getDates().getMonth() == date_current_month_date.getMonth() && model.getDates().getYear() == date_current_month_date.getYear()) {

                    if (AppConstants.datesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.datesBackgroundColor);
                    }

                    if (!AppConstants.strDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                    }

                    if (AppConstants.datesTextColor != -1) {
                        tv_week_date.setTextColor(AppConstants.datesTextColor);
                    }

                    if (!AppConstants.strDatesTextColor.equals("null")) {
                        tv_week_date.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                    } else {
                        tv_week_date.setTextColor(context.getResources().getColor(R.color.black));
                    }

                } else {

                    if (AppConstants.extraDatesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.extraDatesBackgroundColor);
                    }

                    if (!AppConstants.strExtraDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strExtraDatesBackgroundColor));
                    }

                    if (AppConstants.extraDatesTextColor != -1) {
                        tv_week_date.setTextColor(AppConstants.extraDatesTextColor);
                    }

                    if (!AppConstants.strExtraDatesTextColor.equals("null")) {
                        tv_week_date.setTextColor(Color.parseColor(AppConstants.strExtraDatesTextColor));
                    } else {
                        tv_week_date.setTextColor(context.getResources().getColor(R.color.whiteDark8));
                    }

                }

                // set all saturday color
                if (AppConstants.isSaturdayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Saturday")) {
                        if (AppConstants.saturdayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.saturdayOffBackgroundColor);
                        }

                        if (!AppConstants.strSaturdayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSaturdayOffBackgroundColor));
                        }

                        if (AppConstants.saturdayOffTextColor != -1) {
                            tv_week_date.setTextColor(AppConstants.saturdayOffTextColor);
                        }

                        if (!AppConstants.strSaturdayOffTextColor.equals("null")) {
                            tv_week_date.setTextColor(Color.parseColor(AppConstants.strSaturdayOffTextColor));
                        }

                    }
                }

                // set all sunday color
                if (AppConstants.isSundayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Sunday")) {

                        if (AppConstants.sundayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.sundayOffBackgroundColor);
                        }

                        if (!AppConstants.strSundayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSundayOffBackgroundColor));
                        }

                        if (AppConstants.sundayOffTextColor != -1) {
                            tv_week_date.setTextColor(AppConstants.sundayOffTextColor);
                        }

                        if (!AppConstants.strSundayOffTextColor.equals("null")) {
                            tv_week_date.setTextColor(Color.parseColor(AppConstants.strSundayOffTextColor));
                        }

                    }
                }

                // set holiday color
                for (int i = 0; i < AppConstants.holidayList.size(); i++) {
                    if (AppConstants.holidayList.get(i).equals(AppConstants.sdfDate.format(model.getDates()))) {

                        if (AppConstants.holidayCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.holidayCellBackgroundColor);
                        }

                        if (!AppConstants.strHolidayCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strHolidayCellBackgroundColor));
                        }

                        if (AppConstants.holidayCellTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.holidayCellTextColor);
                        }

                        if (!AppConstants.strHolidayCellTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strHolidayCellTextColor));
                        }

                        if (AppConstants.isHolidayCellClickable) {
                            ll_sub_parrent.setClickable(true);
                            ll_sub_parrent.setEnabled(true);
                        } else {
                            ll_sub_parrent.setClickable(false);
                            ll_sub_parrent.setEnabled(false);
                        }

                    }
                }

                // set event color
                for (int i = 0; i < AppConstants.eventList.size(); i++) {
                    if (AppConstants.eventList.get(i).getStrDate().equals(AppConstants.sdfDate.format(model.getDates()))) {
                        tv_event_simbol.setVisibility(View.VISIBLE);

                        if (AppConstants.eventList.get(i).getImage() != -1) {
                            tv_event_simbol.setBackgroundResource(AppConstants.eventList.get(i).getImage());
                        } else {
                            tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                        }

                        if (AppConstants.eventCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                        }

                        if (!AppConstants.strEventCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strEventCellBackgroundColor));
                        }

                        if (AppConstants.eventCellTextColor != -1) {
                            tv_week_date.setTextColor(AppConstants.eventCellTextColor);
                        }

                        if (!AppConstants.strEventCellTextColor.equals("null")) {
                            tv_week_date.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        }

                    } else {
                        tv_event_simbol.setVisibility(View.VISIBLE);
                    }
                }

                // set current date color
                if (model.getDates().getDate() == date_current_date.getDate() && model.getDates().getMonth() == date_current_date.getMonth() && model.getDates().getYear() == date_current_date.getYear()) {

                    if (AppConstants.currentDateBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.currentDateBackgroundColor);
                    }

                    if (!AppConstants.strCurrentDateBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strCurrentDateBackgroundColor));
                    }

                    if (AppConstants.currentDateTextColor != -1) {
                        tv_week_date.setTextColor(AppConstants.currentDateTextColor);
                    }

                    if (!AppConstants.strCurrentDateTextColor.equals("null")) {
                        tv_week_date.setTextColor(Color.parseColor(AppConstants.strCurrentDateTextColor));
                    } else {
                        tv_week_date.setTextColor(Color.BLUE);
                    }

                }

            }
        }

        public void setDates(final DateModel model) {


            colorset(model,AppConstants.v);

            ll_sub_parrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*                    if(!AppConstants.strClickedDateCellBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strClickedDateCellBackgroundColor));
                    }
                    if(AppConstants.ClickedDateCellBackgroundColor!= -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.ClickedDateCellBackgroundColor);
                    }
                    if(!AppConstants.strClickedDateCellTextColor.equals("null")) {
                        tv_week_date.setBackgroundColor(Color.parseColor(AppConstants.strClickedDateCellTextColor));
                    }
                    if(AppConstants.ClickedDateCellTextColor!= -1) {
                        tv_week_date.setBackgroundColor(AppConstants.ClickedDateCellTextColor);
                    }    */

                    /*

                    final Animation animation = new AlphaAnimation(1,0);
                    animation.setDuration(350);
                    animation.setInterpolator(new LinearInterpolator());
                    animation.setRepeatCount(Animation.INFINITE);
                    animation.setRepeatMode(Animation.REVERSE);

                    TextView tv = (TextView) v.findViewById(R.id.tv_month_date);
                    if(AppConstants.oldtV!=null){
                        AppConstants.oldtV.clearAnimation();
                    }
                    tv.startAnimation(animation);

                     */

                    if(AppConstants.olddatell == v){
                /*        TextView oldtv = (TextView) AppConstants.olddatell.findViewById(R.id.tv_month_date);

                        if(!AppConstants.strDatesBackgroundColor.equals("null")) {
                            AppConstants.olddatell.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                        }
                        if(AppConstants.datesBackgroundColor!= -1) {
                            AppConstants.olddatell.setBackgroundColor(AppConstants.datesBackgroundColor);
                        }
                        if(!AppConstants.strDatesTextColor.equals("null")) {
                            oldtv.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                        }
                        if(AppConstants.datesTextColor!= -1) {
                            oldtv.setTextColor(AppConstants.datesTextColor);
                        }    */

                        colorset(model,v);
                        AppConstants.olddatell = null;
                        AppConstants.olddatemodel = null;
                        AppConstants.clickeddate = null;
                        AppConstants.unclickedDate = true;
                    }
                    else{
                        AppConstants.unclickedDate = false;
                        TextView tv = (TextView) v.findViewById(R.id.tv_month_date);
                        if(AppConstants.olddatemodel!=null && AppConstants.olddatell != null){
                            colorset(AppConstants.olddatemodel,AppConstants.olddatell);
                          /*  TextView oldtv = (TextView) AppConstants.olddatell.findViewById(R.id.tv_month_date);

                            if(!AppConstants.strDatesBackgroundColor.equals("null")) {
                                AppConstants.olddatell.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                            }
                            if(AppConstants.datesBackgroundColor!= -1) {
                                AppConstants.olddatell.setBackgroundColor(AppConstants.datesBackgroundColor);
                            }
                            if(!AppConstants.strDatesTextColor.equals("null")) {
                                oldtv.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                            }
                            if(AppConstants.datesTextColor!= -1) {
                                oldtv.setTextColor(AppConstants.datesTextColor);
                            }    */
                        }
                        AppConstants.olddatell = v;
                        AppConstants.olddatemodel = model;
                        if(!AppConstants.strClickedDateCellBackgroundColor.equals("null")) {
                            AppConstants.olddatell.setBackgroundColor(Color.parseColor(AppConstants.strClickedDateCellBackgroundColor));
                        }
                        if(AppConstants.ClickedDateCellBackgroundColor!= -1) {
                            AppConstants.olddatell.setBackgroundColor(AppConstants.ClickedDateCellBackgroundColor);
                        }
                        if(!AppConstants.strClickedDateCellTextColor.equals("null")) {
                            tv.setTextColor(Color.parseColor(AppConstants.strClickedDateCellTextColor));
                        }
                        if(AppConstants.ClickedDateCellTextColor!= -1) {
                            tv.setTextColor(AppConstants.ClickedDateCellTextColor);
                        }
                    }

                    onDateClickListener.onClick(model.getDates());

                    if (AppConstants.isShowMonthWithBellowEvents || AppConstants.isAgenda) {
                        onMonthBellowEventsDateClickListener.onClick(model.getDates());
                    }

                    onDateClickListener.onClick(model.getDates());

                    if (AppConstants.isShowMonthWithBellowEvents || AppConstants.isAgenda) {
                        onMonthBellowEventsDateClickListener.onClick(model.getDates());
                    }
                    //   v.clearAnimation();

                }
            });

            ll_sub_parrent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onDateClickListener.onLongClick(model.getDates());
                    return true;
                }
            });


        }
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }

    public void setOnMonthBellowEventsClick(OnMonthBellowEventsDateClickListener onMonthBellowEventsDateClickListener) {
        this.onMonthBellowEventsDateClickListener = onMonthBellowEventsDateClickListener;
    }


}

