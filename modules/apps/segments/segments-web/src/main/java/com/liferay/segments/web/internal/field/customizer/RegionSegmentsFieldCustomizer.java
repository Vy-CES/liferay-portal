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

package com.liferay.segments.web.internal.field.customizer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	property = {
		"segments.field.customizer.entity.name=Organization",
		"segments.field.customizer.key=" + RegionSegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class RegionSegmentsFieldCustomizer extends BaseSegmentsFieldCustomizer {

	public static final String KEY = "region";

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public List<Field.Option> getOptions(Locale locale) {
		List<Field.Option> options = TransformUtil.transform(
			_regionService.getRegions(),
			region -> new Field.Option(
				_getRegionLabel(region, locale),
				StringUtil.toLowerCase(region.getName())));

		options.sort(
			(a, b) -> {
				String aLabel = a.getLabel();

				return aLabel.compareTo(b.getLabel());
			});

		return options;
	}

	private String _getRegionLabel(Region region, Locale locale) {
		try {
			Country country = _countryService.getCountry(region.getCountryId());

			return StringBundler.concat(
				country.getName(locale), " - ", region.getName());
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private static final List<String> _fieldNames = ListUtil.fromArray(
		"region");

	@Reference
	private CountryService _countryService;

	@Reference
	private RegionService _regionService;

}