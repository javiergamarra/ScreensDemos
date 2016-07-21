package com.nhpatt.dremelapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String fileEntryJson = getIntent().getStringExtra("fileEntry");
        FileEntry fileEntry = load(fileEntryJson);

        if (fileEntry != null) {
            paint(fileEntry);
        }
    }

    private void paint(FileEntry fileEntry) {

        Map<String, Object> values = fileEntry.getValues();
        HashMap file = (HashMap) values.get("fileEntry");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(String.valueOf(file.get("title")));


        String url = LiferayServerContext.getServer() + "documents/" + file.get("repositoryId") + "/" + file.get("folderId") + "/" + file.get("title") + "/" + file.get("uuid");
        Picasso.with(this).load(url).into((ImageView) findViewById(R.id.image));

        LinearLayout information = (LinearLayout) findViewById(R.id.panel);

        for (Field field : fileEntry.getDDMStructure().getFields()) {

            TextView fieldTitle = new TextView(this);
            fieldTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            fieldTitle.setText(field.getLabel());
            fieldTitle.setTextSize(30);
            fieldTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            fieldTitle.setPadding(0, 20, 0, 20);
            information.addView(fieldTitle);

            TextView fieldValue = new TextView(this);
            fieldValue.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            fieldValue.setText(String.valueOf(field.getCurrentValue()));
            information.addView(fieldValue);
        }
    }

    private FileEntry load(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            FileEntry fileEntry = new FileEntry(JSONUtil.toMap(jsonObject));
            fileEntry.parseDDMStructure(null);

            for (Field field : fileEntry.getDDMStructure().getFields()) {
                List list = (ArrayList) ((HashMap) fileEntry.getValues().get("DDMFormValues")).values().toArray()[0];
                for (Object object : list) {
                    HashMap map = (HashMap) object;
                    if (field.getName().equals(map.get("name"))) {
                        field.setCurrentValue((Serializable) map.get("value"));
                    }
                }
            }

            return fileEntry;
        } catch (JSONException e) {
            LiferayLogger.e("Error loading file entry " + e);
        }
        return null;
    }

}
