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

package com.liferay.segments.internal.model.listener;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoColumnTable;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoTableTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.portal.search.expando.ExpandoBridgeIndexer;
import com.liferay.segments.internal.odata.entity.OrganizationEntityModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = ModelListener.class)
public class OrganizationExpandoColumnModelListener
	extends BaseModelListener<ExpandoColumn> {

	@Override
	public void onAfterCreate(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		try {
			if (!_isOrganizationCustomField(expandoColumn)) {
				return;
			}

			EntityField organizationEntityField = _getOrganizationEntityField(
				expandoColumn);

			if (organizationEntityField != null) {
				_organizationEntityFields.put(
					expandoColumn.getColumnId(), organizationEntityField);

				_serviceRegistration.unregister();

				_serviceRegistration = _register();
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterRemove(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		if (expandoColumn == null) {
			return;
		}

		if (_organizationEntityFields.containsKey(
				expandoColumn.getColumnId())) {

			_organizationEntityFields.remove(expandoColumn.getColumnId());

			_serviceRegistration.unregister();

			_serviceRegistration = _register();
		}
	}

	@Override
	public void onAfterUpdate(
			ExpandoColumn originalExpandoColumn, ExpandoColumn expandoColumn)
		throws ModelListenerException {

		if (expandoColumn == null) {
			return;
		}

		_organizationEntityFields.remove(expandoColumn.getColumnId());

		onAfterCreate(expandoColumn);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		try {
			_bundleContext = bundleContext;

			_organizationEntityFields = _getOrganizationEntityFields();

			_serviceRegistration = _register();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private String _encodeName(ExpandoColumn expandoColumn) {
		return StringBundler.concat(
			StringPool.UNDERLINE, expandoColumn.getColumnId(),
			StringPool.UNDERLINE,
			Normalizer.normalizeIdentifier(expandoColumn.getName()));
	}

	private EntityField _getOrganizationEntityField(
		ExpandoColumn expandoColumn) {

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		int indexType = GetterUtil.getInteger(
			unicodeProperties.get(ExpandoColumnConstants.INDEX_TYPE));

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_NONE) {
			return null;
		}

		String encodedName = _encodeName(expandoColumn);

		String encodedIndexedFieldName = _expandoBridgeIndexer.encodeFieldName(
			expandoColumn);

		EntityField entityField = null;

		if (expandoColumn.getType() == ExpandoColumnConstants.BOOLEAN) {
			entityField = new BooleanEntityField(
				encodedName, locale -> encodedIndexedFieldName);
		}
		else if (expandoColumn.getType() == ExpandoColumnConstants.DATE) {
			entityField = new DateTimeEntityField(
				encodedName,
				locale -> Field.getSortableFieldName(encodedIndexedFieldName),
				locale -> encodedIndexedFieldName);
		}
		else if ((expandoColumn.getType() == ExpandoColumnConstants.DOUBLE) ||
				 (expandoColumn.getType() ==
					 ExpandoColumnConstants.DOUBLE_ARRAY) ||
				 (expandoColumn.getType() == ExpandoColumnConstants.FLOAT) ||
				 (expandoColumn.getType() ==
					 ExpandoColumnConstants.FLOAT_ARRAY)) {

			entityField = new DoubleEntityField(
				encodedName, locale -> encodedIndexedFieldName);
		}
		else if ((expandoColumn.getType() == ExpandoColumnConstants.INTEGER) ||
				 (expandoColumn.getType() ==
					 ExpandoColumnConstants.INTEGER_ARRAY) ||
				 (expandoColumn.getType() == ExpandoColumnConstants.LONG) ||
				 (expandoColumn.getType() ==
					 ExpandoColumnConstants.LONG_ARRAY) ||
				 (expandoColumn.getType() == ExpandoColumnConstants.SHORT) ||
				 (expandoColumn.getType() ==
					 ExpandoColumnConstants.SHORT_ARRAY)) {

			entityField = new IntegerEntityField(
				encodedName, locale -> encodedIndexedFieldName);
		}
		else if (expandoColumn.getType() ==
					ExpandoColumnConstants.STRING_LOCALIZED) {

			entityField = new StringEntityField(
				encodedName,
				locale -> Field.getLocalizedName(
					locale, encodedIndexedFieldName));
		}
		else {
			entityField = new StringEntityField(
				encodedName, locale -> encodedIndexedFieldName);
		}

		return entityField;
	}

	private Map<Long, EntityField> _getOrganizationEntityFields()
		throws PortalException {

		Map<Long, EntityField> organizationEntityFieldsMap = new HashMap<>();

		long organizationClassNameId = _classNameLocalService.getClassNameId(
			Organization.class.getName());

		List<ExpandoColumn> expandoColumnList =
			_expandoColumnLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					ExpandoColumnTable.INSTANCE
				).from(
					ExpandoColumnTable.INSTANCE
				).where(
					ExpandoColumnTable.INSTANCE.tableId.in(
						DSLQueryFactoryUtil.select(
							ExpandoTableTable.INSTANCE.tableId
						).from(
							ExpandoTableTable.INSTANCE
						).where(
							ExpandoTableTable.INSTANCE.classNameId.eq(
								organizationClassNameId
							).and(
								ExpandoTableTable.INSTANCE.name.eq(
									ExpandoTableConstants.DEFAULT_TABLE_NAME)
							)
						))
				));

		for (ExpandoColumn expandoColumn : expandoColumnList) {
			EntityField organizationEntityField = _getOrganizationEntityField(
				expandoColumn);

			if (organizationEntityField != null) {
				organizationEntityFieldsMap.put(
					expandoColumn.getColumnId(), organizationEntityField);
			}
		}

		return organizationEntityFieldsMap;
	}

	private boolean _isOrganizationCustomField(ExpandoColumn expandoColumn)
		throws PortalException {

		long organizationClassNameId = _classNameLocalService.getClassNameId(
			Organization.class.getName());

		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			expandoColumn.getTableId());

		if ((expandoTable.getClassNameId() != organizationClassNameId) ||
			!ExpandoTableConstants.DEFAULT_TABLE_NAME.equals(
				expandoTable.getName())) {

			return false;
		}

		return true;
	}

	private ServiceRegistration<EntityModel> _register() {
		return _bundleContext.registerService(
			EntityModel.class,
			new OrganizationEntityModel(
				new ArrayList<>(_organizationEntityFields.values())),
			HashMapDictionaryBuilder.<String, Object>put(
				"entity.model.name", OrganizationEntityModel.NAME
			).build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationExpandoColumnModelListener.class);

	private BundleContext _bundleContext;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoBridgeIndexer _expandoBridgeIndexer;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	private Map<Long, EntityField> _organizationEntityFields = new HashMap<>();
	private ServiceRegistration<EntityModel> _serviceRegistration;

}