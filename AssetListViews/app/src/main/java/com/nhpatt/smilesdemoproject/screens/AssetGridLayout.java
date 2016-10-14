package com.nhpatt.smilesdemoproject.screens;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.viewsets.defaultviews.asset.list.AssetListAdapter;
import com.liferay.mobile.screens.viewsets.defaultviews.asset.list.AssetListView;
import com.nhpatt.smilesdemoproject.R;

/**
 * @author Javier Gamarra
 */
public class AssetGridLayout extends AssetListView {

	public AssetGridLayout(Context context) {
		super(context);
	}

	public AssetGridLayout(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetGridLayout(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.smiles;
	}

	@Override
	protected AssetListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new AssetCustomAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		int itemLayoutId = getItemLayoutId();
		int itemProgressLayoutId = getItemProgressLayoutId();

		recyclerView = (RecyclerView) findViewById(com.liferay.mobile.screens.R.id.liferay_recycler_list);
		progressBar = (ProgressBar) findViewById(com.liferay.mobile.screens.R.id.liferay_progress);

		AssetListAdapter adapter = createListAdapter(itemLayoutId, itemProgressLayoutId);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

		RecyclerView.ItemDecoration dividerItemDecoration = getDividerDecoration();
		if (dividerItemDecoration != null) {
			recyclerView.addItemDecoration(getDividerDecoration());
		}
	}
}

