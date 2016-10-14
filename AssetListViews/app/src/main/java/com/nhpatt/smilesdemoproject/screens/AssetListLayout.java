package com.nhpatt.smilesdemoproject.screens;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.viewsets.defaultviews.asset.list.AssetListAdapter;
import com.liferay.mobile.screens.viewsets.defaultviews.asset.list.AssetListView;
import com.nhpatt.smilesdemoproject.R;

/**
 * @author Javier Gamarra
 */
public class AssetListLayout extends AssetListView {

	public AssetListLayout(Context context) {
		super(context);
	}

	public AssetListLayout(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetListLayout(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
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
