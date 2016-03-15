package com.liferay.events.mainfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.events.R;

/**
 * @author Javier Gamarra
 */
public class BlogsFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.content_blogs, container, false);
		return inflate;
	}

	public static Fragment newInstance() {
		return new BlogsFragment();
	}
}
