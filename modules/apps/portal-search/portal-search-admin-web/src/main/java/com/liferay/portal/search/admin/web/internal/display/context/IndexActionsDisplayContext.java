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

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;

import java.util.Map;

/**
 * @author Olivia Yu
 */
public class IndexActionsDisplayContext {

	public Map<String, Object> getData() {
		return _data;
	}

	public String getTextEmbeddingServiceStatus() {
		return _textEmbeddingServiceStatus;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setTextEmbeddingServiceStatus(
		String textEmbeddingServiceStatus) {

		_textEmbeddingServiceStatus = textEmbeddingServiceStatus;
	}

	private Map<String, Object> _data;
	private String _textEmbeddingServiceStatus = StringPool.BLANK;

}