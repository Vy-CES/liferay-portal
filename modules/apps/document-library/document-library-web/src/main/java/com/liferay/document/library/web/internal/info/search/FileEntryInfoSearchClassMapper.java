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

package com.liferay.document.library.web.internal.info.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.search.InfoSearchClassMapper;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = InfoSearchClassMapper.class)
public class FileEntryInfoSearchClassMapper
	implements InfoSearchClassMapper<FileEntry> {

	@Override
	public String getSearchClassName() {
		return DLFileEntry.class.getName();
	}

}