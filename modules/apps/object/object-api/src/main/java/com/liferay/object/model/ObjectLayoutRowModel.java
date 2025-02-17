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

package com.liferay.object.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ObjectLayoutRow service. Represents a row in the &quot;ObjectLayoutRow&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.object.model.impl.ObjectLayoutRowModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.object.model.impl.ObjectLayoutRowImpl</code>.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutRow
 * @generated
 */
@ProviderType
public interface ObjectLayoutRowModel
	extends BaseModel<ObjectLayoutRow>, MVCCModel, ShardedModel,
			StagedAuditedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a object layout row model instance should use the {@link ObjectLayoutRow} interface instead.
	 */

	/**
	 * Returns the primary key of this object layout row.
	 *
	 * @return the primary key of this object layout row
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this object layout row.
	 *
	 * @param primaryKey the primary key of this object layout row
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this object layout row.
	 *
	 * @return the mvcc version of this object layout row
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this object layout row.
	 *
	 * @param mvccVersion the mvcc version of this object layout row
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this object layout row.
	 *
	 * @return the uuid of this object layout row
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this object layout row.
	 *
	 * @param uuid the uuid of this object layout row
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the object layout row ID of this object layout row.
	 *
	 * @return the object layout row ID of this object layout row
	 */
	public long getObjectLayoutRowId();

	/**
	 * Sets the object layout row ID of this object layout row.
	 *
	 * @param objectLayoutRowId the object layout row ID of this object layout row
	 */
	public void setObjectLayoutRowId(long objectLayoutRowId);

	/**
	 * Returns the company ID of this object layout row.
	 *
	 * @return the company ID of this object layout row
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this object layout row.
	 *
	 * @param companyId the company ID of this object layout row
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this object layout row.
	 *
	 * @return the user ID of this object layout row
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this object layout row.
	 *
	 * @param userId the user ID of this object layout row
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this object layout row.
	 *
	 * @return the user uuid of this object layout row
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this object layout row.
	 *
	 * @param userUuid the user uuid of this object layout row
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this object layout row.
	 *
	 * @return the user name of this object layout row
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this object layout row.
	 *
	 * @param userName the user name of this object layout row
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this object layout row.
	 *
	 * @return the create date of this object layout row
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this object layout row.
	 *
	 * @param createDate the create date of this object layout row
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this object layout row.
	 *
	 * @return the modified date of this object layout row
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this object layout row.
	 *
	 * @param modifiedDate the modified date of this object layout row
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the object layout box ID of this object layout row.
	 *
	 * @return the object layout box ID of this object layout row
	 */
	public long getObjectLayoutBoxId();

	/**
	 * Sets the object layout box ID of this object layout row.
	 *
	 * @param objectLayoutBoxId the object layout box ID of this object layout row
	 */
	public void setObjectLayoutBoxId(long objectLayoutBoxId);

	/**
	 * Returns the priority of this object layout row.
	 *
	 * @return the priority of this object layout row
	 */
	public int getPriority();

	/**
	 * Sets the priority of this object layout row.
	 *
	 * @param priority the priority of this object layout row
	 */
	public void setPriority(int priority);

	@Override
	public ObjectLayoutRow cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}