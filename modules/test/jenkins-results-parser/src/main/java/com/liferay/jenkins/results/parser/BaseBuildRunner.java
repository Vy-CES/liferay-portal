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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner<T extends BuildData, S extends Workspace>
	implements BuildRunner<T, S> {

	@Override
	public T getBuildData() {
		return _buildData;
	}

	@Override
	public S getWorkspace() {
		return _workspace;
	}

	@Override
	public void run() {
		updateBuildDescription();

		setUpWorkspace();
	}

	@Override
	public void setUp() {
	}

	@Override
	public void tearDown() {
		cleanUpHostServices();

		tearDownWorkspace();
	}

	protected BaseBuildRunner(T buildData) {
		_buildData = buildData;

		_job = JobFactory.newJob(_buildData);

		_job.readJobProperties();
	}

	protected void cleanUpHostServices() {
		Host host = _buildData.getHost();

		host.cleanUpServices();
	}

	protected Job getJob() {
		return _job;
	}

	protected abstract void initWorkspace();

	protected void keepLogs(boolean keepLogs) {
		StringBuilder sb = new StringBuilder();

		sb.append("def job = Jenkins.instance.getItemByFullName(\"");
		sb.append(_buildData.getJobName());
		sb.append("\"); ");

		sb.append("def build = job.getBuildByNumber(");
		sb.append(_buildData.getBuildNumber());
		sb.append("); ");

		sb.append("build.keepLog(");
		sb.append(keepLogs);
		sb.append(");");

		JenkinsResultsParserUtil.executeJenkinsScript(
			_buildData.getMasterHostname(), "script=" + sb.toString());
	}

	protected void publishToUserContentDir(File file) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		String userContentRelativePath =
			_buildData.getUserContentRelativePath();

		userContentRelativePath = userContentRelativePath.replace(")", "\\)");
		userContentRelativePath = userContentRelativePath.replace("(", "\\(");

		RemoteExecutor remoteExecutor = new RemoteExecutor();

		int returnCode = remoteExecutor.execute(
			1, new String[] {_buildData.getMasterHostname()},
			new String[] {
				"mkdir -p /opt/java/jenkins/userContent/" +
					userContentRelativePath
			});

		if (returnCode != 0) {
			throw new RuntimeException("Unable to create target directory");
		}

		int maxRetries = 3;
		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				String command = JenkinsResultsParserUtil.combine(
					"time rsync -Ipqrs --chmod=go=rx --timeout=1200 ",
					JenkinsResultsParserUtil.getCanonicalPath(file), " ",
					_buildData.getTopLevelMasterHostname(), "::usercontent/",
					userContentRelativePath);

				JenkinsResultsParserUtil.executeBashCommands(command);

				break;
			}
			catch (IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to send " + file.getName(), e);
				}

				System.out.println(
					"Unable to execute bash commands, retrying... ");

				e.printStackTrace();

				JenkinsResultsParserUtil.sleep(3000);
			}
		}
	}

	protected void setUpWorkspace() {
		if (_workspace == null) {
			initWorkspace();
		}

		_workspace.setBuildData(getBuildData());

		_workspace.setJob(getJob());

		_workspace.setUp();
	}

	protected void setWorkspace(S workspace) {
		_workspace = workspace;
	}

	protected void tearDownWorkspace() {
		if (_workspace == null) {
			initWorkspace();
		}

		_workspace.tearDown();
	}

	protected void updateBuildDescription() {
		JenkinsResultsParserUtil.updateBuildDescription(
			_buildData.getBuildURL(), _buildData.getBuildDescription());
	}

	private final T _buildData;
	private final Job _job;
	private S _workspace;

}