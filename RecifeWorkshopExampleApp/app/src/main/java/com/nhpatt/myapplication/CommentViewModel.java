package com.nhpatt.myapplication;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import org.json.JSONObject;

public interface CommentViewModel extends BaseViewModel {

	void showFinishOperation(String actionName, JSONObject jsonObject);
}
