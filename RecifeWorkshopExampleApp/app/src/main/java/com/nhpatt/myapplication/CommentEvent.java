package com.nhpatt.myapplication;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import org.json.JSONObject;

public class CommentEvent extends CacheEvent {

	public CommentEvent() {
		super();
	}

	public CommentEvent(JSONObject jsonObject) {
		super(jsonObject);
	}
}
