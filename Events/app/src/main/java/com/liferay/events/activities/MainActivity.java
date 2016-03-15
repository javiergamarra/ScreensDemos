package com.liferay.events.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.liferay.events.R;
import com.liferay.events.mainfragments.AddTalkFragment;
import com.liferay.events.mainfragments.AgendaFragment;
import com.liferay.events.mainfragments.BlogsFragment;
import com.liferay.events.mainfragments.LoginFragment;
import com.liferay.events.mainfragments.SponsorsFragment;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.interactor.LoginBasicInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.push.PushScreensActivity;

import org.json.JSONObject;

public class MainActivity extends PushScreensActivity
	implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, LoginListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		doDefaultLogin();

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.setCheckedItem(R.id.agenda);
		navigationView.getMenu().performIdentifierAction(R.id.agenda, 0);

		navigationView.getHeaderView(0).findViewById(R.id.user_portrait).setOnClickListener(this);

		_content = findViewById(android.R.id.content);

	}

	@Override
	protected void onResume() {
		super.onResume();

		_drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
	}

	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {

		int menuId = item.getItemId();
		item.setChecked(true);

		Fragment fragment = fragmentForMenuEntry(menuId);
		getSupportFragmentManager().
			beginTransaction().
			replace(R.id.main_fragment, fragment).
			commit();

		_drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		_drawer.closeDrawer(GravityCompat.START);

		return true;
	}

	@Override
	public void onClick(View v) {
		getSupportFragmentManager().
			beginTransaction().
			replace(R.id.main_fragment, LoginFragment.newInstance()).
			commit();

		_drawer.closeDrawer(GravityCompat.START);
	}

	@Override
	public void onLoginSuccess(User user) {

	}

	@Override
	public void onLoginFailure(Exception e) {

	}

	@Override
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	protected void onPushNotificationReceived(JSONObject jsonObject) {
		View view = findViewById(android.R.id.content);
		Snackbar.make(view, jsonObject.toString(), Snackbar.LENGTH_LONG).show();
	}

	@Override
	protected void onErrorRegisteringPush(String message, Exception e) {

	}

	@Override
	protected String getSenderId() {
		return "733569701128";
	}

	private void doDefaultLogin() {
		try {
			String login = getString(R.string.default_user);
			String password = getString(R.string.default_password);

			SessionContext.createBasicSession(login, password);
			LoginBasicInteractor loginBasicInteractor = new LoginBasicInteractor(0);
			loginBasicInteractor.onScreenletAttached(this);
			loginBasicInteractor.setBasicAuthMethod(BasicAuthMethod.EMAIL);
			loginBasicInteractor.setLogin(login);
			loginBasicInteractor.setPassword(password);

			loginBasicInteractor.login();

		}
		catch (Exception e) {
			Snackbar.make(_content, "Couldn't login with default user", Snackbar.LENGTH_SHORT).show();
		}
	}

	private Fragment fragmentForMenuEntry(int menuId) {
		switch (menuId) {
			case R.id.sponsors:
				return SponsorsFragment.newInstance();
			case R.id.add_talk:
				return AddTalkFragment.newInstance(null);
			case R.id.blogs:
				return BlogsFragment.newInstance();
			default:
				return AgendaFragment.newInstance();
		}
	}

	private DrawerLayout _drawer;
	private View _content;
}
