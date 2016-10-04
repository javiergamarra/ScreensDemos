package com.nhpatt.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

public class MainActivity extends AppCompatActivity implements LoginListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LoginScreenlet login = (LoginScreenlet) findViewById(R.id.login);
		login.setListener(this);

		EditText liferayLogin = (EditText) findViewById(R.id.liferay_login);
		liferayLogin.setText("test@liferay.com");

		EditText liferayPassword = (EditText) findViewById(R.id.liferay_password);
		liferayPassword.setText("test");
	}

	@Override
	public void onLoginSuccess(User user) {
		View view = findViewById(android.R.id.content);

		Snackbar.make(view, "Login!", Snackbar.LENGTH_LONG).show();

		startActivity(new Intent(this, JournalActivity.class));
	}

	@Override
	public void onLoginFailure(Exception e) {

	}
}
