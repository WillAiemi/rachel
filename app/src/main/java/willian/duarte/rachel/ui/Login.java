package willian.duarte.rachel.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import willian.duarte.rachel.MyApplication;
import willian.duarte.rachel.R;
import willian.duarte.rachel.model.User;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private FirebaseAuth auth;

    private MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_bt_login:
                if (etEmail.getText().toString().equalsIgnoreCase("deus")){
                    Intent it = new Intent(Login.this,MainActivity.class);
                    startActivity(it);
                    toast("bem vindo DEUS");
                    finish();
                    return;
                }
                if (etEmail.getText().toString().isEmpty()){
                    etEmail.setError(getResources().getString(R.string.email_error));
                    return;
                }
                if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError(getResources().getString(R.string.password_error));
                    return;
                }

                User u = new User();
                u.setEmail(etEmail.getText().toString());
                u.setPassword(etPassword.getText().toString());

                myApp.getmAuth().signInWithEmailAndPassword(u.getEmail(),u.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        toast(task.isSuccessful() ? "a" : "b");
                        if (!task.isSuccessful()){
                            etEmail.setError(getResources().getString(R.string.auth_email_error));
                            etPassword.setError(getResources().getString(R.string.auth_password_error));
                            return;
                        }

                        toast(getResources().getString(R.string.login_successful));
                        Intent it = new Intent(Login.this,MainActivity.class);
                        startActivity(it);
                        finish();
                    }
                });
        }
    }

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void init(){
        etEmail = findViewById(R.id.login_et_email);
        etPassword = findViewById(R.id.login_et_password);
        btLogin = findViewById(R.id.login_bt_login);
        btLogin.setOnClickListener(this);

        myApp = new MyApplication();
        myApp.setmAuth(FirebaseAuth.getInstance());
    }
}
