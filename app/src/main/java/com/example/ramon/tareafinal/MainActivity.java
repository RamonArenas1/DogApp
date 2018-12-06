package com.example.ramon.tareafinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView mMain_nav;
    public FrameLayout mMain_frame;




    private BreedsFragment breedsFragment;
    private RandomFragment randomFragment;
    private FavoritesFragment favoritesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavService();

    }

    private void bottomNavService(){
        mMain_nav = findViewById(R.id.bottom_navigation);

        breedsFragment = new BreedsFragment();
        randomFragment = new RandomFragment();
        favoritesFragment = new FavoritesFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, randomFragment);
        fragmentTransaction.commit();

        mMain_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.itemRandom:
                        setFragment(randomFragment);
                        return true;

                    case R.id.itemBreeds:
                        setFragment(breedsFragment);
                        return true;

                    case R.id.itemFavorites:
                        setFragment(favoritesFragment);
                        return true;

                    default:
                        return false;

                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }

}
