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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import {
	fetch,
	getOpener,
	objectToFormData,
	openConfirmModal,
	sub,
} from 'frontend-js-web';
import React, {useCallback, useRef, useState} from 'react';

import CollaboratorRow from './components/manage-collaborators-modal/CollaboratorRow';
import SharingAutocomplete from './components/manage-collaborators-modal/SharingAutocomplete';

const ManageCollaborators = ({
	autocompleteUserURL,
	getCollaboratorsURL,
	inviteUsersURL,
	isPublicationTemplate,
	namespace,
	readOnly,
	roles,
	setCollaboratorData,
	setShowModal,
	showModal,
	spritemap,
	trigger,
	verifyEmailAddressURL,
}) => {
	const [active, setActive] = useState(false);
	const [emailAddressErrorMessages, setEmailAddressErrorMessages] = useState(
		[]
	);
	const [multiSelectValue, setMultiSelectValue] = useState('');
	const [selectedItems, setSelectedItems] = useState({});
	const [selectedUserData, setSelectedUserData] = useState({});
	const [updatedRoles, setUpdatedRoles] = useState({});

	let defaultRole = roles[0];

	for (let i = 0; i < roles.length; i++) {
		if (roles[i].default) {
			defaultRole = roles[i];

			break;
		}
	}

	const [selectedRole, setSelectedRole] = useState(defaultRole);

	const handleChange = useCallback((value) => {
		if (!emailValidationInProgressRef.current) {
			setMultiSelectValue(value);
		}
	}, []);

	const {
		refetch: collaboratorsRefetch,
		resource: collaboratorsResource,
	} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: getCollaboratorsURL,
	});

	const collaborators = collaboratorsResource;

	const handleItemsChange = useCallback(
		(items) => {
			emailValidationInProgressRef.current = true;

			Promise.all(
				items.map((item) => {
					if (item.userId) {
						return Promise.resolve({item});
					}

					if (!isEmailAddressValid(item.value)) {
						return Promise.resolve({
							error: sub(
								Liferay.Language.get(
									'x-is-not-a-valid-email-address'
								),
								item.value
							),
							item,
						});
					}

					return fetch(verifyEmailAddressURL, {
						body: objectToFormData({
							[`${namespace}emailAddress`]: item.value,
						}),
						method: 'POST',
					})
						.then((response) => response.json())
						.then(({errorMessage, user}) => {
							if (errorMessage) {
								return {
									error: errorMessage,
									item,
								};
							}
							else if (user) {
								return {
									item: {
										emailAddress: user.emailAddress,
										fullName: user.fullName,
										hasPublicationsAccess:
											user.hasPublicationsAccess,
										label: item.label,
										userId: user.userId,
										value: item.value,
									},
								};
							}

							return {
								error: sub(
									Liferay.Language.get(
										'user-x-does-not-exist'
									),
									item.value
								),
								item,
							};
						});
				})
			).then((results) => {
				emailValidationInProgressRef.current = false;

				const erroredResults = results.filter(({error}) => !!error);

				setEmailAddressErrorMessages(
					erroredResults.map(({error}) => error)
				);

				if (!erroredResults.length) {
					setMultiSelectValue('');
				}

				if (erroredResults.length === 1) {
					setMultiSelectValue(erroredResults[0].item.value);
				}

				const newSelectedItems = JSON.parse(
					JSON.stringify(selectedItems)
				);
				const newSelectedUserData = JSON.parse(
					JSON.stringify(selectedUserData)
				);
				const newUpdatedRoles = JSON.parse(
					JSON.stringify(updatedRoles)
				);

				results
					.filter(({error}) => !error)
					.map(({item}) => {
						if (
							collaborators &&
							!!collaborators.find(
								(collaborator) =>
									collaborator.emailAddress ===
									item.emailAddress
							)
						) {
							newUpdatedRoles[
								item.userId.toString()
							] = selectedRole;

							return;
						}

						const user = JSON.parse(JSON.stringify(item));

						user.new = true;

						newSelectedItems[user.userId.toString()] = selectedRole;
						newSelectedUserData[user.userId.toString()] = user;
					});

				setSelectedUserData(newSelectedUserData);

				setSelectedItems(newSelectedItems);
				setUpdatedRoles(newUpdatedRoles);
			});
		},
		[
			collaborators,
			namespace,
			selectedItems,
			selectedRole,
			selectedUserData,
			updatedRoles,
			verifyEmailAddressURL,
		]
	);

	const {observer, onClose} = useModal({
		onClose: () => setShowModal(false),
	});

	const {resource: autocompleteResource} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: autocompleteUserURL,
		variables: {
			[`${namespace}keywords`]: multiSelectValue,
		},
	});

	const autocompleteUsers = autocompleteResource;

	const emailValidationInProgressRef = useRef(false);

	const isEmailAddressValid = (email) => {
		const emailRegex = /.+@.+\..+/i;

		return emailRegex.test(email);
	};

	const resetForm = () => {
		setEmailAddressErrorMessages([]);
		setMultiSelectValue('');
		setSelectedItems({});
		setSelectedRole(defaultRole);
		setSelectedUserData({});
		setUpdatedRoles({});

		emailValidationInProgressRef.current = false;
	};

	const showNotification = (message, error) => {
		const parentOpenToast = getOpener().Liferay.Util.openToast;

		const openToastParams = {message};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		collaboratorsRefetch();
		onClose();
		parentOpenToast(openToastParams);
		resetForm();
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		const publicationsUserRoleUserIds = [];
		const publicationsUserRoleEmailAddresses = [];
		const roleValues = [];
		const userIds = [];

		const selectedItemsKeys = Object.keys(selectedItems);

		for (let i = 0; i < selectedItemsKeys.length; i++) {
			const user = selectedUserData[selectedItemsKeys[i]];

			if (!user.hasPublicationsAccess) {
				publicationsUserRoleEmailAddresses.push(user.emailAddress);
				publicationsUserRoleUserIds.push(user.userId);
			}

			roleValues.push(selectedItems[selectedItemsKeys[i]].value);
			userIds.push(selectedItemsKeys[i]);
		}

		const langKey =
			publicationsUserRoleUserIds.length > 1
				? Liferay.Language.get(
						'you-are-inviting-users-x-who-do-not-have-access-to-publications'
				  )
				: Liferay.Language.get(
						'you-are-inviting-user-x-who-does-not-have-access-to-publications'
				  );

		if (publicationsUserRoleUserIds.length) {
			openConfirmModal({
				message: sub(
					langKey,
					publicationsUserRoleEmailAddresses.join(', ')
				),
				onConfirm: (isConfirmed) => {
					if (publicationsUserRoleUserIds.length && isConfirmed) {
						sendInvite(
							publicationsUserRoleUserIds,
							roleValues,
							userIds
						);
					}
				},
			});
		}
		else {
			sendInvite(publicationsUserRoleUserIds, roleValues, userIds);
		}
	};

	const sendInvite = (publicationsUserRoleUserIds, roleValues, userIds) => {
		const updatedRolesKeys = Object.keys(updatedRoles);

		for (let i = 0; i < updatedRolesKeys.length; i++) {
			roleValues.push(updatedRoles[updatedRolesKeys[i]].value);
			userIds.push(updatedRolesKeys[i]);
		}

		if (isPublicationTemplate) {
			setCollaboratorData({
				[`publicationsUserRoleUserIds`]: publicationsUserRoleUserIds.join(
					','
				),
				[`roleValues`]: roleValues.join(','),
				[`userIds`]: userIds.join(','),
			});

			showNotification(
				'Selected Users have been associated with the template'
			);
		}
		else {
			const formData = {
				[`${namespace}publicationsUserRoleUserIds`]: publicationsUserRoleUserIds.join(
					','
				),
				[`${namespace}roleValues`]: roleValues.join(','),
				[`${namespace}userIds`]: userIds.join(','),
			};

			fetch(inviteUsersURL, {
				body: objectToFormData(formData),
				method: 'POST',
			})
				.then((response) => response.json())
				.then(({errorMessage, successMessage}) => {
					if (errorMessage) {
						showNotification(errorMessage, true);

						return;
					}

					showNotification(successMessage);
				})
				.catch((error) => {
					showNotification(error.message, true);
				});
		}
	};

	const updateRole = (role, user) => {
		if (user.new) {
			const json = {};

			const keys = Object.keys(selectedItems);

			for (let i = 0; i < keys.length; i++) {
				if (keys[i] !== user.userId.toString()) {
					json[keys[i]] = selectedItems[keys[i]];
				}
			}

			if (role.value >= 0) {
				json[user.userId.toString()] = role;
			}

			setSelectedItems(json);

			return;
		}

		const json = {};

		const keys = Object.keys(updatedRoles);

		for (let i = 0; i < keys.length; i++) {
			if (keys[i] !== user.userId.toString()) {
				json[keys[i]] = updatedRoles[keys[i]];
			}
		}

		let savedRoleValue = null;

		if (collaborators) {
			for (let i = 0; i < collaborators.length; i++) {
				if (collaborators[i].userId === user.userId) {
					savedRoleValue = collaborators[i].roleValue;

					break;
				}
			}
		}

		if (
			savedRoleValue !== role.value ||
			!Object.prototype.hasOwnProperty.call(
				updatedRoles,
				user.userId.toString()
			)
		) {
			json[user.userId.toString()] = role;
		}

		setUpdatedRoles(json);
	};

	const renderCollaborators = () => {
		let users = [];

		if (collaborators && !!collaborators.length) {
			users = collaborators.slice(0);
		}

		const keys = Object.keys(selectedItems);

		for (let i = 0; i < keys.length; i++) {
			users.push(selectedUserData[keys[i]]);
		}

		if (!users.length) {
			return '';
		}

		return (
			<ClayForm.Group>
				<ClayTable hover={false}>
					<ClayTable.Body>
						{users
							.sort((a, b) => {
								const aIsUpdated =
									Object.prototype.hasOwnProperty.call(
										selectedItems,
										a.userId.toString()
									) ||
									Object.prototype.hasOwnProperty.call(
										updatedRoles,
										a.userId.toString()
									);
								const bIsUpdated =
									Object.prototype.hasOwnProperty.call(
										selectedItems,
										b.userId.toString()
									) ||
									Object.prototype.hasOwnProperty.call(
										updatedRoles,
										b.userId.toString()
									);

								if (aIsUpdated && !bIsUpdated) {
									return -1;
								}
								else if (!aIsUpdated && bIsUpdated) {
									return 1;
								}

								if (a.isOwner) {
									return -1;
								}
								else if (b.isOwner) {
									return 1;
								}

								if (a.isCurrentUser) {
									return -1;
								}
								else if (b.isCurrentUser) {
									return 1;
								}

								if (a.emailAddress < b.emailAddress) {
									return -1;
								}

								return 1;
							})
							.map((user) => (
								<CollaboratorRow
									handleSelect={(role) =>
										updateRole(role, user)
									}
									key={user.userId}
									readOnly={readOnly}
									roles={roles}
									selectedItems={selectedItems}
									spritemap={spritemap}
									updatedRoles={updatedRoles}
									user={user}
								/>
							))}
					</ClayTable.Body>
				</ClayTable>
			</ClayForm.Group>
		);
	};

	const renderSelect = () => {
		if (readOnly) {
			return '';
		}

		const dropdownItems = [];

		for (let i = 0; i < roles.length; i++) {
			dropdownItems.push({
				description: roles[i].shortDescription,
				label: roles[i].label,
				onClick: () => {
					setActive(false);
					setSelectedRole(roles[i]);
				},
				symbolLeft:
					selectedRole.value === roles[i].value ? 'check' : '',
			});
		}

		return (
			<ClayForm.Group
				className={emailAddressErrorMessages.length ? 'has-error' : ''}
			>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<label htmlFor={`${namespace}userEmailAddress`}>
							{Liferay.Language.get('people')}
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayMultiSelect
									inputName={`${namespace}userEmailAddress`}
									items={[]}
									menuRenderer={SharingAutocomplete}
									onChange={handleChange}
									onItemsChange={handleItemsChange}
									placeholder={Liferay.Language.get(
										'enter-name-or-email-address'
									)}
									sourceItems={
										multiSelectValue && autocompleteUsers
											? autocompleteUsers.map((user) => {
													return {
														emailAddress:
															user.emailAddress,
														fullName: user.fullName,
														hasPublicationsAccess:
															user.hasPublicationsAccess,
														isOwner: user.isOwner,
														label: user.fullName,
														portraitURL:
															user.portraitURL,
														userId: user.userId,
														value:
															user.emailAddress,
													};
											  })
											: []
									}
									spritemap={spritemap}
									value={multiSelectValue}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<ClayDropDown
									active={active}
									alignmentPosition={Align.BottomLeft}
									hasLeftSymbols={true}
									menuWidth="sm"
									onActiveChange={setActive}
									spritemap={spritemap}
									trigger={
										<ClayButton
											data-tooltip-align="top"
											displayType="secondary"
											title={selectedRole.longDescription}
										>
											{selectedRole.label}

											<span className="inline-item inline-item-after">
												<ClayIcon
													spritemap={spritemap}
													symbol="caret-bottom"
												/>
											</span>
										</ClayButton>
									}
								>
									<ClayDropDown.ItemList>
										<ClayDropDown.Group>
											{dropdownItems.map((item, i) => (
												<ClayDropDown.Item
													key={i}
													onClick={item.onClick}
													symbolLeft={item.symbolLeft}
												>
													<strong>
														{item.label}
													</strong>

													<div>
														{item.description}
													</div>
												</ClayDropDown.Item>
											))}
										</ClayDropDown.Group>
									</ClayDropDown.ItemList>
								</ClayDropDown>
							</ClayInput.GroupItem>
						</ClayInput.Group>

						{!!emailAddressErrorMessages.length && (
							<ClayForm.FeedbackGroup>
								{emailAddressErrorMessages.map(
									(emailAddressErrorMessage) => (
										<ClayForm.FeedbackItem
											key={emailAddressErrorMessage}
										>
											{emailAddressErrorMessage}
										</ClayForm.FeedbackItem>
									)
								)}
							</ClayForm.FeedbackGroup>
						)}
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		);
	};

	const renderSubmit = () => {
		return (
			<ClayButton
				disabled={
					!Object.keys(selectedItems).length &&
					!Object.keys(updatedRoles).length
				}
				displayType="primary"
				type="submit"
			>
				{!Object.keys(updatedRoles).length
					? Liferay.Language.get('send')
					: Liferay.Language.get('save')}
			</ClayButton>
		);
	};

	const renderModal = () => {
		if (!showModal) {
			return '';
		}

		return (
			<ClayModal
				className="publications-invite-users-modal"
				observer={observer}
				size="lg"
				spritemap={spritemap}
			>
				<ClayForm onSubmit={handleSubmit}>
					<ClayModal.Header>
						<div className="autofit-row">
							<div className="autofit-col">
								<ClaySticker
									className="sticker-use-icon user-icon-color-0"
									displayType="secondary"
									shape="circle"
								>
									<ClayIcon symbol="users" />
								</ClaySticker>
							</div>

							<div className="autofit-col">
								<div className="modal-title">
									{readOnly
										? Liferay.Language.get(
												'view-collaborators'
										  )
										: Liferay.Language.get('invite-users')}
								</div>
							</div>
						</div>
					</ClayModal.Header>

					<div className="inline-scroller modal-body publications-invite-users-modal-body">
						{renderSelect()}

						{renderCollaborators()}
					</div>

					{readOnly || (
						<ClayModal.Footer
							last={
								<ClayButton.Group spaced>
									<ClayButton
										displayType="secondary"
										onClick={() => {
											if (
												Object.keys(selectedItems) ===
													0 &&
												Object.keys(updatedRoles) === 0
											) {
												onClose();
												resetForm();
											}
											else {
												openConfirmModal({
													message: Liferay.Language.get(
														'discard-unsaved-changes'
													),
													onConfirm: (
														isConfirmed
													) => {
														if (isConfirmed) {
															onClose();
															resetForm();
														}
													},
												});
											}
										}}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>

									{renderSubmit()}
								</ClayButton.Group>
							}
						/>
					)}
				</ClayForm>
			</ClayModal>
		);
	};

	const renderTrigger = () => {
		if (trigger) {
			return trigger;
		}

		if (!collaborators || !collaborators.length) {
			return (
				<ClayButtonWithIcon
					className="rounded-circle"
					data-tooltip-align="top"
					displayType="secondary"
					onClick={() => setShowModal(true)}
					small
					symbol="plus"
					title={Liferay.Language.get('invite-users')}
				/>
			);
		}

		const columns = [];

		if (!readOnly) {
			columns.push(
				<div className="autofit-col">
					<ClaySticker
						className="sticker-user-icon user-icon-color-0"
						data-tooltip-align="top"
						size="md"
						title={Liferay.Language.get('invite-users')}
					>
						<ClayIcon symbol="plus" />
					</ClaySticker>
				</div>
			);
		}

		const users = collaborators.sort((a, b) => {
			if (a.isOwner) {
				return -1;
			}
			else if (b.isOwner) {
				return 1;
			}

			if (a.isCurrentUser) {
				return -1;
			}
			else if (b.isCurrentUser) {
				return 1;
			}

			if (a.emailAddress < b.emailAddress) {
				return -1;
			}

			return 1;
		});

		for (let i = 0; i < 3 && i < users.length; i++) {
			const user = users[i];

			columns.push(
				<div className="autofit-col">
					<ClaySticker
						className={`sticker-user-icon ${
							user.portraitURL
								? ''
								: 'user-icon-color-' + (user.userId % 10)
						}`}
						data-tooltip-align="top"
						size="md"
						title={user.fullName}
					>
						{user.portraitURL ? (
							<div className="sticker-overlay">
								<img
									className="sticker-img"
									src={user.portraitURL}
								/>
							</div>
						) : (
							<ClayIcon symbol="user" />
						)}
					</ClaySticker>
				</div>
			);
		}

		if (!users.length) {
			columns.push(
				<div className="autofit-col">
					<ClaySticker
						className="sticker-user-icon user-icon-color-0"
						data-tooltip-align="top"
						size="md"
						title={
							readOnly
								? Liferay.Language.get('view-collaborators')
								: Liferay.Language.get('invite-users')
						}
					>
						<ClayIcon symbol="users" />
					</ClaySticker>
				</div>
			);
		}
		else if (users.length > 3) {
			columns.push(
				<div className="autofit-col">
					<ClaySticker
						className="btn-secondary"
						data-tooltip-align="top"
						size="md"
						title={
							readOnly
								? Liferay.Language.get('view-collaborators')
								: Liferay.Language.get('invite-users')
						}
					>
						{'+' + (users.length - 3)}
					</ClaySticker>
				</div>
			);
		}

		return (
			<ClayButton
				displayType="unstyled"
				onClick={() => setShowModal(true)}
			>
				<div className="autofit-row">{columns}</div>
			</ClayButton>
		);
	};

	return (
		<>
			{renderModal()}
			{renderTrigger()}
		</>
	);
};

const ManageCollaboratorsWithStateHook = ({...props}) => {
	const [showModal, setShowModal] = useState(false);

	return (
		<ManageCollaborators
			setShowModal={setShowModal}
			showModal={showModal}
			{...props}
		/>
	);
};

export default ManageCollaboratorsWithStateHook;
export {ManageCollaborators, ManageCollaboratorsWithStateHook};
