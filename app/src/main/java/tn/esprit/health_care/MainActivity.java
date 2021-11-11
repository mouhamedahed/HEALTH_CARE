package tn.esprit.health_care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import health_care.R;
import tn.esprit.health_care.dao.UserdDAO;
import tn.esprit.health_care.database.MyDatabase;
import tn.esprit.health_care.entities.User;


public class MainActivity extends AppCompatActivity {
    EditText usernameEt ;
    EditText passEt ;
    private SharedPreferences mPreferences;
    public static final String sharedPrefFile = "tn.esprit.health_care";
    Button signInBtn ;
    TextView forgtPw ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {

        }
        setContentView(R.layout.activity_main);

        usernameEt = findViewById(R.id.usernameEt);
        passEt = findViewById(R.id.passEt);
        signInBtn = findViewById(R.id.signinBtn);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        forgtPw = findViewById(R.id.ForgetPw);

        forgtPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgottenPassword.class));
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameEt.getText().toString();
                final String password = passEt.getText().toString();
                if (username.isEmpty() || (password.isEmpty())){
                    Toast.makeText(getApplicationContext(),"Fill all fields ",Toast.LENGTH_SHORT).show();
                }
                else {
                    MyDatabase myDatabase = MyDatabase.getDatabase(getApplicationContext());
                    UserdDAO userDAO = myDatabase.userdDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDAO.login(username,password);
                            if (user == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                                preferencesEditor.putString("login", usernameEt.getText().toString());
                                preferencesEditor.putString("password", passEt.getText().toString());
                                preferencesEditor.putInt("idUser", user.getId());
                                preferencesEditor.apply();
                                user.setConnected(true);

                                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                }
                            }

                    }).start();
                }
            }
        });

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        TextView tvSignUpSecond = findViewById(R.id.tvSignUpSecond);

        tvSignUp.setOnClickListener(onClickSignUp());
        tvSignUpSecond.setOnClickListener(onClickSignUp());
    }


    private View.OnClickListener onClickSignUp() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(intent);
            }
        };
    }
}