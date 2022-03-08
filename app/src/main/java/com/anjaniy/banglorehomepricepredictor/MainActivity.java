package com.anjaniy.banglorehomepricepredictor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.anjaniy.banglorehomepricepredictor.fragments.About_App;
import com.anjaniy.banglorehomepricepredictor.fragments.Predictor;
import com.anjaniy.banglorehomepricepredictor.fragments.Saved_Predictions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar main_toolbar;
    private Toolbar nav_toolbar;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widgetSetup();

        //WILL GET MESSAGE FRAGMENT AS DEFAULT FRAGMENT.
        if(savedInstanceState == null){
            main_toolbar.setTitle("Predictor");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Predictor()).commit();
            navigationView.setCheckedItem(R.id.predictor);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.predictor:
                    main_toolbar.setTitle("Predictor");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Predictor()).commit();
                    break;

                case R.id.saved_prediction:
                    main_toolbar.setTitle("Saved Predictions");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Saved_Predictions()).commit();
                    break;

                case R.id.about_app:
                    main_toolbar.setTitle("About App");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new About_App()).commit();
                    break;

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void widgetSetup() {
        //CUSTOM - TOOLBAR: -
        main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        nav_toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(nav_toolbar);

        navigationView = findViewById(R.id.nav_menu);
        drawerLayout = findViewById(R.id.Drawer_Layout);
        drawerLayout.setFitsSystemWindows(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,main_toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //ON_BACK_PRESSED() - METHOD: -
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else {
            exitApp();
        }
    }

    private void exitApp() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);

        builder.setMessage("Do You Want To Close This App?")

                .setCancelable(false)

                //CODE FOR POSITIVE(YES) BUTTON: -
                .setPositiveButton("Yes", (dialog, which) -> {
                    //ACTION FOR "YES" BUTTON: -
                    finishAffinity();
                })

                //CODE FOR NEGATIVE(NO) BUTTON: -
                .setNegativeButton("No", (dialog, which) -> {
                    //ACTION FOR "NO" BUTTON: -
                    dialog.cancel();

                });

        //CREATING A DIALOG-BOX: -
        AlertDialog alertDialog = builder.create();
        //SET TITLE MAUALLY: -
        alertDialog.setTitle("Exit");
        alertDialog.show();
    }
}