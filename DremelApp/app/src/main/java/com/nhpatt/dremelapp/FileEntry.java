package com.nhpatt.dremelapp;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.ddl.model.DDMStructure;
import com.liferay.mobile.screens.ddl.model.WithDDM;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FileEntry extends AssetEntry implements WithDDM {

    private DDMStructure ddmStructure;

    public FileEntry(Map<String, Object> values) {
        super(values);

        ddmStructure = new DDMStructure(Locale.US);
    }

    @Override
    public DDMStructure getDDMStructure() {
        return ddmStructure;
    }

    @Override
    public void parseDDMStructure(JSONObject jsonObject) throws JSONException {
        List array = (ArrayList) _values.get("DDMStructures");

        ddmStructure.parse(new JSONObject((Map) array.get(0)));
    }

}
