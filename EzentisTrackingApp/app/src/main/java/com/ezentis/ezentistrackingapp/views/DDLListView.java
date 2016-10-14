/*
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.ezentis.ezentistrackingapp.views;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListView
	extends BaseListScreenletView<Record, DDLListAdapter.TwoTextsViewHolder, DDLListAdapter>
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
	public void showFinishOperation(int startRow, int endRow, Exception e) {


		super.showFinishOperation(startRow, endRow, e);
	}

	@Override
	public void showFinishOperation(int startRow, int endRow, List<Record> serverEntries, int totalRowCount) {
		sortRecords(serverEntries);

		super.showFinishOperation(startRow, endRow, serverEntries, totalRowCount);
	}

	@Override
	protected DDLListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new DDLListAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.ddl_list_item_material;
	}

	@Override
	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_material;
	}

	private void sortRecords(List<Record> entries) {
		Collections.sort(entries, new Comparator<Record>() {
			@Override
			public int compare(Record lhs, Record rhs) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date dateLhs = simpleDateFormat.parse((String) lhs.getModelValues().get("Date"));
					Date dateRhs = simpleDateFormat.parse((String) rhs.getModelValues().get("Date"));

					String hourLhs = (String) lhs.getModelValues().get("Time");
					String hourRhs = (String) rhs.getModelValues().get("Time");

					if (dateLhs.before(dateRhs)) {
						return 1;
					}
					else if (dateRhs.before(dateLhs)) {
						return -1;
					}
					else {
						return hourRhs.compareTo(hourLhs);
					}

				}
				catch (ParseException e) {
					e.printStackTrace();
				}
				return 1;
			}
		});
	}

}