<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionGroupedEntriesDisplayContext cpDefinitionGroupedEntriesDisplayContext = (CPDefinitionGroupedEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionGroupedEntry cpDefinitionGroupedEntry = cpDefinitionGroupedEntriesDisplayContext.getCPDefinitionGroupedEntry();

CPDefinition cpDefinition = cpDefinitionGroupedEntry.getCPDefinition();

CProduct cProduct = cpDefinitionGroupedEntry.getEntryCProduct();

CPDefinition cProductCPDefinition = CPDefinitionLocalServiceUtil.getCPDefinition(cProduct.getPublishedCPDefinitionId());

PortletURL groupedProductsURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/cp_definitions/edit_cp_definition"
).setParameter(
	"cpDefinitionId", cpDefinition.getCPDefinitionId()
).setParameter(
	"screenNavigationCategoryKey", cpDefinitionGroupedEntriesDisplayContext.getScreenNavigationCategoryKey()
).buildPortletURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(groupedProductsURL.toString());

renderResponse.setTitle(cpDefinition.getName(themeDisplay.getLanguageId()) + " - " + cProductCPDefinition.getName(themeDisplay.getLanguageId()));
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_grouped_entry" var="editCPDefinitionGroupedEntryActionURL">
	<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition_grouped_entry" />
</portlet:actionURL>

<aui:form action="<%= editCPDefinitionGroupedEntryActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCPDefinitionGroupedEntry();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= groupedProductsURL %>" />
	<aui:input name="cpDefinitionGroupedEntryId" type="hidden" value="<%= cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId() %>" />

	<aui:model-context bean="<%= cpDefinitionGroupedEntry %>" model="<%= CPDefinitionGroupedEntry.class %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CPDefinitionGroupedEntryQuantityException.class %>" message="please-enter-a-valid-quantity" />

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input name="priority" />

					<aui:input name="quantity" />
				</aui:fieldset>
			</div>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= groupedProductsURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCPDefinitionGroupedEntry() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>