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

package com.liferay.fragment.renderer.react.internal.model.listener;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.react.internal.util.FragmentEntryFragmentRendererReactUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ModelListener.class)
public class FragmentEntryLinkModelListener
	extends BaseModelListener<FragmentEntryLink> {

	@Override
	public void onAfterCreate(FragmentEntryLink fragmentEntryLink) {
		if (!fragmentEntryLink.isTypeReact()) {
			return;
		}

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		npmRegistryUpdate.registerJSModule(
			_jsPackage,
			FragmentEntryFragmentRendererReactUtil.getModuleName(
				fragmentEntryLink),
			_dependencies, _getJs(fragmentEntryLink), null);

		npmRegistryUpdate.finish();
	}

	@Override
	public void onAfterRemove(FragmentEntryLink fragmentEntryLink) {
		if (!fragmentEntryLink.isTypeReact()) {
			return;
		}

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		npmRegistryUpdate.unregisterJSModule(
			_jsPackage.getJSModule(
				FragmentEntryFragmentRendererReactUtil.getModuleName(
					fragmentEntryLink)));

		npmRegistryUpdate.finish();
	}

	@Override
	public void onAfterUpdate(
		FragmentEntryLink originalFragmentEntryLink,
		FragmentEntryLink fragmentEntryLink) {

		if (!fragmentEntryLink.isTypeReact()) {
			return;
		}

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		npmRegistryUpdate.unregisterJSModule(
			_jsPackage.getJSModule(
				FragmentEntryFragmentRendererReactUtil.getModuleName(
					originalFragmentEntryLink)));

		npmRegistryUpdate.registerJSModule(
			_jsPackage,
			FragmentEntryFragmentRendererReactUtil.getModuleName(
				fragmentEntryLink),
			_dependencies, _getJs(fragmentEntryLink), null);

		npmRegistryUpdate.finish();
	}

	@Activate
	protected void activate() {
		_jsPackage = _npmResolver.getJSPackage();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				FragmentConstants.TYPE_REACT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			npmRegistryUpdate.registerJSModule(
				_jsPackage,
				FragmentEntryFragmentRendererReactUtil.getModuleName(
					fragmentEntryLink),
				_dependencies, _getJs(fragmentEntryLink), null);
		}

		npmRegistryUpdate.finish();
	}

	@Deactivate
	protected void deactivate() {
		_jsPackage = _npmResolver.getJSPackage();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				FragmentConstants.TYPE_REACT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			npmRegistryUpdate.unregisterJSModule(
				_jsPackage.getJSModule(
					FragmentEntryFragmentRendererReactUtil.getModuleName(
						fragmentEntryLink)));
		}

		npmRegistryUpdate.finish();
	}

	private String _getJs(FragmentEntryLink fragmentEntryLink) {
		return StringUtil.replace(
			fragmentEntryLink.getJs(),
			new String[] {
				"'__FRAGMENT_MODULE_NAME__'", "'__REACT_PROVIDER__$react'",
				"'frontend-js-react-web$react'"
			},
			new String[] {
				StringBundler.concat(
					StringPool.APOSTROPHE,
					ModuleNameUtil.getModuleResolvedId(
						_jsPackage,
						FragmentEntryFragmentRendererReactUtil.getModuleName(
							fragmentEntryLink)),
					StringPool.APOSTROPHE),
				StringBundler.concat(
					StringPool.APOSTROPHE, _DEPENDENCY_PORTAL_REACT,
					StringPool.APOSTROPHE),
				StringBundler.concat(
					StringPool.APOSTROPHE, _DEPENDENCY_PORTAL_REACT,
					StringPool.APOSTROPHE)
			});
	}

	private static final String _DEPENDENCY_PORTAL_REACT =
		"liferay!frontend-js-react-web$react";

	private static final List<String> _dependencies = Collections.singletonList(
		_DEPENDENCY_PORTAL_REACT);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private JSPackage _jsPackage;

	@Reference
	private NPMRegistry _npmRegistry;

	@Reference
	private NPMResolver _npmResolver;

}