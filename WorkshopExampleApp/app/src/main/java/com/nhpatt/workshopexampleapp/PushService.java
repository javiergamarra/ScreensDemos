package com.nhpatt.workshopexampleapp;

import com.liferay.mobile.screens.push.AbstractPushService;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class PushService extends AbstractPushService {
	@Override
	protected void processJSONNotification(JSONObject json) throws JSONException {
		LiferayLogger.e(json.toString());
	}
}
