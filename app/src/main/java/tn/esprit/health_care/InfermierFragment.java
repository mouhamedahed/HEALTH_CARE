package tn.esprit.health_care;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import health_care.R;
import tn.esprit.health_care.dao.InfirmierDAO;
import tn.esprit.health_care.database.MyDatabase;
import tn.esprit.health_care.entities.Infermier;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfermierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfermierFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter ;


    FloatingActionButton addCovFab , addLfFab ;
    ExtendedFloatingActionButton addActionFav ;
    TextView addCovTv , addLfTv ;
    Boolean isAllFabVisible ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfermierFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfermierFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfermierFragment newInstance(String param1, String param2) {
        InfermierFragment fragment = new InfermierFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_infirmier, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MyDatabase myDB = Room.databaseBuilder(getContext(),MyDatabase.class,"my_db")
                .allowMainThreadQueries().build();
        List<Infermier> infermiers = myDB.infirmierDAO().getAllInf();
        Log.d("tag","cov"+ infermiers);
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerViewInf adapter = new RecyclerViewInf(getContext(), infermiers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);





        addCovFab = view.findViewById(R.id.add_fab_inf);
        addLfFab = view.findViewById(R.id.add_fab_LF);
        addActionFav = view.findViewById(R.id.add_fab);
        addCovTv =view.findViewById(R.id.add_inf_Tv);
        addLfTv = view.findViewById(R.id.add_lf_Tv);

        addCovFab.setVisibility(View.GONE);
        addLfFab.setVisibility(View.GONE);
        addCovTv.setVisibility(View.GONE);
        addLfTv.setVisibility(View.GONE);

        isAllFabVisible = false ;

        addActionFav.shrink();

        addActionFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase myDatabase = MyDatabase.getDatabase(getContext());
                InfirmierDAO infDAO = myDatabase.infirmierDAO();

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        Log.d("TAG","infirmier"+infDAO.getAllInf());

                    }});
                if(!isAllFabVisible){
                    addCovFab.show();
                    addLfFab.show();
                    addCovTv.setVisibility(view.VISIBLE);
                    addLfTv.setVisibility(view.VISIBLE);

                    addActionFav.extend();
                    isAllFabVisible= true ;
                }else{
                    addCovFab.hide();
                    addLfFab.hide();
                    addCovTv.setVisibility(View.GONE);
                    addLfTv.setVisibility(View.GONE);

                    addActionFav.shrink();
                    isAllFabVisible = false;
                }
            }
        });



        addCovFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddInfermier.class));


            }
        });

        addLfFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Statistique.class));
            }
        });



    }


}