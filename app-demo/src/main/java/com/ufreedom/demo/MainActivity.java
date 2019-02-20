package com.ufreedom.demo;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ufreedom.module.ModuleFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new ModuleFragment());
        fragmentList.add(new AboutFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_container, fragmentList.get(0))
                            .commitAllowingStateLoss();

                    return true;
                case R.id.navigation_module:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_container, fragmentList.get(1))
                            .commitAllowingStateLoss();
                    return true;
                case R.id.navigation_about:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_container, fragmentList.get(2))
                            .commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };
}
