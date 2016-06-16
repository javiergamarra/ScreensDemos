package com.ezentis.ezentistrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;


public class LoginActivity extends AppCompatActivity implements LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
        loginScreenlet.setListener(this);

        EditText login = (EditText) findViewById(R.id.liferay_login);
        login.setText(R.string.liferay_anonymousApiUserName);

        EditText password = (EditText) findViewById(R.id.liferay_password);
        password.setText(R.string.liferay_anonymousApiPassword);
    }

    @Override
    public void onLoginSuccess(User user) {
        startActivity(new Intent(this, WorkActivity.class));
    }

    @Override
    public void onLoginFailure(Exception e) {

    }
}
