package tn.esprit.health_care;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


import health_care.R;
import tn.esprit.health_care.dao.InfirmierDAO;
import tn.esprit.health_care.database.MyDatabase;
import tn.esprit.health_care.entities.Infermier;

public class AddInfermier extends AppCompatActivity {
    private SharedPreferences mPreferences;
    public static final String sharedPrefFile = "tn.esprit.health_care";
    EditText  descEt , localisationEt , prixEt  ;
    Button ajout_f ;
    MyDatabase myDb ;

    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_infirmier);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        descEt = findViewById(R.id.desc);
        localisationEt = findViewById(R.id.localisation) ;
        prixEt = findViewById(R.id.PrixEt) ;
        ajout_f = findViewById(R.id.AjoutCoBtn) ;





        myDb = MyDatabase.getDatabase(this);

        ajout_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String desc = descEt.getText().toString();
                final String localistion = localisationEt.getText().toString();
                final String prix = prixEt.getText().toString();
                final int idUser = mPreferences.getInt("idUser",0);

                Infermier infermier = new Infermier();
                infermier.setDep(desc);
                infermier.setLocalisation(localistion);
                infermier.setPrice(prix);
                infermier.setIdUser(idUser);


                 if(validateInput(infermier)){
                    MyDatabase myDatabase = MyDatabase.getDatabase(getApplicationContext());
                    InfirmierDAO infDAO = myDatabase.infirmierDAO();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            infDAO.insertOne(infermier);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("TAG","idUser: "+idUser);
                                    Toast.makeText(getApplicationContext()," Registered",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(AddInfermier.this,HomeActivity.class));



                                }

                            });

                        }
                    }).start();
                }else
                    Toast.makeText(getApplicationContext(),"Fill all fields ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Boolean validateInput(Infermier infermier){
        if(infermier.getDep().isEmpty()||
                infermier.getPrice().isEmpty()||
                infermier.getLocalisation().isEmpty()
               ){
            return false ;

        };
        return true ;
    }



}