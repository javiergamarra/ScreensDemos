package com.nhpatt.smilesdemoproject.screens;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import com.liferay.mobile.screens.viewsets.defaultviews.asset.list.AssetListAdapter;
import com.liferay.mobile.screens.viewsets.defaultviews.asset.list.AssetListView;
import com.nhpatt.smilesdemoproject.R;

/**
 * @author Javier Gamarra
 */
public class AssetSlideshowLayout extends AssetListView {

	public AssetSlideshowLayout(Context context) {
		super(context);
	}

	public AssetSlideshowLayout(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetSlideshowLayout(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		recyclerView.setLayoutManager(layout);
	}

	@Override
	protected AssetListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new AssetCustomAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.smiles;
	}
}
