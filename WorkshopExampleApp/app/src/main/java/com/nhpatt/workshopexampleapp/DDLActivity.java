package com.nhpatt.workshopexampleapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.journalarticle.JournalArticleService;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.push.PushScreensActivity;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;


public class DDLActivity extends PushScreensActivity implements DDLFormListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ddl);

		DDLFormScreenlet ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.form);
		ddlFormScreenlet.setListener(this);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Session session = SessionContext.createSessionFromCurrentSession();
				JournalArticleService journalArticleService = new JournalArticleService(session);
				try {
					JSONObject jsonObject = journalArticleService.getArticle(30965);
					LiferayLogger.e(jsonObject.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	protected void onPushNotificationReceived(JSONObject jsonObject) {
		LiferayLogger.e(jsonObject.toString());
	}

	@Override
	protected void onErrorRegisteringPush(String message, Exception e) {
		LiferayLogger.e(message, e);
	}

	@Override
	protected String getSenderId() {
		return "868241662267";
	}

	@Override
	public void onDDLFormLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		View view = findViewById(android.R.id.content);
		Snackbar.make(view, "Inserted!", Snackbar.LENGTH_LONG).show();
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {

	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {

	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {

	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

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
}
