package com.nhpatt.smilesdemoproject.screens;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liferay.mobile.android.http.UnsafeOkHttpClient;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.viewsets.defaultviews.assetlist.AssetListAdapter;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.nhpatt.smilesdemoproject.R;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * @author Javier Gamarra
 */
public class AssetCustomAdapter extends AssetListAdapter {

    private Picasso picasso = new Picasso.Builder(LiferayScreensContext.getContext()).downloader(new OkHttpDownloader(UnsafeOkHttpClient.getUnsafeClient())).build();

    public AssetCustomAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new SmilesHolder(view, listener);
    }

    @Override
    protected void fillHolder(AssetEntry entry, ViewHolder holder) {
        WebContent webContent = (WebContent) entry;
        SmilesHolder smilesHolder = (SmilesHolder) holder;

        Context context = LiferayScreensContext.getContext();
        final String localized = webContent.getLocalized(context.getString(R.string.imagen));

        picasso.load(LiferayServerContext.getServer() + localized)
                .into(smilesHolder._image);

        smilesHolder._title.setText(webContent.getLocalized(context.getString(R.string.titulo)));
        smilesHolder._text.setText(webContent.getLocalized(context.getString(R.string.subtitulo)));
    }

    private class SmilesHolder extends ViewHolder {
        protected final ImageView _image;
        protected final TextView _title;
        protected final TextView _text;
        protected final Button _button;

        public SmilesHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);

            _image = (ImageView) view.findViewById(R.id.smiles_image_view);
            _title = (TextView) view.findViewById(R.id.smiles_title);
            _text = (TextView) view.findViewById(R.id.smiles_text);
            _button = (Button) view.findViewById(R.id.smiles_button);
            _button.setOnClickListener(this);
        }
    }
}
