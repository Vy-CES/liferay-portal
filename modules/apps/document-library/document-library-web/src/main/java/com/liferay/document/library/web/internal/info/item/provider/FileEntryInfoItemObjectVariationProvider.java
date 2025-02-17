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

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemObjectVariationProvider.class)
public class FileEntryInfoItemObjectVariationProvider
	implements InfoItemObjectVariationProvider<FileEntry> {

	@Override
	public String getInfoItemFormVariationKey(FileEntry fileEntry) {
		if ((fileEntry == null) ||
			!(fileEntry.getModel() instanceof DLFileEntry)) {

			return null;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		return String.valueOf(dlFileEntry.getFileEntryTypeId());
	}

}