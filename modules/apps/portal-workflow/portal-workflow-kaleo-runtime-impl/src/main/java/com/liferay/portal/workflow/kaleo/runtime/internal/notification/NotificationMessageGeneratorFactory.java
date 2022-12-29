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

package com.liferay.portal.workflow.kaleo.runtime.internal.notification;

import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationMessageGenerator;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(service = NotificationMessageGeneratorFactory.class)
public class NotificationMessageGeneratorFactory {

	public NotificationMessageGenerator getNotificationMessageGenerator(
			String templateLanguage)
		throws WorkflowException {

		NotificationMessageGenerator notificationMessageGenerator =
			_notificationMessageGenerators.get(templateLanguage);

		if (notificationMessageGenerator == null) {
			throw new WorkflowException(
				"Invalid template language " + templateLanguage);
		}

		return notificationMessageGenerator;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addNotificationMessageGenerator(
		NotificationMessageGenerator notificationMessageGenerator) {

		String[] templateLanguages =
			notificationMessageGenerator.getTemplateLanguages();

		for (String templateLanguage : templateLanguages) {
			_notificationMessageGenerators.put(
				templateLanguage, notificationMessageGenerator);
		}
	}

	protected void removeNotificationMessageGenerator(
		NotificationMessageGenerator notificationMessageGenerator) {

		String[] templateLanguages =
			notificationMessageGenerator.getTemplateLanguages();

		for (String templateLanguage : templateLanguages) {
			_notificationMessageGenerators.remove(templateLanguage);
		}
	}

	private final Map<String, NotificationMessageGenerator>
		_notificationMessageGenerators = new HashMap<>();

}