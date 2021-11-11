package tn.esprit.health_care;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import health_care.R;

public class HomeActivity extends AppCompatActivity {

    
    MeowBottomNavigation bottomNavigation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigation = findViewById(R.id.buttom_navigation);
        /*username = findViewById(R.id.usernameTv);
        String userN = getIntent().getStringExtra("username");

        username.setText(userN);*/

        //Add menu items
        bottomNavigation.add( new MeowBottomNavigation.Model(1,R.drawable.ic_search));
        bottomNavigation.add( new MeowBottomNavigation.Model(2,R.drawable.images));
        bottomNavigation.add( new MeowBottomNavigation.Model(3,R.drawable.ic_person));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null ;
                switch (item.getId()){
                    case 1:
                        fragment = new infFragment();
                        break;
                    case 2:
                        fragment = new InfermierFragment();
                        break;
                    case 3:
                        fragment = new ProfileFragment();
                        break;

                }

                loadFragment(fragment);
            }
        });

        bottomNavigation.show(2 ,true);
        //bottomNavigation.setCount(2,"10");
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(),"you clicked"+item.getId(),Toast.LENGTH_SHORT).show();

            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(),"You reselected"+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

    }
}