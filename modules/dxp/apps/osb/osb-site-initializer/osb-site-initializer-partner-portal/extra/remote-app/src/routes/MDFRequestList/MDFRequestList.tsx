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

import ClayButton from '@clayui/button';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';

import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import {MDFRequestListItem} from '../../common/interfaces/mdfRequestListItem';
import {Liferay} from '../../common/services/liferay';
import Table from './components/Table';
import useGetMDFListColumns from './hooks/useGetMDFListColumns';
import useGetMDFRequestListData from './hooks/useGetMDFRequestListData';
import usePagination from './hooks/usePagination';

const MDFRequestList = () => {
	const pagination = usePagination();
	const {data} = useGetMDFRequestListData(
		pagination.activePage,
		pagination.activeDelta
	);

	const siteURL = useLiferayNavigate();

	const columns = useGetMDFListColumns(data.listColumns, siteURL);

	return (
		<div className="border-0 pb-3 pt-5 px-6 sheet">
			<h1>MDF Requests</h1>

			<div className="bg-neutral-1 d-flex justify-content-end p-3 rounded">
				<ClayButton
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/${PRMPageRoute.CREATE_MDF_REQUEST}`
						)
					}
				>
					New Request
				</ClayButton>
			</div>

			{!!data.listItems?.items && data.listItems?.totalCount && columns && (
				<div className="mt-3">
					<Table<MDFRequestListItem>
						borderless
						columns={columns}
						responsive
						rows={data.listItems.items}
					/>

					<ClayPaginationBarWithBasicItems
						{...pagination}
						totalItems={data.listItems.totalCount}
					/>
				</div>
			)}
		</div>
	);
};
export default MDFRequestList;
