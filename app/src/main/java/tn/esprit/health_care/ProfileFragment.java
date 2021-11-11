package tn.esprit.health_care;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import health_care.R;
import tn.esprit.health_care.database.MyDatabase;
import tn.esprit.health_care.entities.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private SharedPreferences mPreferences;
    public static final String sharedPrefFile = "tn.esprit.health_care";
    EditText userEt ,emailUEt , phoneEt,editPTv , editPTv2 ;
    TextView usernameTv , logoutTv,editP,changeP,saveU,saveP ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userEt = view.findViewById(R.id.userEt);
        emailUEt = view.findViewById(R.id.emailUEt);
        phoneEt = view.findViewById(R.id.phoneEt);
        editPTv = view.findViewById(R.id.editPTv);
        editPTv2 = view.findViewById(R.id.editPTv2) ;
        usernameTv = view.findViewById(R.id.usernameTv);
        logoutTv = view.findViewById(R.id.logoutTv);
        editP = view.findViewById(R.id.editP);
        changeP = view.findViewById(R.id.changeP) ;
        saveU = view.findViewById(R.id.saveU);
        saveP = view.findViewById(R.id.saveP);

        editPTv.setVisibility(View.GONE);
        editPTv2.setVisibility(View.GONE);
        saveU.setVisibility(View.GONE);
        saveP.setVisibility(View.GONE);
        userEt.setEnabled(false);
        emailUEt.setEnabled(false);
        phoneEt.setEnabled(false);
        MyDatabase myDB = Room.databaseBuilder(getContext(),MyDatabase.class,"my_db")
                .allowMainThreadQueries().build();
        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        final int idUser = mPreferences.getInt("idUser",1);
        User user = myDB.userdDAO().getUserById(idUser);
        usernameTv.setText(user.getUsername());
        userEt.setText(user.getUsername());
        emailUEt.setText(user.getEmail());
        phoneEt.setText(user.getPhone());

        editP.setOnClickListener(view14 -> {
            saveU.setVisibility(View.VISIBLE);
            userEt.setEnabled(true);
            emailUEt.setEnabled(true);
            phoneEt.setEnabled(true);
            saveP.setVisibility(View.GONE);
            editPTv.setVisibility(View.GONE);
            editPTv2.setVisibility(View.GONE);


            saveU.setOnClickListener(view13 -> {
                 int userUp = myDB.userdDAO().updateUser(userEt.getText().toString(),emailUEt.getText().toString()
                        ,phoneEt.getText().toString(),idUser);
                userEt.setEnabled(false);
                emailUEt.setEnabled(false);
                phoneEt.setEnabled(false);
                saveU.setVisibility(View.GONE);
                user.setConnected(false);

                Toast.makeText(getContext(),"“Your account details have been saved.”",Toast.LENGTH_SHORT).show();
            });

        });

        changeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPTv.setVisibility(View.VISIBLE);
                editPTv2.setVisibility(View.VISIBLE);
                saveP.setVisibility(View.VISIBLE);
                saveU.setVisibility(View.GONE);
                userEt.setEnabled(false);
                emailUEt.setEnabled(false);
                phoneEt.setEnabled(false);


                saveP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!editPTv.getText().toString().equals(editPTv2.getText().toString()))
                            Toast.makeText(getContext(),"passwords not match", Toast.LENGTH_SHORT).show();
                        else{
                            int passUp = myDB.userdDAO().updatePasswordUser(editPTv.getText().toString(),user.getId());
                            Toast.makeText(getContext(),"password changed with success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });



        logoutTv.setOnClickListener(view12 -> {
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.clear();
            preferencesEditor.apply();
            startActivity(new Intent(getActivity(),MainActivity.class));

        });




    }
}