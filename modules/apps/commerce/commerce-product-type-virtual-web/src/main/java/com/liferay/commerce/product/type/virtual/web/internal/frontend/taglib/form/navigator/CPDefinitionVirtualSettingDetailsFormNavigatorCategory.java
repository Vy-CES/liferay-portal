/**
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

package com.liferay.commerce.product.type.virtual.web.internal.frontend.taglib.form.navigator;

import com.liferay.commerce.product.type.virtual.web.internal.servlet.taglib.ui.constants.CPDefinitionVirtualSettingFormNavigatorConstants;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "form.navigator.category.order:Integer=100",
	service = FormNavigatorCategory.class
)
public class CPDefinitionVirtualSettingDetailsFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return CPDefinitionVirtualSettingFormNavigatorConstants.
			FORM_NAVIGATOR_ID_CP_DEFINITION_VIRTUAL_SETTING;
	}

	@Override
	public String getKey() {
		return CPDefinitionVirtualSettingFormNavigatorConstants.
			CATEGORY_KEY_CP_DEFINITION_VIRTUAL_SETTING_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "details");
	}

	@Reference
	private Language _language;

}