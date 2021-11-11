package tn.esprit.health_care;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

import health_care.R;
import tn.esprit.health_care.database.MyDatabase;
import tn.esprit.health_care.entities.Infermier;

public class Statistique extends AppCompatActivity {


    PieChart pie;
    TextView f,cl,t,r;
    Integer varIdUser = RegisterActivity.iduser;

    public Integer sumtunis=2;
    public Integer sumsousse=1;
    public Integer sumnabel=2;
    public Integer summanouba=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        new Thread(() -> {
            MyDatabase userDataBase = MyDatabase.getDatabase(getApplicationContext());


            //List<Infermier> all =userDataBase.infirmierDAO().getSumExpenseByCAT(varIdUser);
            //Log.d("Tag All","Expenses"+all);
        });
        f=(TextView) findViewById(R.id.tunis);
        f.setVisibility(View.INVISIBLE);
        cl=(TextView) findViewById(R.id.nabel);
        cl.setVisibility(View.INVISIBLE);
        t=(TextView) findViewById(R.id.sousse);
        t.setVisibility(View.INVISIBLE);
        r=(TextView) findViewById(R.id.beja);
        r.setVisibility(View.INVISIBLE);


        pie = (PieChart) findViewById(R.id.piechart);
        addToPieChart();
        f.setVisibility(View.VISIBLE);
        cl.setVisibility(View.VISIBLE);
        t.setVisibility(View.VISIBLE);
        r.setVisibility(View.VISIBLE);
        f.append(" : "+sumtunis.toString()+"%");
        cl.append(" : "+sumsousse.toString()+"%");
        t.append(" : "+sumnabel.toString()+"%");
        r.append(" : "+summanouba.toString()+"%");

    }

    private void addToPieChart() {

        pie.addPieSlice(new PieModel("Tunis" ,sumtunis, Color.parseColor("#FE6DA8")));
        pie.addPieSlice(new PieModel("manouba",sumsousse, Color.parseColor("#56B7F1")));
        pie.addPieSlice(new PieModel("nabel",sumnabel, Color.parseColor("#DEFF0E")));
        pie.addPieSlice(new PieModel("sousse",summanouba, Color.parseColor("#CDA67F")));

        pie.startAnimation();
    }



}