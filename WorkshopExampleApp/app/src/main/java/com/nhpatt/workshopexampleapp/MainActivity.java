package com.nhpatt.workshopexampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

public class MainActivity extends AppCompatActivity implements LoginListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login);
		loginScreenlet.setListener(this);

		EditText login = (EditText) findViewById(R.id.liferay_login);
		login.setText(R.string.default_user);

		EditText password = (EditText) findViewById(R.id.liferay_password);
		password.setText(R.string.default_password);
	}

	@Override
	public void onLoginSuccess(User user) {
		startActivity(new Intent(this, DDLActivity.class));
	}

	@Override
	public void onLoginFailure(Exception e) {

	}
}
