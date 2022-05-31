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

package com.liferay.client.extension.type.factory;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.CETThemeJS;

/**
 * @author Brian Wing Shun Chan
 */
public interface CETFactory {

	public CETCustomElement customElement(
		ClientExtensionEntry clientExtensionEntry);

	public CETIFrame iFrame(ClientExtensionEntry clientExtensionEntry);

	public CETThemeCSS themeCSS(ClientExtensionEntry clientExtensionEntry);

	public CETThemeFavicon themeFavicon(
		ClientExtensionEntry clientExtensionEntry);

	public CETThemeJS themeJS(ClientExtensionEntry clientExtensionEntry);

}