package willian.duarte.rachel.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import willian.duarte.rachel.R;

public class Main2Activity extends AppCompatActivity {

    private CalendarView cvCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();

        cvCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String date = day + "/" + (month+1) + "/" + year;
                Intent it = new Intent(Main2Activity.this,MainActivity.class);
                it.putExtra("date",date);
                startActivity(it);
            }
        });
    }

    private void init(){
        cvCalendar = findViewById(R.id.ma2_cv_calendar);
    }
}
