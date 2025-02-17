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

package com.liferay.portal.template.engine;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public abstract class BaseTemplateManager implements TemplateManager {

	@Override
	public String[] getRestrictedVariables() {
		return new String[0];
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource, boolean restricted) {

		return doGetTemplate(
			templateResource, restricted, getHelperUtilities(restricted));
	}

	protected abstract Template doGetTemplate(
		TemplateResource templateResource, boolean restricted,
		Map<String, Object> helperUtilities);

	protected Map<String, Object> getHelperUtilities(boolean restricted) {
		TemplateContextHelper templateContextHelper =
			getTemplateContextHelper();

		return templateContextHelper.getHelperUtilities(restricted);
	}

	protected abstract TemplateContextHelper getTemplateContextHelper();

}