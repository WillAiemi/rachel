package willian.duarte.rachel.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import willian.duarte.rachel.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout flMainFrame;
    private FloatingActionsMenu fabMenu;
    private com.getbase.floatingactionbutton.FloatingActionButton fabAddEvent;
    private com.getbase.floatingactionbutton.FloatingActionButton fabAddClothes;
    private TextView labelAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fabAddEvent.setColorNormal(R.color.colorPrimaryDark);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                labelAddEvent.setVisibility(View.VISIBLE);
                toast(fabAddEvent.getTitle());
            }

            @Override
            public void onMenuCollapsed() {
                labelAddEvent.setVisibility(View.INVISIBLE);
            }
        });

    } //close onCreate();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ma_fab_add_event:
                toast("hell yeah!");
                return;
        }
    } // close onClick();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    }; // close navigation onClickListeners

    private void init(){
        flMainFrame = findViewById(R.id.ma_fl_mainframe);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fabMenu = findViewById(R.id.ma_fabm_menu);

        fabAddEvent = findViewById(R.id.ma_fab_add_event);
        fabAddEvent.setOnClickListener(this);
        fabAddEvent.setTitle("arroba");

        fabAddClothes = findViewById(R.id.ma_fab_add_clothes);

        labelAddEvent = findViewById(R.id.ma_tv_addevent);
        labelAddEvent.setVisibility(View.INVISIBLE);
    }// close init();

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
    }//close toast();
}
