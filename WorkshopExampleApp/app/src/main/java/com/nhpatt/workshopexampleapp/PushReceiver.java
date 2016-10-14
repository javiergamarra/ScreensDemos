package com.nhpatt.workshopexampleapp;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.push.AbstractPushReceiver;

/**
 * @author Javier Gamarra
 */
public class PushReceiver extends AbstractPushReceiver<PushService> {
	@NonNull
	@Override
	protected Class<PushService> getPushServiceClass() {
		return PushService.class;
	}
}
