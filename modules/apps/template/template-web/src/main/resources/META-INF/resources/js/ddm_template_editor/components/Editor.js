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

import {openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from './AppContext';
import {CodeMirrorEditor} from './CodeMirrorEditor';

export function Editor({autocompleteData, initialScript, mode}) {
	const {inputChannel, portletNamespace} = useContext(AppContext);

	const [script, setScript] = useState(initialScript);

	const scriptRef = useRef(script);
	scriptRef.current = script;

	useEffect(() => {
		const refreshHandler = Liferay.on(
			`${portletNamespace}refreshEditor`,
			() => {
				const formElement = document.getElementById(
					`${portletNamespace}fm`
				);

				if (!formElement) {
					return;
				}

				if (scriptRef.current === initialScript) {
					setScript('');
				}

				Liferay.fire(`${portletNamespace}saveTemplate`);

				requestAnimationFrame(() => {
					formElement.action = window.location.href;
					formElement.submit();
				});
			}
		);

		return () => {
			refreshHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	useEffect(() => {
		const scriptImportedHandler = Liferay.on(
			`${portletNamespace}scriptImported`,
			(event) => {
				setScript(event.script);

				openToast({
					message: sub(
						Liferay.Language.get('x-imported'),
						event.fileName
					),
					title: Liferay.Language.get('success'),
					type: 'success',
				});
			}
		);

		return () => {
			scriptImportedHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	useEffect(() => {
		const exportScriptHandler = Liferay.on(
			`${portletNamespace}exportScript`,
			() => {
				exportScript(scriptRef.current, 'ftl');
			}
		);

		return () => {
			exportScriptHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	function base64Encode(input) {

		// btoa can't be used directly if the input exceeds the ascii
		// charset. The Liferay backend expectes the scriptContent to be the
		// base64 encoded UTF-8 representation of the input. encodeURI
		// is used to map high codepoints to their UTF-8 escape sequences
		// in the form %AB%CD..., where each AB/CD/.. pair represents one
		// octed of the UTF-8 representation. The ecapes are then mapped
		// to their individual codepoints.
		// The result is then run through btoa.

		const utf8EncodedInput = encodeURI(input).replaceAll(
			/%[A-Fa-f0-9]{2}/g,
			(escapeSequence) => {
				const escapeContent = escapeSequence.substring(1);
				const escapeCode = parseInt(escapeContent, 16);

				return String.fromCharCode(escapeCode);
			}
		);

		return btoa(utf8EncodedInput);
	}

	return (
		<>
			<CodeMirrorEditor
				autocompleteData={autocompleteData}
				content={script}
				inputChannel={inputChannel}
				mode={mode}
				onChange={setScript}
			/>

			<input
				id={`${portletNamespace}scriptContent`}
				name={`${portletNamespace}scriptContent`}
				type="hidden"
				value={base64Encode(script)}
			/>
		</>
	);
}

Editor.propTypes = {
	autocompleteData: PropTypes.object.isRequired,
	initialScript: PropTypes.string.isRequired,
	mode: PropTypes.object,
};

const exportScript = (script) => {
	const link = document.createElement('a');
	const blob = new Blob([script]);

	const fileURL = URL.createObjectURL(blob);

	link.href = fileURL;
	link.download = 'script.ftl';

	link.click();

	URL.revokeObjectURL(fileURL);
};
