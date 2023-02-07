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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.persistence.listener;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.constants.TensorFlowDestinationNames;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration",
	service = ConfigurationModelListener.class
)
public class
	TensorFlowImageAssetAutoTagProviderCompanyConfigurationModelListener
		implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		TensorFlowImageAssetAutoTagProviderCompanyConfiguration
			tensorFlowImageAssetAutoTagProviderCompanyConfiguration =
				ConfigurableUtil.createConfigurable(
					TensorFlowImageAssetAutoTagProviderCompanyConfiguration.
						class,
					properties);

		if (tensorFlowImageAssetAutoTagProviderCompanyConfiguration.enabled()) {
			_messageBus.sendMessage(
				TensorFlowDestinationNames.TENSORFLOW_MODEL_DOWNLOAD,
				new Message());
		}
	}

	@Reference
	private MessageBus _messageBus;

}