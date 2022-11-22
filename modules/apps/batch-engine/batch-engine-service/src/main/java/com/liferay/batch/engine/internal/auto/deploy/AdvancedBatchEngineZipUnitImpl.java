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

package com.liferay.batch.engine.internal.auto.deploy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Beslic
 */
public class AdvancedBatchEngineZipUnitImpl<T>
	implements BatchEngineZipUnit<T> {

	public AdvancedBatchEngineZipUnitImpl(ZipFile zipFile, ZipEntry zipEntry) {
		_zipFile = zipFile;
		_zipEntry = zipEntry;
	}

	@Override
	public T getBatchEngineConfiguration(Class<T> clazz) throws IOException {
		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			AdvancedJsonReader<T> advancedJsonReader = new AdvancedJsonReader<>(
				inputStream);

			return advancedJsonReader.getObject("configuration", clazz);
		}
	}

	@Override
	public InputStream getConfigurationInputStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDataFileName() {
		return _zipEntry.getName();
	}

	@Override
	public InputStream getDataInputStream() throws IOException {
		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			AdvancedJsonReader advancedJsonReader = new AdvancedJsonReader(
				inputStream);

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			advancedJsonReader.transferJsonArray(
				"items", byteArrayOutputStream);

			return new ByteArrayInputStream(
				byteArrayOutputStream.toByteArray());
		}
	}

	@Override
	public String getZipFileName() {
		return _zipFile.getName();
	}

	@Override
	public boolean isValid() {
		if (_zipEntry == null) {
			return false;
		}

		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			AdvancedJsonReader advancedJsonReader = new AdvancedJsonReader(
				inputStream);

			return advancedJsonReader.hasKey("items");
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to find data in file " + _zipEntry.getName(),
				ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedBatchEngineZipUnitImpl.class);

	private ZipEntry _zipEntry;
	private final ZipFile _zipFile;

}