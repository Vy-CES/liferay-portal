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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.kernel.workflow.comparator.WorkflowDefinitionModifiedDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowDefinitionNameComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowInstanceCompletedComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowInstanceEndDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowInstanceStartDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowInstanceStateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowLogCreateDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowLogUserIdComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowTaskCompletionDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowTaskCreateDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowTaskDueDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowTaskInstanceIdComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowTaskModifiedDateComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowTaskNameComparator;
import com.liferay.portal.kernel.workflow.comparator.WorkflowTaskUserIdComparator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = WorkflowComparatorFactory.class)
public class WorkflowComparatorFactoryImpl
	implements WorkflowComparatorFactory {

	@Override
	public OrderByComparator<WorkflowDefinition>
		getDefinitionModifiedDateComparator(boolean ascending) {

		return new WorkflowDefinitionModifiedDateComparator(
			ascending, "modifiedDate ASC, version ASC",
			"modifiedDate DESC, version DESC",
			new String[] {"modifiedDate", "version"});
	}

	@Override
	public OrderByComparator<WorkflowDefinition> getDefinitionNameComparator(
		boolean ascending) {

		return new WorkflowDefinitionNameComparator(
			ascending, "name ASC, version ASC", "name DESC, version DESC",
			new String[] {"name", "version"});
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceCompletedComparator(
		boolean ascending) {

		return new WorkflowInstanceCompletedComparator(
			ascending, "completed ASC", "completed DESC",
			new String[] {"completed"});
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceEndDateComparator(
		boolean ascending) {

		return new WorkflowInstanceEndDateComparator(
			ascending, "completed ASC, completionDate ASC, kaleoInstanceId ASC",
			"completed ASC, completionDate DESC, kaleoInstanceId DESC",
			new String[] {"completed", "completionDate", "kaleoInstanceId"});
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceStartDateComparator(
		boolean ascending) {

		return new WorkflowInstanceStartDateComparator(
			ascending, "completed ASC, createDate ASC, kaleoInstanceId ASC",
			"completed ASC, createDate DESC, kaleoInstanceId DESC",
			new String[] {"completed", "createDate", "kaleoInstanceId"});
	}

	@Override
	public OrderByComparator<WorkflowInstance> getInstanceStateComparator(
		boolean ascending) {

		return new WorkflowInstanceStateComparator(
			ascending, "completed ASC, state ASC, kaleoInstanceId ASC",
			"completed ASC, state DESC, kaleoInstanceId DESC",
			new String[] {"completed", "state", "kaleoInstanceId"});
	}

	@Override
	public OrderByComparator<WorkflowLog> getLogCreateDateComparator(
		boolean ascending) {

		return new WorkflowLogCreateDateComparator(
			ascending, "createDate ASC, kaleoLogId ASC",
			"createDate DESC, kaleoLogId DESC",
			new String[] {"createDate", "kaleoLogId"});
	}

	@Override
	public OrderByComparator<WorkflowLog> getLogUserIdComparator(
		boolean ascending) {

		return new WorkflowLogUserIdComparator(
			ascending, "userId ASC, kaleoLogId ASC",
			"userId DESC, kaleoLogId DESC",
			new String[] {"userId", "kaleoLogId"});
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskCompletionDateComparator(
		boolean ascending) {

		return new WorkflowTaskCompletionDateComparator(
			ascending, "completed ASC, completionDate ASC, kaleoTaskId ASC",
			"completed ASC, completionDate DESC, kaleoTaskId DESC",
			new String[] {"completed", "completionDate", "kaleoTaskId"});
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskCreateDateComparator(
		boolean ascending) {

		return new WorkflowTaskCreateDateComparator(
			ascending,
			"completed ASC, createDate ASC, kaleoTaskInstanceTokenId ASC",
			"completed ASC, createDate DESC, kaleoTaskInstanceTokenId DESC",
			new String[] {
				"completed", "createDate", "kaleoTaskInstanceTokenId"
			});
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskDueDateComparator(
		boolean ascending) {

		return new WorkflowTaskDueDateComparator(
			ascending,
			"completed ASC, dueDate ASC, modifiedDate ASC, kaleoTaskId ASC",
			"completed ASC, dueDate DESC, modifiedDate DESC, kaleoTaskId DESC",
			new String[] {
				"completed", "dueDate", "modifiedDate", "kaleoTaskId"
			});
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskInstanceIdComparator(
		boolean ascending) {

		return new WorkflowTaskInstanceIdComparator(
			ascending, "completed ASC, kaleoInstanceId ASC",
			"completed ASC, kaleoInstanceId DESC",
			new String[] {"completed", "kaleoInstanceId"});
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskModifiedDateComparator(
		boolean ascending) {

		return new WorkflowTaskModifiedDateComparator(
			ascending,
			"completed ASC, modifiedDate ASC, kaleoTaskInstanceTokenId ASC",
			"completed ASC, modifiedDate DESC, kaleoTaskInstanceTokenId DESC",
			new String[] {
				"completed", "modifiedDate", "kaleoTaskInstanceTokenId"
			});
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskNameComparator(
		boolean ascending) {

		return new WorkflowTaskNameComparator(
			ascending, "completed ASC, name ASC, kaleoTaskId ASC",
			"completed ASC, name DESC, kaleoTaskId DESC",
			new String[] {"completed", "name", "kaleoTaskId"});
	}

	@Override
	public OrderByComparator<WorkflowTask> getTaskUserIdComparator(
		boolean ascending) {

		return new WorkflowTaskUserIdComparator(
			ascending, "completed ASC, userId ASC, kaleoTaskId ASC",
			"completed ASC, userId DESC, kaleoTaskId DESC",
			new String[] {"completed", "userId", "kaleoTaskId"});
	}

}