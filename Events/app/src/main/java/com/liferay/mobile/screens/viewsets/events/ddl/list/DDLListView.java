package com.liferay.mobile.screens.viewsets.events.ddl.list;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.events.R;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.ddl.model.Record;

/**
 * @author Javier Gamarra
 */
public class DDLListView extends BaseListScreenletView<Record, DDLListAdapter.TwoTextsViewHolder, DDLListAdapter>
	implements DDLListViewModel {
	public DDLListView(Context context) {
		super(context);
	}

	public DDLListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected DDLListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new DDLListAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.ddl_list_item_events;
	}

	@Override
	protected int getItemProgressLayoutId() {
		return com.liferay.mobile.screens.viewsets.R.layout.list_item_progress_material;
	}
}
