package com.tesji.recicladora;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BuyMatFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_buy_mat);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_ver_mat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShowMatFragment()).commit();
                break;
            case R.id.nav_ver_com:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShowBuysFragment()).commit();
                break;
            case R.id.nav_buy_mat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BuyMatFragment()).commit();
                break;
            case R.id.nav_act_mat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UpdateMatFragment()).commit();
                break;
            case R.id.nav_search_mat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchMatFragment()).commit();
                break;
            case R.id.nav_add_mat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddMatFragment()).commit();
                break;
            case R.id.nav_delet_mat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DeleteMatFragment()).commit();
                break;
            case R.id.nav_exit:
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

}


