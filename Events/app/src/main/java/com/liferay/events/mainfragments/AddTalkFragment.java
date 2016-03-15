package com.liferay.events.mainfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.events.R;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class AddTalkFragment extends Fragment implements DDLFormListener {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.content_add_talk, container, false);

		Record record = (Record) getArguments().get("record");
		DDLFormScreenlet ddlFormScreenlet = (DDLFormScreenlet) inflate.findViewById(R.id.ddl_form);
		if (record != null) {
			ddlFormScreenlet.setRecord(record);
			record.setStructureId(Long.valueOf(getString(R.string.structure_id)));
			record.setLocale(Locale.ENGLISH);
		}
		ddlFormScreenlet.setListener(this);

		return inflate;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_view = getActivity().findViewById(android.R.id.content);
	}

	public static Fragment newInstance(Record record) {
		AddTalkFragment addTalkFragment = new AddTalkFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("record", record);
		addTalkFragment.setArguments(bundle);
		return addTalkFragment;
	}

	@Override
	public void onDDLFormLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {

		Snackbar.make(_view, "Record added!", Snackbar.LENGTH_LONG).show();
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {

	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {

	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {

	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

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

	private View _view;
}
