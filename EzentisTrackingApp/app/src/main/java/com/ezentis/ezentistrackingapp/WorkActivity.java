package com.ezentis.ezentistrackingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;

import java.util.List;

public class WorkActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, BaseListListener<Record> {

    public static final String STATE = "state";
    private State _currentState;
    private State _nextState;
    private TextView startWork;
    private TextView startBreak;
    private TextView finishBreak;
    private TextView finishWork;
    private DDLListScreenlet ddlListScreenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        TextView user = (TextView) drawer.findViewById(R.id.user_text);
        User loggedUser = SessionContext.getCurrentUser();

        if (user != null) {
            user.setText(loggedUser.getFirstName());
        }

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ddlListScreenlet = (DDLListScreenlet) findViewById(R.id.ddllist_screenlet);
        ddlListScreenlet.setListener(this);

        startWork = (TextView) findViewById(R.id.start_work);
        startWork.setOnClickListener(this);
        startBreak = (TextView) findViewById(R.id.start_break);
        startBreak.setOnClickListener(this);
        finishBreak = (TextView) findViewById(R.id.finish_break);
        finishBreak.setOnClickListener(this);
        finishWork = (TextView) findViewById(R.id.finish_work);
        finishWork.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences();

        _currentState = State.fromInt(sharedPreferences.getInt(STATE, 0));

        hideButtons(_currentState);

        ddlListScreenlet.loadPage(0);
    }

    private void hideButtons(State currentState) {
        switch (currentState) {
            case NOTHING:
                startWork.setVisibility(View.VISIBLE);
                startBreak.setVisibility(View.GONE);
                finishBreak.setVisibility(View.GONE);
                finishWork.setVisibility(View.GONE);
                break;
            case START_DAY:
                startWork.setVisibility(View.GONE);
                startBreak.setVisibility(View.VISIBLE);
                finishBreak.setVisibility(View.GONE);
                finishWork.setVisibility(View.VISIBLE);
                break;
            case START_PAUSE:
                startWork.setVisibility(View.GONE);
                startBreak.setVisibility(View.GONE);
                finishBreak.setVisibility(View.VISIBLE);
                finishWork.setVisibility(View.GONE);
                break;
            case END_PAUSE:
                startWork.setVisibility(View.GONE);
                startBreak.setVisibility(View.GONE);
                finishBreak.setVisibility(View.GONE);
                finishWork.setVisibility(View.VISIBLE);
                break;
            case END_DAY:
                startWork.setVisibility(View.VISIBLE);
                startBreak.setVisibility(View.GONE);
                finishBreak.setVisibility(View.GONE);
                finishWork.setVisibility(View.GONE);
                break;
        }
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences("PREFERENCES", MODE_PRIVATE);
    }

    @Override
    public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

    }

    @Override
    public void onListPageReceived(BaseListScreenlet source, int page, List<Record> entries, int rowCount) {

    }

    @Override
    public void onListItemSelected(Record record, View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("record", record);
        startActivity(intent);
    }

    @Override
    public void loadingFromCache(boolean success) {

    }

    @Override
    public void retrievingOnline(boolean triedInCache, Exception e) {

    }

    @Override
    public void storingToCache(Object object) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_work:
                _nextState = State.START_DAY;
                break;
            case R.id.start_break:
                _nextState = State.START_PAUSE;
                break;
            case R.id.finish_break:
                _nextState = State.END_PAUSE;
                break;
            case R.id.finish_work:
                _nextState = State.END_DAY;
                break;
        }
        Intent intent = new Intent(this, AddPositionActivity.class);
        intent.putExtra("nextState", _nextState);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            storeState(_nextState);
        }
    }

    public void storeState(State state) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().putInt(STATE, state.ordinal()).commit();
        _currentState = _nextState;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);
            startActivity(new Intent(this, WelcomeActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
