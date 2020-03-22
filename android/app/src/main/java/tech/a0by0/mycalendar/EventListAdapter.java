package tech.a0by0.mycalendar;

/**
 * Created by Debjit on 23-Oct-17.
 */

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<EventModel> eventModelList;
    private OnEventClickListener onEventClickListener;
    private String strViewFlag;

    private View rootView;

    public EventListAdapter(Context context, ArrayList<EventModel> eventModelList, String strViewFlag) {
        this.context = context;
        this.strViewFlag = strViewFlag;
        this.eventModelList = eventModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_event, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        EventModel eventModel = eventModelList.get(position);

        EventViewHolder showEventsViewHolder = (EventViewHolder) holder;

        showEventsViewHolder.setEvent(eventModel);

    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_month_events;
        TextView tv_event_name, tv_event_date, tv_event_time, tv_event_simbol;
        View v_divider;


        public EventViewHolder(View itemView) {  //this creates the local variables representing the ids of the row_event.xml file
            super(itemView);
            ll_month_events = (LinearLayout) itemView.findViewById(R.id.ll_month_events);
            tv_event_name = (TextView) itemView.findViewById(R.id.tv_event_name);
            tv_event_date = (TextView) itemView.findViewById(R.id.tv_event_date);
            tv_event_time = (TextView) itemView.findViewById(R.id.tv_event_time);
            tv_event_simbol = (TextView) itemView.findViewById(R.id.tv_event_simbol);
            v_divider = (View) itemView.findViewById(R.id.v_divider);

        }

        public void setEvent(final EventModel model) {

            if (strViewFlag.equals("month")) {
                ll_month_events.setVisibility(View.VISIBLE);

                if (model.getImage() != -1) {
                    tv_event_simbol.setBackgroundResource(model.getImage());
                } else {
                    tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                }

                tv_event_name.setText(model.getStrName());
                tv_event_date.setText(model.getStrDate());
                tv_event_time.setText(String.format("%s to %s", model.getStrStartTime(), model.getStrEndTime()));

                if (AppConstants.belowMonthEventTextColor != -1) {
                    tv_event_name.setTextColor(AppConstants.belowMonthEventTextColor);
                    tv_event_date.setTextColor(AppConstants.belowMonthEventTextColor);
                    tv_event_time.setTextColor(AppConstants.belowMonthEventTextColor);
                }

                if (!AppConstants.strBelowMonthEventTextColor.equals("null")) {
                    tv_event_name.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                    tv_event_date.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                    tv_event_time.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                }

                if (AppConstants.belowMonthEventDividerColor != -1) {
                    v_divider.setBackgroundColor(AppConstants.belowMonthEventDividerColor);
                }

                if (!AppConstants.strBelowMonthEventDividerColor.equals("null")) {
                    v_divider.setBackgroundColor(Color.parseColor(AppConstants.strBelowMonthEventDividerColor));
                }

                ll_month_events.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if(AppConstants.oldeventll == v){
                            TextView old_tv_event_name = (TextView) AppConstants.oldeventll.findViewById(R.id.tv_event_name);
                            TextView old_tv_event_date = (TextView) AppConstants.oldeventll.findViewById(R.id.tv_event_date);
                            TextView old_tv_event_time = (TextView) AppConstants.oldeventll.findViewById(R.id.tv_event_time);

                            if(!AppConstants.strDatesBackgroundColor.equals("null")) {
                                AppConstants.oldeventll.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                            }
                            if(AppConstants.datesBackgroundColor!= -1) {
                                AppConstants.oldeventll.setBackgroundColor(AppConstants.datesBackgroundColor);
                            }
                            if(!AppConstants.strDatesTextColor.equals("null")) {
                                old_tv_event_name.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                                old_tv_event_date.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                                old_tv_event_time.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));

                            }
                            if(AppConstants.datesTextColor!= -1) {
                                old_tv_event_name.setTextColor(AppConstants.datesTextColor);
                                old_tv_event_date.setTextColor(AppConstants.datesTextColor);
                                old_tv_event_time.setTextColor(AppConstants.datesTextColor);
                            }


                            AppConstants.oldeventll = null;
                            AppConstants.clickedeventmodel = null;
                            AppConstants.unclickedEvent = true;
                        }
                        else{
                            AppConstants.unclickedEvent = false;
                            TextView tv_event_name = (TextView) v.findViewById(R.id.tv_event_name);
                            TextView tv_event_date = (TextView) v.findViewById(R.id.tv_event_date);
                            TextView tv_event_time = (TextView) v.findViewById(R.id.tv_event_time);
                            if(AppConstants.oldeventll!=null){

                                TextView old_tv_event_name = (TextView) AppConstants.oldeventll.findViewById(R.id.tv_event_name);
                                TextView old_tv_event_date = (TextView) AppConstants.oldeventll.findViewById(R.id.tv_event_date);
                                TextView old_tv_event_time = (TextView) AppConstants.oldeventll.findViewById(R.id.tv_event_time);

                                if(!AppConstants.strDatesBackgroundColor.equals("null")) {
                                    AppConstants.oldeventll.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                                }
                                if(AppConstants.datesBackgroundColor!= -1) {
                                    AppConstants.oldeventll.setBackgroundColor(AppConstants.datesBackgroundColor);
                                }
                                if(!AppConstants.strDatesTextColor.equals("null")) {
                                    old_tv_event_name.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                                    old_tv_event_date.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                                    old_tv_event_time.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));

                                }
                                if(AppConstants.datesTextColor!= -1) {
                                    old_tv_event_name.setTextColor(AppConstants.datesTextColor);
                                    old_tv_event_date.setTextColor(AppConstants.datesTextColor);
                                    old_tv_event_time.setTextColor(AppConstants.datesTextColor);
                                }
                            }
                            AppConstants.oldeventll = v;

                            if(!AppConstants.strClickedDateCellBackgroundColor.equals("null")) {
                                AppConstants.oldeventll.setBackgroundColor(Color.parseColor(AppConstants.strClickedDateCellBackgroundColor));
                            }
                            if(AppConstants.ClickedDateCellBackgroundColor!= -1) {
                                AppConstants.oldeventll.setBackgroundColor(AppConstants.ClickedDateCellBackgroundColor);
                            }
                            if(!AppConstants.strClickedDateCellTextColor.equals("null")) {
                                tv_event_name.setTextColor(Color.parseColor(AppConstants.strClickedDateCellTextColor));
                                tv_event_date.setTextColor(Color.parseColor(AppConstants.strClickedDateCellTextColor));
                                tv_event_time.setTextColor(Color.parseColor(AppConstants.strClickedDateCellTextColor));
                            }
                            if(AppConstants.ClickedDateCellTextColor!= -1) {
                                tv_event_name.setTextColor(AppConstants.ClickedDateCellTextColor);
                                tv_event_date.setTextColor(AppConstants.ClickedDateCellTextColor);
                                tv_event_time.setTextColor(AppConstants.ClickedDateCellTextColor);
                            }
                        }

                        if (onEventClickListener != null) {
                            onEventClickListener.onClick(model);
                        }
                    }
                });

            }


        }
    }

    public void setOnEventClickListener(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }

}




























