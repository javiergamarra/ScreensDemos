package com.nhpatt.myapplication;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import org.json.JSONObject;

public interface CommentLoadListener extends BaseCacheListener {

	void success(JSONObject jsonObject);
}