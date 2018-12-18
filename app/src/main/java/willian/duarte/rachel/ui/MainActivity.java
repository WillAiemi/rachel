package willian.duarte.rachel.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import willian.duarte.rachel.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvDate;
    private Button btCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        if (getIntent().hasExtra("date")){
            String date = getIntent().getStringExtra("date");
            tvDate.setText(date);
        }


        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(it);
            }
        });
    }

    private void init(){
        tvDate = findViewById(R.id.ma_tv_date);
        btCalendar = findViewById(R.id.ma_bt_calendar);
    }

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT);
    }
}
