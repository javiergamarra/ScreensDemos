package com.nhpatt.pushbubble;

import com.liferay.mobile.push.bus.BusUtil;
import com.liferay.mobile.screens.push.AbstractPushService;

import org.json.JSONException;
import org.json.JSONObject;

public class PushService extends AbstractPushService {

    @Override
    protected void processJSONNotification(JSONObject json) throws JSONException {
        BusUtil.post(json);
    }
}
