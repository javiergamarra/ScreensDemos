package com.nhpatt.dremelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.interactor.LoginBasicInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginListener {

	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		button = (Button) findViewById(R.id.find_it);
		button.setVisibility(View.INVISIBLE);
		button.setOnClickListener(this);

		try {
			LiferayScreensContext.init(this);
			LoginBasicInteractor loginBasicInteractor = new LoginBasicInteractor();
			loginBasicInteractor.onScreenletAttached(this);
			loginBasicInteractor.start("bruno.admin", "brunoadmin", BasicAuthMethod.SCREEN_NAME);
		}
		catch (Exception e) {
			LiferayLogger.e(e.getMessage(), e);
		}
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, FinderActivity.class));
	}

	@Override
	public void onLoginSuccess(User user) {
		button.setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoginFailure(Exception e) {
		LiferayLogger.e(e.getMessage(), e);
	}
}
