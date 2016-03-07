/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.ezentis.ezentistrackingapp.views;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.R;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
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
		String titleField = (String) entry.getServerValue(getLabelFields().get(0));
		String replace = titleField.replace("[\"", "");
		String replace1 = replace.replace("\"]", "");
		holder.textView.setText(replace1);

		StringBuilder builder = new StringBuilder();
		for (String field : getLabelFields().subList(1, getLabelFields().size())) {
			String value = (String) entry.getServerValue(field);
			if (value != null && !value.isEmpty()) {
				builder.append(value);
				builder.append(" ");
			}
		}

		holder.subtitleTextView.setText(builder.toString());
	}

	public static class TwoTextsViewHolder extends BaseListAdapter.ViewHolder {

		public TextView subtitleTextView;

		public TwoTextsViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			this.subtitleTextView = (TextView) view.findViewById(R.id.liferay_list_subtitle);
		}
	}

}