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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CPDefinitionInventoryModel;
import com.liferay.commerce.model.CPDefinitionInventorySoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the CPDefinitionInventory service. Represents a row in the &quot;CPDefinitionInventory&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CPDefinitionInventoryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CPDefinitionInventoryImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryImpl
 * @see CPDefinitionInventory
 * @see CPDefinitionInventoryModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CPDefinitionInventoryModelImpl extends BaseModelImpl<CPDefinitionInventory>
	implements CPDefinitionInventoryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cp definition inventory model instance should use the {@link CPDefinitionInventory} interface instead.
	 */
	public static final String TABLE_NAME = "CPDefinitionInventory";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "CPDefinitionInventoryId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "CPDefinitionId", Types.BIGINT },
			{ "CPDefinitionInventoryEngine", Types.VARCHAR },
			{ "lowStockActivity", Types.VARCHAR },
			{ "displayAvailability", Types.BOOLEAN },
			{ "displayStockQuantity", Types.BOOLEAN },
			{ "minStockQuantity", Types.INTEGER },
			{ "backOrders", Types.BOOLEAN },
			{ "minOrderQuantity", Types.INTEGER },
			{ "maxOrderQuantity", Types.INTEGER },
			{ "allowedOrderQuantities", Types.VARCHAR },
			{ "multipleOrderQuantity", Types.INTEGER }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("CPDefinitionInventoryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("CPDefinitionInventoryEngine", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lowStockActivity", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("displayAvailability", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("displayStockQuantity", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("minStockQuantity", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("backOrders", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("minOrderQuantity", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("maxOrderQuantity", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("allowedOrderQuantities", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("multipleOrderQuantity", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE = "create table CPDefinitionInventory (uuid_ VARCHAR(75) null,CPDefinitionInventoryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPDefinitionId LONG,CPDefinitionInventoryEngine VARCHAR(75) null,lowStockActivity VARCHAR(75) null,displayAvailability BOOLEAN,displayStockQuantity BOOLEAN,minStockQuantity INTEGER,backOrders BOOLEAN,minOrderQuantity INTEGER,maxOrderQuantity INTEGER,allowedOrderQuantities VARCHAR(75) null,multipleOrderQuantity INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table CPDefinitionInventory";
	public static final String ORDER_BY_JPQL = " ORDER BY cpDefinitionInventory.CPDefinitionInventoryId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CPDefinitionInventory.CPDefinitionInventoryId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.model.CPDefinitionInventory"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.model.CPDefinitionInventory"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.model.CPDefinitionInventory"),
			true);
	public static final long CPDEFINITIONID_COLUMN_BITMASK = 1L;
	public static final long COMPANYID_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long UUID_COLUMN_BITMASK = 8L;
	public static final long CPDEFINITIONINVENTORYID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CPDefinitionInventory toModel(
		CPDefinitionInventorySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CPDefinitionInventory model = new CPDefinitionInventoryImpl();

		model.setUuid(soapModel.getUuid());
		model.setCPDefinitionInventoryId(soapModel.getCPDefinitionInventoryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCPDefinitionId(soapModel.getCPDefinitionId());
		model.setCPDefinitionInventoryEngine(soapModel.getCPDefinitionInventoryEngine());
		model.setLowStockActivity(soapModel.getLowStockActivity());
		model.setDisplayAvailability(soapModel.isDisplayAvailability());
		model.setDisplayStockQuantity(soapModel.isDisplayStockQuantity());
		model.setMinStockQuantity(soapModel.getMinStockQuantity());
		model.setBackOrders(soapModel.isBackOrders());
		model.setMinOrderQuantity(soapModel.getMinOrderQuantity());
		model.setMaxOrderQuantity(soapModel.getMaxOrderQuantity());
		model.setAllowedOrderQuantities(soapModel.getAllowedOrderQuantities());
		model.setMultipleOrderQuantity(soapModel.getMultipleOrderQuantity());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CPDefinitionInventory> toModels(
		CPDefinitionInventorySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CPDefinitionInventory> models = new ArrayList<CPDefinitionInventory>(soapModels.length);

		for (CPDefinitionInventorySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.model.CPDefinitionInventory"));

	public CPDefinitionInventoryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _CPDefinitionInventoryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCPDefinitionInventoryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _CPDefinitionInventoryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionInventory.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionInventory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionInventoryId", getCPDefinitionInventoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPDefinitionInventoryEngine",
			getCPDefinitionInventoryEngine());
		attributes.put("lowStockActivity", getLowStockActivity());
		attributes.put("displayAvailability", isDisplayAvailability());
		attributes.put("displayStockQuantity", isDisplayStockQuantity());
		attributes.put("minStockQuantity", getMinStockQuantity());
		attributes.put("backOrders", isBackOrders());
		attributes.put("minOrderQuantity", getMinOrderQuantity());
		attributes.put("maxOrderQuantity", getMaxOrderQuantity());
		attributes.put("allowedOrderQuantities", getAllowedOrderQuantities());
		attributes.put("multipleOrderQuantity", getMultipleOrderQuantity());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionInventoryId = (Long)attributes.get(
				"CPDefinitionInventoryId");

		if (CPDefinitionInventoryId != null) {
			setCPDefinitionInventoryId(CPDefinitionInventoryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		String CPDefinitionInventoryEngine = (String)attributes.get(
				"CPDefinitionInventoryEngine");

		if (CPDefinitionInventoryEngine != null) {
			setCPDefinitionInventoryEngine(CPDefinitionInventoryEngine);
		}

		String lowStockActivity = (String)attributes.get("lowStockActivity");

		if (lowStockActivity != null) {
			setLowStockActivity(lowStockActivity);
		}

		Boolean displayAvailability = (Boolean)attributes.get(
				"displayAvailability");

		if (displayAvailability != null) {
			setDisplayAvailability(displayAvailability);
		}

		Boolean displayStockQuantity = (Boolean)attributes.get(
				"displayStockQuantity");

		if (displayStockQuantity != null) {
			setDisplayStockQuantity(displayStockQuantity);
		}

		Integer minStockQuantity = (Integer)attributes.get("minStockQuantity");

		if (minStockQuantity != null) {
			setMinStockQuantity(minStockQuantity);
		}

		Boolean backOrders = (Boolean)attributes.get("backOrders");

		if (backOrders != null) {
			setBackOrders(backOrders);
		}

		Integer minOrderQuantity = (Integer)attributes.get("minOrderQuantity");

		if (minOrderQuantity != null) {
			setMinOrderQuantity(minOrderQuantity);
		}

		Integer maxOrderQuantity = (Integer)attributes.get("maxOrderQuantity");

		if (maxOrderQuantity != null) {
			setMaxOrderQuantity(maxOrderQuantity);
		}

		String allowedOrderQuantities = (String)attributes.get(
				"allowedOrderQuantities");

		if (allowedOrderQuantities != null) {
			setAllowedOrderQuantities(allowedOrderQuantities);
		}

		Integer multipleOrderQuantity = (Integer)attributes.get(
				"multipleOrderQuantity");

		if (multipleOrderQuantity != null) {
			setMultipleOrderQuantity(multipleOrderQuantity);
		}
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getCPDefinitionInventoryId() {
		return _CPDefinitionInventoryId;
	}

	@Override
	public void setCPDefinitionInventoryId(long CPDefinitionInventoryId) {
		_CPDefinitionInventoryId = CPDefinitionInventoryId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_columnBitmask |= CPDEFINITIONID_COLUMN_BITMASK;

		if (!_setOriginalCPDefinitionId) {
			_setOriginalCPDefinitionId = true;

			_originalCPDefinitionId = _CPDefinitionId;
		}

		_CPDefinitionId = CPDefinitionId;
	}

	public long getOriginalCPDefinitionId() {
		return _originalCPDefinitionId;
	}

	@JSON
	@Override
	public String getCPDefinitionInventoryEngine() {
		if (_CPDefinitionInventoryEngine == null) {
			return "";
		}
		else {
			return _CPDefinitionInventoryEngine;
		}
	}

	@Override
	public void setCPDefinitionInventoryEngine(
		String CPDefinitionInventoryEngine) {
		_CPDefinitionInventoryEngine = CPDefinitionInventoryEngine;
	}

	@JSON
	@Override
	public String getLowStockActivity() {
		if (_lowStockActivity == null) {
			return "";
		}
		else {
			return _lowStockActivity;
		}
	}

	@Override
	public void setLowStockActivity(String lowStockActivity) {
		_lowStockActivity = lowStockActivity;
	}

	@JSON
	@Override
	public boolean getDisplayAvailability() {
		return _displayAvailability;
	}

	@JSON
	@Override
	public boolean isDisplayAvailability() {
		return _displayAvailability;
	}

	@Override
	public void setDisplayAvailability(boolean displayAvailability) {
		_displayAvailability = displayAvailability;
	}

	@JSON
	@Override
	public boolean getDisplayStockQuantity() {
		return _displayStockQuantity;
	}

	@JSON
	@Override
	public boolean isDisplayStockQuantity() {
		return _displayStockQuantity;
	}

	@Override
	public void setDisplayStockQuantity(boolean displayStockQuantity) {
		_displayStockQuantity = displayStockQuantity;
	}

	@JSON
	@Override
	public int getMinStockQuantity() {
		return _minStockQuantity;
	}

	@Override
	public void setMinStockQuantity(int minStockQuantity) {
		_minStockQuantity = minStockQuantity;
	}

	@JSON
	@Override
	public boolean getBackOrders() {
		return _backOrders;
	}

	@JSON
	@Override
	public boolean isBackOrders() {
		return _backOrders;
	}

	@Override
	public void setBackOrders(boolean backOrders) {
		_backOrders = backOrders;
	}

	@JSON
	@Override
	public int getMinOrderQuantity() {
		return _minOrderQuantity;
	}

	@Override
	public void setMinOrderQuantity(int minOrderQuantity) {
		_minOrderQuantity = minOrderQuantity;
	}

	@JSON
	@Override
	public int getMaxOrderQuantity() {
		return _maxOrderQuantity;
	}

	@Override
	public void setMaxOrderQuantity(int maxOrderQuantity) {
		_maxOrderQuantity = maxOrderQuantity;
	}

	@JSON
	@Override
	public String getAllowedOrderQuantities() {
		if (_allowedOrderQuantities == null) {
			return "";
		}
		else {
			return _allowedOrderQuantities;
		}
	}

	@Override
	public void setAllowedOrderQuantities(String allowedOrderQuantities) {
		_allowedOrderQuantities = allowedOrderQuantities;
	}

	@JSON
	@Override
	public int getMultipleOrderQuantity() {
		return _multipleOrderQuantity;
	}

	@Override
	public void setMultipleOrderQuantity(int multipleOrderQuantity) {
		_multipleOrderQuantity = multipleOrderQuantity;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				CPDefinitionInventory.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CPDefinitionInventory.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CPDefinitionInventory toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CPDefinitionInventory)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CPDefinitionInventoryImpl cpDefinitionInventoryImpl = new CPDefinitionInventoryImpl();

		cpDefinitionInventoryImpl.setUuid(getUuid());
		cpDefinitionInventoryImpl.setCPDefinitionInventoryId(getCPDefinitionInventoryId());
		cpDefinitionInventoryImpl.setGroupId(getGroupId());
		cpDefinitionInventoryImpl.setCompanyId(getCompanyId());
		cpDefinitionInventoryImpl.setUserId(getUserId());
		cpDefinitionInventoryImpl.setUserName(getUserName());
		cpDefinitionInventoryImpl.setCreateDate(getCreateDate());
		cpDefinitionInventoryImpl.setModifiedDate(getModifiedDate());
		cpDefinitionInventoryImpl.setCPDefinitionId(getCPDefinitionId());
		cpDefinitionInventoryImpl.setCPDefinitionInventoryEngine(getCPDefinitionInventoryEngine());
		cpDefinitionInventoryImpl.setLowStockActivity(getLowStockActivity());
		cpDefinitionInventoryImpl.setDisplayAvailability(isDisplayAvailability());
		cpDefinitionInventoryImpl.setDisplayStockQuantity(isDisplayStockQuantity());
		cpDefinitionInventoryImpl.setMinStockQuantity(getMinStockQuantity());
		cpDefinitionInventoryImpl.setBackOrders(isBackOrders());
		cpDefinitionInventoryImpl.setMinOrderQuantity(getMinOrderQuantity());
		cpDefinitionInventoryImpl.setMaxOrderQuantity(getMaxOrderQuantity());
		cpDefinitionInventoryImpl.setAllowedOrderQuantities(getAllowedOrderQuantities());
		cpDefinitionInventoryImpl.setMultipleOrderQuantity(getMultipleOrderQuantity());

		cpDefinitionInventoryImpl.resetOriginalValues();

		return cpDefinitionInventoryImpl;
	}

	@Override
	public int compareTo(CPDefinitionInventory cpDefinitionInventory) {
		long primaryKey = cpDefinitionInventory.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionInventory)) {
			return false;
		}

		CPDefinitionInventory cpDefinitionInventory = (CPDefinitionInventory)obj;

		long primaryKey = cpDefinitionInventory.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		CPDefinitionInventoryModelImpl cpDefinitionInventoryModelImpl = this;

		cpDefinitionInventoryModelImpl._originalUuid = cpDefinitionInventoryModelImpl._uuid;

		cpDefinitionInventoryModelImpl._originalGroupId = cpDefinitionInventoryModelImpl._groupId;

		cpDefinitionInventoryModelImpl._setOriginalGroupId = false;

		cpDefinitionInventoryModelImpl._originalCompanyId = cpDefinitionInventoryModelImpl._companyId;

		cpDefinitionInventoryModelImpl._setOriginalCompanyId = false;

		cpDefinitionInventoryModelImpl._setModifiedDate = false;

		cpDefinitionInventoryModelImpl._originalCPDefinitionId = cpDefinitionInventoryModelImpl._CPDefinitionId;

		cpDefinitionInventoryModelImpl._setOriginalCPDefinitionId = false;

		cpDefinitionInventoryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CPDefinitionInventory> toCacheModel() {
		CPDefinitionInventoryCacheModel cpDefinitionInventoryCacheModel = new CPDefinitionInventoryCacheModel();

		cpDefinitionInventoryCacheModel.uuid = getUuid();

		String uuid = cpDefinitionInventoryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			cpDefinitionInventoryCacheModel.uuid = null;
		}

		cpDefinitionInventoryCacheModel.CPDefinitionInventoryId = getCPDefinitionInventoryId();

		cpDefinitionInventoryCacheModel.groupId = getGroupId();

		cpDefinitionInventoryCacheModel.companyId = getCompanyId();

		cpDefinitionInventoryCacheModel.userId = getUserId();

		cpDefinitionInventoryCacheModel.userName = getUserName();

		String userName = cpDefinitionInventoryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			cpDefinitionInventoryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			cpDefinitionInventoryCacheModel.createDate = createDate.getTime();
		}
		else {
			cpDefinitionInventoryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			cpDefinitionInventoryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			cpDefinitionInventoryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		cpDefinitionInventoryCacheModel.CPDefinitionId = getCPDefinitionId();

		cpDefinitionInventoryCacheModel.CPDefinitionInventoryEngine = getCPDefinitionInventoryEngine();

		String CPDefinitionInventoryEngine = cpDefinitionInventoryCacheModel.CPDefinitionInventoryEngine;

		if ((CPDefinitionInventoryEngine != null) &&
				(CPDefinitionInventoryEngine.length() == 0)) {
			cpDefinitionInventoryCacheModel.CPDefinitionInventoryEngine = null;
		}

		cpDefinitionInventoryCacheModel.lowStockActivity = getLowStockActivity();

		String lowStockActivity = cpDefinitionInventoryCacheModel.lowStockActivity;

		if ((lowStockActivity != null) && (lowStockActivity.length() == 0)) {
			cpDefinitionInventoryCacheModel.lowStockActivity = null;
		}

		cpDefinitionInventoryCacheModel.displayAvailability = isDisplayAvailability();

		cpDefinitionInventoryCacheModel.displayStockQuantity = isDisplayStockQuantity();

		cpDefinitionInventoryCacheModel.minStockQuantity = getMinStockQuantity();

		cpDefinitionInventoryCacheModel.backOrders = isBackOrders();

		cpDefinitionInventoryCacheModel.minOrderQuantity = getMinOrderQuantity();

		cpDefinitionInventoryCacheModel.maxOrderQuantity = getMaxOrderQuantity();

		cpDefinitionInventoryCacheModel.allowedOrderQuantities = getAllowedOrderQuantities();

		String allowedOrderQuantities = cpDefinitionInventoryCacheModel.allowedOrderQuantities;

		if ((allowedOrderQuantities != null) &&
				(allowedOrderQuantities.length() == 0)) {
			cpDefinitionInventoryCacheModel.allowedOrderQuantities = null;
		}

		cpDefinitionInventoryCacheModel.multipleOrderQuantity = getMultipleOrderQuantity();

		return cpDefinitionInventoryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", CPDefinitionInventoryId=");
		sb.append(getCPDefinitionInventoryId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", CPDefinitionId=");
		sb.append(getCPDefinitionId());
		sb.append(", CPDefinitionInventoryEngine=");
		sb.append(getCPDefinitionInventoryEngine());
		sb.append(", lowStockActivity=");
		sb.append(getLowStockActivity());
		sb.append(", displayAvailability=");
		sb.append(isDisplayAvailability());
		sb.append(", displayStockQuantity=");
		sb.append(isDisplayStockQuantity());
		sb.append(", minStockQuantity=");
		sb.append(getMinStockQuantity());
		sb.append(", backOrders=");
		sb.append(isBackOrders());
		sb.append(", minOrderQuantity=");
		sb.append(getMinOrderQuantity());
		sb.append(", maxOrderQuantity=");
		sb.append(getMaxOrderQuantity());
		sb.append(", allowedOrderQuantities=");
		sb.append(getAllowedOrderQuantities());
		sb.append(", multipleOrderQuantity=");
		sb.append(getMultipleOrderQuantity());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(61);

		sb.append("<model><model-name>");
		sb.append("com.liferay.commerce.model.CPDefinitionInventory");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPDefinitionInventoryId</column-name><column-value><![CDATA[");
		sb.append(getCPDefinitionInventoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPDefinitionId</column-name><column-value><![CDATA[");
		sb.append(getCPDefinitionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPDefinitionInventoryEngine</column-name><column-value><![CDATA[");
		sb.append(getCPDefinitionInventoryEngine());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lowStockActivity</column-name><column-value><![CDATA[");
		sb.append(getLowStockActivity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>displayAvailability</column-name><column-value><![CDATA[");
		sb.append(isDisplayAvailability());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>displayStockQuantity</column-name><column-value><![CDATA[");
		sb.append(isDisplayStockQuantity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minStockQuantity</column-name><column-value><![CDATA[");
		sb.append(getMinStockQuantity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>backOrders</column-name><column-value><![CDATA[");
		sb.append(isBackOrders());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minOrderQuantity</column-name><column-value><![CDATA[");
		sb.append(getMinOrderQuantity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>maxOrderQuantity</column-name><column-value><![CDATA[");
		sb.append(getMaxOrderQuantity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allowedOrderQuantities</column-name><column-value><![CDATA[");
		sb.append(getAllowedOrderQuantities());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>multipleOrderQuantity</column-name><column-value><![CDATA[");
		sb.append(getMultipleOrderQuantity());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CPDefinitionInventory.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CPDefinitionInventory.class, ModelWrapper.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _CPDefinitionInventoryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _CPDefinitionId;
	private long _originalCPDefinitionId;
	private boolean _setOriginalCPDefinitionId;
	private String _CPDefinitionInventoryEngine;
	private String _lowStockActivity;
	private boolean _displayAvailability;
	private boolean _displayStockQuantity;
	private int _minStockQuantity;
	private boolean _backOrders;
	private int _minOrderQuantity;
	private int _maxOrderQuantity;
	private String _allowedOrderQuantities;
	private int _multipleOrderQuantity;
	private long _columnBitmask;
	private CPDefinitionInventory _escapedModel;
}