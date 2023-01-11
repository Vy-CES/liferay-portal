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

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class PriceListAccountUtil {

	public static CommercePriceListAccountRel addCommercePriceListAccountRel(
			AccountEntryService accountEntryService,
			CommercePriceListAccountRelService
				commercePriceListAccountRelService,
			PriceListAccount priceListAccount,
			CommercePriceList commercePriceList,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext = serviceContextHelper.getServiceContext(
			commercePriceList.getGroupId());

		AccountEntry accountEntry;

		if (Validator.isNull(
				priceListAccount.getAccountExternalReferenceCode())) {

			accountEntry = accountEntryService.getAccountEntry(
				priceListAccount.getAccountId());
		}
		else {
			accountEntry =
				accountEntryService.fetchAccountEntryByExternalReferenceCode(
					serviceContext.getCompanyId(),
					priceListAccount.getAccountExternalReferenceCode());

			if (accountEntry == null) {
				throw new NoSuchEntryException(
					"Unable to find account with external reference code " +
						priceListAccount.getAccountExternalReferenceCode());
			}
		}

		return commercePriceListAccountRelService.
			addCommercePriceListAccountRel(
				commercePriceList.getCommercePriceListId(),
				accountEntry.getAccountEntryId(),
				GetterUtil.get(priceListAccount.getOrder(), 0), serviceContext);
	}

}