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

package com.liferay.adaptive.media.image.internal.configuration.test;

import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class AMImageUpdateConfigurationTest
	extends BaseAMImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpdateConfigurationEntryWithAlphanumericCharactersUuid()
		throws Exception {

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		String uuid = "one-2-three_four";

		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationHelper.updateAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", uuid,
				properties);

		Assert.assertEquals(uuid, amImageConfigurationEntry.getUUID());
	}

	@Test
	public void testUpdateConfigurationEntryWithBlankDescription()
		throws Exception {

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", StringPool.BLANK, "1",
			amImageConfigurationEntry1.getProperties());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			amImageConfigurationEntryOptional.get();

		Assert.assertEquals(
			StringPool.BLANK, amImageConfigurationEntry2.getDescription());
	}

	@Test
	public void testUpdateConfigurationEntryWithBlankMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", ""
			).put(
				"max-width", "200"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("200", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxHeightOnly()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", ""
			).build());
	}

	@Test
	public void testUpdateConfigurationEntryWithBlankMaxWidth()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "200"
			).put(
				"max-width", ""
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("200", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxWidthAndBlankMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", ""
			).put(
				"max-width", ""
			).build());
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxWidthAndZeroMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", ""
			).put(
				"max-width", "0"
			).build());
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxWidthOnly()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-width", ""
			).build());
	}

	@Test(expected = AMImageConfigurationException.InvalidNameException.class)
	public void testUpdateConfigurationEntryWithBlankName() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", StringPool.BLANK, "desc", "1",
			amImageConfigurationEntry1.getProperties());
	}

	@Test(expected = AMImageConfigurationException.InvalidUuidException.class)
	public void testUpdateConfigurationEntryWithBlankUuid() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "two", "desc",
			StringPool.BLANK, amImageConfigurationEntry1.getProperties());
	}

	@Test
	public void testUpdateConfigurationEntryWithColonSemicolonDescription()
		throws Exception {

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc:;desc", "1",
			properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertEquals(
			"desc:;desc", amImageConfigurationEntry.getDescription());
	}

	@Test
	public void testUpdateConfigurationEntryWithColonSemicolonName()
		throws Exception {

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one:;one", "desc", "1",
			properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertEquals("one:;one", amImageConfigurationEntry.getName());
	}

	@Test
	public void testUpdateConfigurationEntryWithMaxHeightOnly()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "200"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("200", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test
	public void testUpdateConfigurationEntryWithMaxWidthOnly()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-width", "200"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("200", actualProperties.get("max-width"));
	}

	@Test(expected = AMImageConfigurationException.InvalidHeightException.class)
	public void testUpdateConfigurationEntryWithNegativeNumberMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "-10"
			).build());
	}

	@Test(expected = AMImageConfigurationException.InvalidWidthException.class)
	public void testUpdateConfigurationEntryWithNegativeNumberMaxWidth()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-width", "-10"
			).build());
	}

	@Test(expected = AMImageConfigurationException.InvalidUuidException.class)
	public void testUpdateConfigurationEntryWithNonalphanumericCharactersUuid()
		throws Exception {

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "a3%&!2",
			properties);
	}

	@Test(expected = AMImageConfigurationException.InvalidHeightException.class)
	public void testUpdateConfigurationEntryWithNotNumberMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "Invalid"
			).build());
	}

	@Test(expected = AMImageConfigurationException.InvalidWidthException.class)
	public void testUpdateConfigurationEntryWithNotNumberMaxWidth()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-width", "Invalid"
			).build());
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithoutMaxHeightNorMaxWidth()
		throws Exception {

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			properties);
	}

	@Test
	public void testUpdateConfigurationEntryWithZeroMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "0"
			).put(
				"max-width", "200"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("200", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxHeightOnly()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "0"
			).build());
	}

	@Test
	public void testUpdateConfigurationEntryWithZeroMaxWidth()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "200"
			).put(
				"max-width", "0"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("200", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxWidthAndBlankMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "0"
			).put(
				"max-width", ""
			).build());
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxWidthAndZeroMaxHeight()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "0"
			).put(
				"max-width", "0"
			).build());
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxWidthOnly()
		throws Exception {

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
			HashMapBuilder.put(
				"max-width", "0"
			).build());
	}

	@Test
	public void testUpdateDisabledConfigurationEntry() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry.getUUID());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one-bis", "desc-bis", "1-bis",
			amImageConfigurationEntry.getProperties());

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1-bis");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		assertDisabled(amImageConfigurationEntryOptional);

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertEquals(
			"one-bis", actualAMImageConfigurationEntry.getName());
		Assert.assertEquals(
			"desc-bis", actualAMImageConfigurationEntry.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualAMImageConfigurationEntry.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.DuplicateAMImageConfigurationNameException.class
	)
	public void testUpdateDuplicateConfigurationEntryName() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2",
			HashMapBuilder.put(
				"max-height", "200"
			).put(
				"max-width", "200"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "two", "twodesc", "1",
			amImageConfigurationEntry1.getProperties());
	}

	@Test(
		expected = AMImageConfigurationException.DuplicateAMImageConfigurationUuidException.class
	)
	public void testUpdateDuplicateConfigurationEntryUuid() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2",
			HashMapBuilder.put(
				"max-height", "200"
			).put(
				"max-width", "200"
			).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "two-bis", "twodesc", "2",
			amImageConfigurationEntry1.getProperties());
	}

	@Test
	public void testUpdateFirstConfigurationEntryName() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				HashMapBuilder.put(
					"max-height", "200"
				).put(
					"max-width", "200"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one-bis", "onedesc-bis", "1",
			amImageConfigurationEntry1.getProperties());

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry1Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualAMImageConfigurationEntry1Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry1Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry1 =
			actualAMImageConfigurationEntry1Optional.get();

		Assert.assertEquals(
			"one-bis", actualAMImageConfigurationEntry1.getName());
		Assert.assertEquals(
			"onedesc-bis", actualAMImageConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualAMImageConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry2Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualAMImageConfigurationEntry2Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry2Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry2 =
			actualAMImageConfigurationEntry2Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry2.getName(),
			actualAMImageConfigurationEntry2.getName());
		Assert.assertEquals(
			amImageConfigurationEntry2.getDescription(),
			actualAMImageConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualAMImageConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryProperties() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				HashMapBuilder.put(
					"max-height", "200"
				).put(
					"max-width", "200"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry1.getUUID(),
			amImageConfigurationEntry1.getName(),
			amImageConfigurationEntry1.getDescription(),
			amImageConfigurationEntry1.getUUID(),
			HashMapBuilder.put(
				"max-height", "500"
			).put(
				"max-width", "800"
			).build());

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry1Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualAMImageConfigurationEntry1Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry1Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry1 =
			actualAMImageConfigurationEntry1Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry1.getName(),
			actualAMImageConfigurationEntry1.getName());
		Assert.assertEquals(
			amImageConfigurationEntry1.getDescription(),
			actualAMImageConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualAMImageConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry2Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualAMImageConfigurationEntry2Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry2Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry2 =
			actualAMImageConfigurationEntry2Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry2.getName(),
			actualAMImageConfigurationEntry2.getName());
		Assert.assertEquals(
			amImageConfigurationEntry2.getDescription(),
			actualAMImageConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualAMImageConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryUuid() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				HashMapBuilder.put(
					"max-height", "200"
				).put(
					"max-width", "200"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1",
			amImageConfigurationEntry1.getName(),
			amImageConfigurationEntry1.getDescription(), "1-bis",
			amImageConfigurationEntry1.getProperties());

		Optional<AMImageConfigurationEntry>
			nonexistantAMImageConfigurationEntry1Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(
			nonexistantAMImageConfigurationEntry1Optional.isPresent());

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry1Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1-bis");

		assertEnabled(actualAMImageConfigurationEntry1Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry1Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry1 =
			actualAMImageConfigurationEntry1Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry1.getName(),
			actualAMImageConfigurationEntry1.getName());
		Assert.assertEquals(
			amImageConfigurationEntry1.getDescription(),
			actualAMImageConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualAMImageConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry2Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualAMImageConfigurationEntry2Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry2Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry2 =
			actualAMImageConfigurationEntry2Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry2.getName(),
			actualAMImageConfigurationEntry2.getName());
		Assert.assertEquals(
			amImageConfigurationEntry2.getDescription(),
			actualAMImageConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualAMImageConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.NoSuchAMImageConfigurationException.class
	)
	public void testUpdateNonexistingConfiguration() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", "two", "twodesc", "2",
			amImageConfigurationEntry1.getProperties());
	}

	@Test
	public void testUpdateSecondConfigurationEntryName() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				HashMapBuilder.put(
					"max-height", "200"
				).put(
					"max-width", "200"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", "two-bis", "twodesc-bis", "2",
			amImageConfigurationEntry2.getProperties());

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry2Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualAMImageConfigurationEntry2Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry2Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry2 =
			actualAMImageConfigurationEntry2Optional.get();

		Assert.assertEquals(
			"two-bis", actualAMImageConfigurationEntry2.getName());
		Assert.assertEquals(
			"twodesc-bis", actualAMImageConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualAMImageConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry1Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualAMImageConfigurationEntry1Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry1Optional.isPresent());

		AMImageConfigurationEntry actualConfigurationEntry1 =
			actualAMImageConfigurationEntry1Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry1.getName(),
			actualConfigurationEntry1.getName());
		Assert.assertEquals(
			amImageConfigurationEntry1.getDescription(),
			actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryProperties()
		throws Exception {

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				HashMapBuilder.put(
					"max-height", "200"
				).put(
					"max-width", "200"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry2.getUUID(),
			amImageConfigurationEntry2.getName(),
			amImageConfigurationEntry2.getDescription(),
			amImageConfigurationEntry2.getUUID(),
			HashMapBuilder.put(
				"max-height", "500"
			).put(
				"max-width", "800"
			).build());

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry2Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualAMImageConfigurationEntry2Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry2Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry2 =
			actualAMImageConfigurationEntry2Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry2.getName(),
			actualAMImageConfigurationEntry2.getName());
		Assert.assertEquals(
			amImageConfigurationEntry2.getDescription(),
			actualAMImageConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualAMImageConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry1Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualAMImageConfigurationEntry1Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry1Optional.isPresent());

		AMImageConfigurationEntry actualConfigurationEntry1 =
			actualAMImageConfigurationEntry1Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry1.getName(),
			actualConfigurationEntry1.getName());
		Assert.assertEquals(
			amImageConfigurationEntry1.getDescription(),
			actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryUuid() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				HashMapBuilder.put(
					"max-height", "200"
				).put(
					"max-width", "200"
				).build());

		_amImageConfigurationHelper.updateAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2",
			amImageConfigurationEntry2.getName(),
			amImageConfigurationEntry2.getDescription(), "2-bis",
			amImageConfigurationEntry2.getProperties());

		Optional<AMImageConfigurationEntry>
			nonexistantAMImageConfigurationEntry2Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(
			nonexistantAMImageConfigurationEntry2Optional.isPresent());

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry2Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2-bis");

		assertEnabled(actualAMImageConfigurationEntry2Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry2Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry2 =
			actualAMImageConfigurationEntry2Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry2.getName(),
			actualAMImageConfigurationEntry2.getName());
		Assert.assertEquals(
			amImageConfigurationEntry2.getDescription(),
			actualAMImageConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualAMImageConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AMImageConfigurationEntry>
			actualAMImageConfigurationEntry1Optional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualAMImageConfigurationEntry1Optional);

		Assert.assertTrue(actualAMImageConfigurationEntry1Optional.isPresent());

		AMImageConfigurationEntry actualAMImageConfigurationEntry1 =
			actualAMImageConfigurationEntry1Optional.get();

		Assert.assertEquals(
			amImageConfigurationEntry1.getName(),
			actualAMImageConfigurationEntry1.getName());
		Assert.assertEquals(
			amImageConfigurationEntry1.getDescription(),
			actualAMImageConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualAMImageConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Override
	protected AMImageConfigurationHelper getAMImageConfigurationHelper() {
		return _amImageConfigurationHelper;
	}

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

}