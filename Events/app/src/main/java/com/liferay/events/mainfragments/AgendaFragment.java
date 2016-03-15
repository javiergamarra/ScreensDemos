package com.liferay.events.mainfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.events.R;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class AgendaFragment extends Fragment implements BaseListListener<Record> {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_agenda, container, false);
		DDLListScreenlet ddlListScreenlet = (DDLListScreenlet) view.findViewById(R.id.agenda_list);
		ddlListScreenlet.setListener(this);
		return view;
	}

	public static Fragment newInstance() {
		return new AgendaFragment();
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<Record> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Record element, View view) {
		getFragmentManager().
			beginTransaction().
			replace(R.id.main_fragment, AddTalkFragment.newInstance(element)).
			addToBackStack(null).
			commit();
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}
}
