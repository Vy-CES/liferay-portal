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

package com.liferay.object.internal.action.executor;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.internal.action.util.ObjectEntryVariablesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(service = ObjectActionExecutor.class)
public class AddObjectEntryObjectActionExecutorImpl
	implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		ObjectDefinition targetObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				GetterUtil.getLong(
					parametersUnicodeProperties.get("objectDefinitionId")));

		if (targetObjectDefinition.isSystem()) {
			throw new UnsupportedOperationException();
		}

		ObjectDefinition sourceObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));
		User user = _userLocalService.getUser(userId);

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				targetObjectDefinition.getStorageType());

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				user.getLocale(), null, user),
			targetObjectDefinition,
			new ObjectEntry() {
				{
					properties = ObjectEntryVariablesUtil.getValues(
						_ddmExpressionFactory, parametersUnicodeProperties,
						ObjectEntryVariablesUtil.getActionVariables(
							_dtoConverterRegistry, sourceObjectDefinition,
							payloadJSONObject,
							_systemObjectDefinitionMetadataRegistry));
				}
			},
			String.valueOf(
				_getGroupId(
					companyId, payloadJSONObject, sourceObjectDefinition,
					targetObjectDefinition)));

		if (!GetterUtil.getBoolean(
				parametersUnicodeProperties.get("relatedObjectEntries"))) {

			return;
		}

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getObjectRelationships(
					sourceObjectDefinition.getObjectDefinitionId())) {

			if (!Objects.equals(
					objectRelationship.getObjectDefinitionId2(),
					targetObjectDefinition.getObjectDefinitionId())) {

				continue;
			}

			_objectRelationshipLocalService.
				addObjectRelationshipMappingTableValues(
					userId, objectRelationship.getObjectRelationshipId(),
					payloadJSONObject.getLong("classPK"), objectEntry.getId(),
					_getServiceContext(companyId, userId));
		}
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY;
	}

	private long _getGroupId(
			long companyId, JSONObject payloadJSONObject,
			ObjectDefinition sourceObjectDefinition,
			ObjectDefinition targetObjectDefinition)
		throws Exception {

		ObjectScopeProvider targetObjectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				targetObjectDefinition.getScope());

		if (!targetObjectScopeProvider.isGroupAware()) {
			return 0L;
		}

		ObjectScopeProvider sourceObjectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				sourceObjectDefinition.getScope());

		if (!sourceObjectScopeProvider.isGroupAware()) {
			Group companyGroup = _groupLocalService.fetchCompanyGroup(
				companyId);

			return companyGroup.getGroupId();
		}

		if (sourceObjectDefinition.isSystem()) {
			return MapUtil.getLong(
				(Map<String, Object>)payloadJSONObject.get(
					"model" + sourceObjectDefinition.getName()),
				"groupId");
		}

		return MapUtil.getLong(
			(Map<String, Object>)payloadJSONObject.get("objectEntry"),
			"groupId");
	}

	private ServiceContext _getServiceContext(long companyId, long userId) {
		return new ServiceContext() {
			{
				setCompanyId(companyId);
				setUserId(userId);
			}
		};
	}

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	@Reference
	private UserLocalService _userLocalService;

}