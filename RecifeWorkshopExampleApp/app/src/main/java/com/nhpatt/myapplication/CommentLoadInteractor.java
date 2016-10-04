package com.nhpatt.myapplication;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.service.v70.ScreenscommentService;
import org.json.JSONObject;

public class CommentLoadInteractor extends BaseCacheReadInteractor<CommentLoadListener, CommentEvent> {

	@Override
	public CommentEvent execute(Object... args) throws Exception {
		long commentId = (long) args[0];

		ScreenscommentService commentService = new ScreenscommentService(getSession());
		JSONObject jsonObject = commentService.getComment(commentId);
		return new CommentEvent(jsonObject);
	}

	@Override
	public void onSuccess(CommentEvent event) throws Exception {
		getListener().success(event.getJSONObject());
	}

	@Override
	public void onFailure(CommentEvent event) {
		getListener().error(event.getException(), "");
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		return String.valueOf(args[0]);
	}
}
