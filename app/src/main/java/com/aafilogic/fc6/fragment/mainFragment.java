package com.aafilogic.fc6.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.aafilogic.fc6.R;


public class mainFragment extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        loadFragment(new profileFragment());


        navigationChangeListner();
        navigation.setSelectedItemId(R.id.nav_profile);


    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void navigationChangeListner() {
        navigation = findViewById(R.id.navigation);


        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        Fragment fragment = null;

        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                fragment = new timelineFragment();
                break;

            case R.id.nav_profile:
                fragment = new profileFragment();
                break;


            case R.id.notifcation:
                fragment = new notificationFragment();
                break;

            case R.id.Challenge:
                fragment = new challengeFragment();
                break;


            case R.id.menu:
                fragment = new menuFragment();
                break;


        }

        return loadFragment(fragment);

    }


}
