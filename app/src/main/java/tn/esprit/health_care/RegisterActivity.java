package tn.esprit.health_care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import health_care.R;
import tn.esprit.health_care.dao.UserdDAO;
import tn.esprit.health_care.database.MyDatabase;
import tn.esprit.health_care.entities.User;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEt ;
    EditText emailEt ;
    EditText passwordEt ;
    EditText passwordEt2 ;
    EditText telEt ;
    Button signUpBtn ;
    MyDatabase myDb ;
    public static int iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        TextView tvLogin = findViewById(R.id.tvLogin);

        usernameEt = findViewById(R.id.usernameEt);
        emailEt = findViewById(R.id.emailET);
        passwordEt = findViewById(R.id.passwordEt);
        passwordEt2 = findViewById(R.id.passwordEt2);
        telEt =findViewById(R.id.telET);
        signUpBtn = findViewById(R.id.signUpBtn);

        myDb = MyDatabase.getDatabase(this);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(usernameEt.getText().toString());
                user.setEmail(emailEt.getText().toString());
                user.setPassword(passwordEt.getText().toString());
                user.setPhone(telEt.getText().toString());
                user.setConnected(false);
                if (!passwordEt.getText().toString().equals(passwordEt2.getText().toString()))
                    Toast.makeText(getApplicationContext(),"password not match", Toast.LENGTH_SHORT).show();
                else if ((emailEt.getText().toString().length()>0) & emailValidator((emailEt))){
                    Toast.makeText(getApplicationContext(),"Invalid email",Toast.LENGTH_SHORT).show();
                }
                else if (validateInput(user)){
                    MyDatabase myDatabase = MyDatabase.getDatabase(getApplicationContext());
                    UserdDAO userDAO = myDatabase.userdDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user1 = userDAO.register(user.getUsername(),user.getEmail());

                            if (user1 == null) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        userDAO.insertUser(user);
                                        //   iduser=user1.getId();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                                            }

                                        });

                                    }
                                }).start();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Username/Email exist",Toast.LENGTH_SHORT).show();


                                    }

                                });
                            }
                             //  iduser=user1.getId();
                        }

                    }).start();
                }else
                        Toast.makeText(getApplicationContext(),"Fill all fields ", Toast.LENGTH_SHORT).show();
            }

        });





        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }

    private Boolean validateInput(User user){
        if(user.getEmail().isEmpty()||
        user.getUsername().isEmpty()||
        user.getPassword().isEmpty()||
        user.getPhone().isEmpty()){
            return false ;

        };
        return true ;
    }

    public Boolean emailValidator(EditText etMail) {


        String emailToText = etMail.getText().toString();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            return false ;

        };
        return true ;
    }


}