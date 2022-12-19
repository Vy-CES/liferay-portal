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

package com.liferay.list.type.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ListTypeEntry service. Represents a row in the &quot;ListTypeEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.list.type.model.impl.ListTypeEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.list.type.model.impl.ListTypeEntryImpl</code>.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see ListTypeEntry
 * @generated
 */
@ProviderType
public interface ListTypeEntryModel
	extends BaseModel<ListTypeEntry>, LocalizedModel, MVCCModel, ShardedModel,
			StagedAuditedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a list type entry model instance should use the {@link ListTypeEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this list type entry.
	 *
	 * @return the primary key of this list type entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this list type entry.
	 *
	 * @param primaryKey the primary key of this list type entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this list type entry.
	 *
	 * @return the mvcc version of this list type entry
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this list type entry.
	 *
	 * @param mvccVersion the mvcc version of this list type entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this list type entry.
	 *
	 * @return the uuid of this list type entry
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this list type entry.
	 *
	 * @param uuid the uuid of this list type entry
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the external reference code of this list type entry.
	 *
	 * @return the external reference code of this list type entry
	 */
	@AutoEscape
	public String getExternalReferenceCode();

	/**
	 * Sets the external reference code of this list type entry.
	 *
	 * @param externalReferenceCode the external reference code of this list type entry
	 */
	public void setExternalReferenceCode(String externalReferenceCode);

	/**
	 * Returns the list type entry ID of this list type entry.
	 *
	 * @return the list type entry ID of this list type entry
	 */
	public long getListTypeEntryId();

	/**
	 * Sets the list type entry ID of this list type entry.
	 *
	 * @param listTypeEntryId the list type entry ID of this list type entry
	 */
	public void setListTypeEntryId(long listTypeEntryId);

	/**
	 * Returns the company ID of this list type entry.
	 *
	 * @return the company ID of this list type entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this list type entry.
	 *
	 * @param companyId the company ID of this list type entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this list type entry.
	 *
	 * @return the user ID of this list type entry
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this list type entry.
	 *
	 * @param userId the user ID of this list type entry
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this list type entry.
	 *
	 * @return the user uuid of this list type entry
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this list type entry.
	 *
	 * @param userUuid the user uuid of this list type entry
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this list type entry.
	 *
	 * @return the user name of this list type entry
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this list type entry.
	 *
	 * @param userName the user name of this list type entry
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this list type entry.
	 *
	 * @return the create date of this list type entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this list type entry.
	 *
	 * @param createDate the create date of this list type entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this list type entry.
	 *
	 * @return the modified date of this list type entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this list type entry.
	 *
	 * @param modifiedDate the modified date of this list type entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the list type definition ID of this list type entry.
	 *
	 * @return the list type definition ID of this list type entry
	 */
	public long getListTypeDefinitionId();

	/**
	 * Sets the list type definition ID of this list type entry.
	 *
	 * @param listTypeDefinitionId the list type definition ID of this list type entry
	 */
	public void setListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the key of this list type entry.
	 *
	 * @return the key of this list type entry
	 */
	@AutoEscape
	public String getKey();

	/**
	 * Sets the key of this list type entry.
	 *
	 * @param key the key of this list type entry
	 */
	public void setKey(String key);

	/**
	 * Returns the name of this list type entry.
	 *
	 * @return the name of this list type entry
	 */
	public String getName();

	/**
	 * Returns the localized name of this list type entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this list type entry
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this list type entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this list type entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this list type entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this list type entry
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this list type entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this list type entry
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this list type entry.
	 *
	 * @return the locales and localized names of this list type entry
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this list type entry.
	 *
	 * @param name the name of this list type entry
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this list type entry in the language.
	 *
	 * @param name the localized name of this list type entry
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this list type entry in the language, and sets the default locale.
	 *
	 * @param name the localized name of this list type entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this list type entry from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this list type entry
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this list type entry from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this list type entry
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the type of this list type entry.
	 *
	 * @return the type of this list type entry
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this list type entry.
	 *
	 * @param type the type of this list type entry
	 */
	public void setType(String type);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public ListTypeEntry cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}