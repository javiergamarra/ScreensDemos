package com.nhpatt.myapplication;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.base.BaseScreenlet;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentView extends LinearLayout implements CommentViewModel {
	private BaseScreenlet screenlet;
	private TextView commentText;

	public CommentView(Context context) {
		super(context);
	}

	public CommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public CommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void showStartOperation(String actionName) {

	}

	@Override
	public void showFinishOperation(String actionName) {
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		commentText = (TextView) findViewById(R.id.comment_text);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	public void showFinishOperation(String actionName, JSONObject jsonObject) {
		try {
			commentText.setText(Html.fromHtml(jsonObject.getString("body")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
