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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateService;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Truong
 */
public class ViewTemplatesDisplayContext
	extends BasePublicationsDisplayContext {

	public ViewTemplatesDisplayContext(
		CTCollectionTemplateService ctCollectionTemplateService,
		HttpServletRequest httpServletRequest, Language language,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(httpServletRequest);

		_ctCollectionTemplateService = ctCollectionTemplateService;
		_httpServletRequest = httpServletRequest;
		_language = language;

		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_renderResponse = renderResponse;
	}

	public Map<String, Object> getDropdownReactData(
		CTCollectionTemplate ctCollectionTemplate) {

		return HashMapBuilder.<String, Object>put(
			"deleteURL",
			_getDeleteTemplateURL(
				ctCollectionTemplate.getCtCollectionTemplateId())
		).put(
			"editURL",
			getEditTemplateURL(ctCollectionTemplate.getCtCollectionTemplateId())
		).build();
	}

	public String getEditTemplateURL(long ctCollectionTemplateId) {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/change_tracking/edit_template"
		).setParameter(
			"ctCollectionTemplateId", ctCollectionTemplateId
		).buildString();
	}

	public SearchContainer<CTCollectionTemplate> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CTCollectionTemplate> searchContainer =
			new SearchContainer<>(
				_renderRequest, new DisplayTerms(_renderRequest), null,
				SearchContainer.DEFAULT_CUR_PARAM,
				SearchContainer.DEFAULT_DELTA,
				PortletURLUtil.getCurrent(_renderRequest, _renderResponse),
				null,
				_language.get(_httpServletRequest, "no-templates-were-found"));

		searchContainer.setId("templates");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		String keywords = displayTerms.getKeywords();

		searchContainer.setResultsAndTotal(
			() -> {
				String column = searchContainer.getOrderByCol();

				if (column.equals("modified-date")) {
					column = "modifiedDate";
				}

				return _ctCollectionTemplateService.getCTCollectionTemplates(
					_themeDisplay.getCompanyId(), keywords,
					searchContainer.getStart(), searchContainer.getEnd(),
					OrderByComparatorFactoryUtil.create(
						"CTCollectionTemplate", column,
						Objects.equals(
							searchContainer.getOrderByType(), "asc")));
			},
			_ctCollectionTemplateService.getCTCollectionTemplatesCount(
				_themeDisplay.getCompanyId(), keywords));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	@Override
	protected String getDefaultOrderByCol() {
		return "name";
	}

	@Override
	protected String getPortalPreferencesPrefix() {
		return "templates";
	}

	private String _getDeleteTemplateURL(long ctCollectionTemplateId) {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/change_tracking/delete_template"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"ctCollectionTemplateId", ctCollectionTemplateId
		).buildString();
	}

	private final CTCollectionTemplateService _ctCollectionTemplateService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CTCollectionTemplate> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}