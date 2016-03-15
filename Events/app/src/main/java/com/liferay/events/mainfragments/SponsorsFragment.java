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
public class SponsorsFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.content_sponsors, container, false);
	}

	public static Fragment newInstance() {
		SponsorsFragment sponsorsFragment = new SponsorsFragment();
		Bundle args = new Bundle();
		sponsorsFragment.setArguments(args);
		return sponsorsFragment;
	}
}
