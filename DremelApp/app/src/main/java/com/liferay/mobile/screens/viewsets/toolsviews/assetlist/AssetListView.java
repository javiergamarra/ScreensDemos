package com.liferay.mobile.screens.viewsets.toolsviews.assetlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.view.AssetListViewModel;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.nhpatt.dremelapp.R;

public class AssetListView extends BaseListScreenletView<AssetEntry,
	AssetListView.AssetViewHolder, AssetListView.ToolsAdapter>
	implements AssetListViewModel {

	public AssetListView(Context context) {
		super(context);
	}

	public AssetListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected ToolsAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new ToolsAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.row_layout;
	}

	class ToolsAdapter extends BaseListAdapter<AssetEntry, AssetViewHolder> {

		ToolsAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
			super(layoutId, progressLayoutId, listener);
		}

		@NonNull
		@Override
		public AssetViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
			return new AssetViewHolder(view, listener);
		}

		@Override
		protected void fillHolder(AssetEntry entry, AssetViewHolder holder) {
			String title = entry.getTitle() == null ? "" : entry.getTitle();

			for (String label : getLabelFields()) {
				if (entry.getValues().containsKey(label)) {
					title += entry.getValues().get(label);
				}
			}

			holder.textView.setText(title);
//			holder.imageView
		}

	}

	class AssetViewHolder extends BaseListAdapter.ViewHolder {

		private final ImageView imageView;

		public AssetViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			imageView = (ImageView) view.findViewById(R.id.image_view);
		}
	}
}