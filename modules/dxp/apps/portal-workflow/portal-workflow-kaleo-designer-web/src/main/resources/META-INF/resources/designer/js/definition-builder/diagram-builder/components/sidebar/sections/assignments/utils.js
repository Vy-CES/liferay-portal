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

function getAssignmentType(assignments) {
	return assignments?.assignmentType[0] === 'user' &&
		!Object.keys(assignments).includes('emailAddress') &&
        !Object.keys(assignments).includes('screenName') &&
		!Object.keys(assignments).includes('userId')
		? 'assetCreator'
		: assignments?.assignmentType[0];
}

export {getAssignmentType};
