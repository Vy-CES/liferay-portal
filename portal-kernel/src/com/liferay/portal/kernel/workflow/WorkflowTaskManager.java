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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
@MessagingProxy(mode = ProxyMode.SYNC)
@ProviderType
public interface WorkflowTaskManager {

	public WorkflowTask assignWorkflowTaskToRole(
			long companyId, long userId, long workflowTaskId, long roleId,
			String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws WorkflowException;

	public WorkflowTask assignWorkflowTaskToUser(
			long companyId, long userId, long workflowTaskId,
			long assigneeUserId, String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			String transitionName, String comment,
			Map<String, Serializable> workflowContext)
		throws WorkflowException;

	public WorkflowTask fetchWorkflowTask(long companyId, long workflowTaskId)
		throws WorkflowException;

	public List<String> getNextTransitionNames(
			long companyId, long userId, long workflowTaskId)
		throws WorkflowException;

	public default List<User> getPooledActors(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public long[] getPooledActorsIds(long companyId, long workflowTaskId)
		throws WorkflowException;

	public WorkflowTask getWorkflowTask(long companyId, long workflowTaskId)
		throws WorkflowException;

	public int getWorkflowTaskCount(long companyId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByRole(
			long companyId, long roleId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountBySubmittingUser(
			long companyId, long userId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByUser(
			long companyId, long userId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByUserRoles(
			long companyId, long userId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByUserRoles(
			long companyId, long userId, long workflowInstanceId,
			Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByWorkflowInstance(
			long companyId, Long userId, long workflowInstanceId,
			Boolean completed)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasks(
			long companyId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByRole(
			long companyId, long roleId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksBySubmittingUser(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByUser(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByUserRoles(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByWorkflowInstance(
			long companyId, Long userId, long workflowInstanceId,
			Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public boolean hasOtherAssignees(long workflowTaskId, long userId)
		throws WorkflowException;

	public List<WorkflowTask> search(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> search(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKeys, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator,
			int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public default List<WorkflowTask> search(
			long companyId, long userId, String assetTitle, String taskName,
			String[] assetTypes, Long[] assetPrimaryKeys, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Boolean andOperator, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowTask> search(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException;

	public int searchCount(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles)
		throws WorkflowException;

	public int searchCount(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKeys, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator)
		throws WorkflowException;

	public default int searchCount(
			long companyId, long userId, String assetTitle, String taskName,
			String[] assetTypes, Long[] assetPrimaryKeys, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Boolean andOperator)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public int searchCount(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles)
		throws WorkflowException;

	public WorkflowTask updateDueDate(
			long companyId, long userId, long workflowTaskId, String comment,
			Date dueDate)
		throws WorkflowException;

}