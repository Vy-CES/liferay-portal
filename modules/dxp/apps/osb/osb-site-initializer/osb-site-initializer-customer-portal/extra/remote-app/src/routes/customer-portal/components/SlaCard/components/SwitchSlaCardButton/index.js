/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

const SwitchSlaCardsButton = ({handleSlaCardClick}) => {
	return (
		<button
			className="btn btn-outline-primary d-none hide ml-3 position-relative rounded-circle"
			onClick={handleSlaCardClick}
		>
			<ClayIcon symbol="angle-right" />
		</button>
	);
};
export default SwitchSlaCardsButton;
