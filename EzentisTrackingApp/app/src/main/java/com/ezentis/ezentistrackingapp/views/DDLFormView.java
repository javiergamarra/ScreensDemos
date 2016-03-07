/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.ezentis.ezentistrackingapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.form.view.DDLFormViewModel;

/**
 * @author Silvio Santos
 */
public class DDLFormView
        extends com.liferay.mobile.screens.viewsets.material.ddl.form.DDLFormView implements DDLFormViewModel, View.OnClickListener {

    public DDLFormView(Context context) {
        super(context);
    }

    public DDLFormView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDLFormView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public void showRecordValues() {
        for (int i = 0; i < _fieldsContainerView.getChildCount(); i++) {
            DDLFieldViewModel viewModel = (DDLFieldViewModel) _fieldsContainerView.getChildAt(i);
            viewModel.refresh();

            if (viewModel.getField().isReadOnly()) {
                ((View) viewModel).setVisibility(View.GONE);
            }
        }
    }

}