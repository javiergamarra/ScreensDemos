package com.nhpatt.pushbubble;

import android.support.annotation.NonNull;

import com.liferay.mobile.screens.push.AbstractPushReceiver;

public class PushReceiver extends AbstractPushReceiver {

    @NonNull
    @Override
    protected Class getPushServiceClass() {
        return PushService.class;
    }
}
