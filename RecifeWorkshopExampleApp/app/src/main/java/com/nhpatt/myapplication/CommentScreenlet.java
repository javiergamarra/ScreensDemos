package com.nhpatt.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.base.BaseScreenlet;
import org.json.JSONObject;

public class CommentScreenlet extends BaseScreenlet<CommentViewModel, CommentLoadInteractor>
	implements CommentLoadListener {

	private long commentId;

	public CommentScreenlet(Context context) {
		super(context);
	}

	public CommentScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.CommentScreenlet, 0, 0);

		commentId = typedArray.getInt(R.styleable.CommentScreenlet_id, 0);

		int layoutId = typedArray.getResourceId(R.styleable.CommentScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		performUserAction();
	}

	@Override
	protected CommentLoadInteractor createInteractor(String actionName) {
		return new CommentLoadInteractor();
	}

	@Override
	protected void onUserAction(String userActionName, CommentLoadInteractor interactor, Object... args) {
		interactor.start(commentId);
	}

	@Override
	public void success(JSONObject jsonObject) {
		getViewModel().showFinishOperation("", jsonObject);
	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation("", e);
	}
}
