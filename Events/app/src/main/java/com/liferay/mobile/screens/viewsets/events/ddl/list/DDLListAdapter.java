package com.liferay.mobile.screens.viewsets.events.ddl.list;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.liferay.events.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.model.Record;

public class DDLListAdapter
	extends BaseListAdapter<Record, DDLListAdapter.TwoTextsViewHolder> {

	public DDLListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull
	@Override
	public TwoTextsViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new TwoTextsViewHolder(view, getListener());
	}

	@Override
	protected void fillHolder(Record entry, TwoTextsViewHolder holder) {
		String titleField = (String) entry.getServerValue("Title");
		holder.textView.setText(titleField);
		holder.subtitleTextView.setText((String) entry.getServerValue("Subtitle"));
		holder.date.setText((String) entry.getServerValue("Hour"));

		String room = (String) entry.getServerValue("Room");
		int color = "[\"value 1\"]".equals(room) ? R.color.colorPrimaryDark : R.color.colorAccent;

		holder.room.setBackgroundResource(color);
	}

	public static class TwoTextsViewHolder extends BaseListAdapter.ViewHolder {

		public final TextView subtitleTextView;
		public final TextView date;
		public final View room;

		public TwoTextsViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			this.subtitleTextView = (TextView) view.findViewById(R.id.liferay_list_subtitle);
			this.date = (TextView) view.findViewById(R.id.liferay_date);
			this.room = view.findViewById(R.id.room);
		}
	}

}