package willian.duarte.rachel.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import willian.duarte.rachel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment implements View.OnClickListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView tvDisplay;
    private EditText etEmail;
    private Button btLogoff;

    public Settings() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        init(v);
        if (user.isAnonymous()){
            tvDisplay.setText(getResources().getString(R.string.anonymous));
        } else {
            tvDisplay.setText(user.getDisplayName());
            etEmail.setText(user.getEmail());
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_bt_logoff:
                auth.signOut();
                startActivity(new Intent(getActivity(),Login.class));
                break;
        }
    }

    private void init(View v) {
        tvDisplay = v.findViewById(R.id.setting_tv_display);
        etEmail = v.findViewById(R.id.setting_et_email);
        btLogoff = v.findViewById(R.id.setting_bt_logoff);
        btLogoff.setOnClickListener(this);
    }
}
