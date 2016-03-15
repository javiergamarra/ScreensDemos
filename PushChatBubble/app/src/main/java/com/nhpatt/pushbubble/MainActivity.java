package com.nhpatt.pushbubble;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.push.PushScreensActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends PushScreensActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected Session getDefaultSession() {
		String server = getString(R.string.server);
		String email = getString(R.string.user);
		String password = getString(R.string.password);
		return new SessionImpl(server, new BasicAuthentication(email, password));
	}

	@Override
	protected void onPushNotificationReceived(final JSONObject jsonObject) {
		updateLinearOnUiThread(jsonObject, R.id.linear);
	}

	@Override
	protected void onErrorRegisteringPush(final String s, final Exception e) {

	}

	@Override
	protected String getSenderId() {
		return getString(R.string.sender_id);
	}

	private void updateLinearOnUiThread(final JSONObject jsonObject, final int id) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				LinearLayout linear = (LinearLayout) findViewById(id);
				linear.addView(createTextView(jsonObject));
			}
		});
	}

	private TextView createTextView(final JSONObject rootObject) {
		try {
			String body = rootObject.getString("body");
			JSONObject jsonObject = new JSONObject(body);
			boolean brian = "brian".equals(jsonObject.getString("name"));
			int leftMargin = brian ? 100 : 0;
			int rightMargin = brian ? 0 : 100;
			int color = brian ? android.R.color.holo_red_dark : android.R.color.holo_green_dark;
			String message = jsonObject.getString("message");

			return createTextView(message, leftMargin, rightMargin, getResources().getColor(color));
		}
		catch (JSONException e) {
			Log.e("TAG", "Error parsing message");
		}
		return null;
	}

	@NonNull
	private TextView createTextView(final String message, final int leftMargin, final int rightMargin, final int color) {
		TextView textView = new TextView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(leftMargin, 10, rightMargin, 10);
		textView.setLayoutParams(params);
		textView.setText(message);
		textView.setBackgroundColor(color);
		textView.setPadding(10, 10, 10, 10);
		return textView;
	}

}
