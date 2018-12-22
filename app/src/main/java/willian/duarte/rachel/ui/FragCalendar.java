package willian.duarte.rachel.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pl.rafman.scrollcalendar.ScrollCalendar;
import pl.rafman.scrollcalendar.contract.OnDateClickListener;
import willian.duarte.rachel.MyApplication;
import willian.duarte.rachel.R;
import willian.duarte.rachel.model.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragCalendar extends Fragment {

    private ScrollCalendar calendar;
    private Date date;

    private static final String TAG = "LogsFragCalendar";

    public FragCalendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_calendar, container, false);

        calendar = v.findViewById(R.id.frg_clndr_calendar);

        date = new Date();

        calendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onCalendarDayClicked(int year, int month, int day) {
                Log.e(TAG,"UM");
                if (date.getDay() == day && date.getMonth() == month && date.getYear() == year){

                } else {
                    date.setDay(day);
                    date.setMonth(month);
                    date.setYear(year);
                    toast("Click again etc...");
                }
                Log.e(TAG,"SETE");
                return;
            }
        });

        return v;
    }

    private void toast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
