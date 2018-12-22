package willian.duarte.rachel.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import pl.rafman.scrollcalendar.ScrollCalendar;
import pl.rafman.scrollcalendar.contract.DateWatcher;
import pl.rafman.scrollcalendar.contract.OnDateClickListener;
import pl.rafman.scrollcalendar.data.CalendarDay;
import willian.duarte.rachel.MyApplication;
import willian.duarte.rachel.R;
import willian.duarte.rachel.model.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragCalendar extends Fragment {

    OnDateSelectedListener mCallback;

    private ScrollCalendar calendar;
    private Date date;

    private static final String TAG = "LogsFragCalendar";

    public FragCalendar() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_calendar, container, false);

        calendar = v.findViewById(R.id.frg_clndr_calendar);

        date = new Date();

        calendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onCalendarDayClicked(int year, int month, int day) {
                if (date.getDay() == day && date.getMonth() == month && date.getYear() == year){
                    mCallback.onDateSelected(date);
                } else {
                    date.setDay(day);
                    date.setMonth(month);
                    date.setYear(year);
//                    Calendar calendar = Calendar.getInstance();
//                    toast(calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));
                }
                return;
            }
        });

        calendar.setDateWatcher(new DateWatcher() {
            @Override
            public int getStateForDate(int year, int month, int day) {
                if (date.getDay() == day && date.getMonth() == month && date.getYear() == year){
                    return CalendarDay.SELECTED;
                }
                Calendar calendar = Calendar.getInstance();
                if (year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH) && day == calendar.get(Calendar.DAY_OF_MONTH)){
                    return CalendarDay.TODAY;
                }
                return CalendarDay.DEFAULT;
            }
        });

        return v;
    } // close onCreateView();

    public void setOnDateSelectedListener(Activity activity){
        mCallback = (OnDateSelectedListener) activity;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }

    private void toast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    } // close toast();
}
