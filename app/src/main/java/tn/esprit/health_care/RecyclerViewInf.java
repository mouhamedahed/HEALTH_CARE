package tn.esprit.health_care;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import health_care.R;
import tn.esprit.health_care.database.MyDatabase;
import tn.esprit.health_care.entities.Infermier;

public class RecyclerViewInf extends RecyclerView.Adapter<RecyclerViewInf.MyViewHolder> {
    Context context ;
    List<Infermier> infermiers;
    MyDatabase myDb ;
    String phoneuser ;
    String username;
    public RecyclerViewInf(Context context, List<Infermier> infermiers) {
        this.context = context;
        this.infermiers = infermiers;
    }


    @Override
    public RecyclerViewInf.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infirmier_view,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewInf.MyViewHolder holder, int position) {
        myDb = MyDatabase.getDatabase(context);

        holder.descf.setText(infermiers.get(position).getDep());

        holder.prixf.setText(infermiers.get(position).getPrice());
        holder.local.setText(infermiers.get(position).getLocalisation());

        MyDatabase myDB = Room.databaseBuilder(context,MyDatabase.class,"my_db")
                .allowMainThreadQueries().build();
                Log.d("position","p"+position);
                 phoneuser = myDB.userdDAO().getPhoneByIdUser(infermiers.get(position).getIdUser());
        username = myDB.userdDAO().getUsernameByIdUser(infermiers.get(position).getIdUser());
        holder.nom.setText(username);
        holder.phone.setText(phoneuser);






    }

    @Override
    public int getItemCount() {
        return infermiers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView descf, local , prixf , nom ,phone;
        public MyViewHolder( View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nomTv);
            descf = itemView.findViewById(R.id.descriptionTv);
            prixf = itemView.findViewById(R.id.coinTv);
            local = itemView.findViewById(R.id.localistationTv);
            phone=itemView.findViewById(R.id.phonetv);
        }
    }
}
