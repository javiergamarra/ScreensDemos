package com.nhpatt.dremelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liferay.mobile.android.v62.dlapp.DLAppService;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.assetlist.AssetListScreenlet;
import com.liferay.mobile.screens.assetlist.interactor.AssetFactory;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class FinderActivity extends AppCompatActivity implements BaseListListener<AssetEntry> {

    private AssetListScreenlet assetListScreenlet;
    private Stack<AssetEntry> stack = new Stack<>();
    private AssetEntry oldAssetEntry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assetListScreenlet = (AssetListScreenlet) findViewById(R.id.tasks);
        assetListScreenlet.setListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!stack.isEmpty()) {
            oldAssetEntry = stack.pop();
            searchForAssets(oldAssetEntry);
        } else {
            final AssetEntry assetEntry = getIntent().getParcelableExtra("ASSET");
            searchForAssets(assetEntry);
        }
    }

    protected void searchForAssets(final AssetEntry assetEntry) {
        if (assetEntry == null) {
            assetListScreenlet.setPortletItemName("tools");
            assetListScreenlet.loadPage(0);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doInBackground(assetEntry);
                }
            }).start();
        }
    }

    private void doInBackground(AssetEntry assetEntry) {
        try {
            Map<String, Object> values = assetEntry.getValues();
            int groupId = (int) values.get("groupId");
            int classPK = (int) (values.containsKey("classPK") ? values.get("classPK") : values.get("folderId"));

            DLAppService dlAppService = new DLAppService(SessionContext.createSessionFromCurrentSession());
            JSONArray folders = dlAppService.getFolders(groupId, classPK);

            final List<AssetEntry> assets = new ArrayList<>();
            for (int i = 0; i < folders.length(); i++) {
                JSONObject jsonObject = folders.getJSONObject(i);
                assets.add(AssetFactory.createInstance(JSONUtil.toMap(jsonObject)));
            }

            if (assets.isEmpty()) {
                JSONArray jsonArray = dlAppService.getFileEntries(groupId, classPK);

                if (jsonArray.length() > 0) {

                    FileEntryService fileEntryService = new FileEntryService(SessionContext.createSessionFromCurrentSession());
                    JSONObject jsonObject = fileEntryService.getFileEntry(jsonArray.getJSONObject(0).getLong("fileEntryId"));

                    Intent intent = new Intent(this, AccessoriesActivity.class);
                    intent.putExtra("fileEntry", jsonObject.toString());
                    startActivity(intent);
                }
            }

            doOnUiThread(assets);
        } catch (Exception e) {
            LiferayLogger.e(e.getMessage(), e);
        }
    }

    private void doOnUiThread(final List<AssetEntry> assets) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assetListScreenlet.onListRowsReceived(0, assets.size(), assets, assets.size());
            }
        });
    }

    @Override
    public void onListPageFailed(BaseListScreenlet source, int startRow, int endRow, Exception e) {

    }

    @Override
    public void onListPageReceived(BaseListScreenlet source, int startRow, int endRow, List<AssetEntry> entries, int rowCount) {

    }

    @Override
    public void onListItemSelected(AssetEntry assetEntry, View view) {
        stack.push(oldAssetEntry);
        this.oldAssetEntry = assetEntry;
        searchForAssets(assetEntry);
    }

    @Override
    public void onBackPressed() {
        if (stack.isEmpty()) {
            super.onBackPressed();
        } else {
            oldAssetEntry = stack.pop();
            searchForAssets(oldAssetEntry);
        }
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
