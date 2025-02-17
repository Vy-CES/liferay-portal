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

package com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.sort.ElasticsearchSortFieldTranslatorFixture;

/**
 * @author Michael C. Han
 */
public class ElasticsearchPipelineAggregationVisitorFixture {

	public ElasticsearchPipelineAggregationVisitorFixture() {
		ElasticsearchPipelineAggregationVisitor
			elasticsearchPipelineAggregationVisitor =
				new ElasticsearchPipelineAggregationVisitor();

		_injectSortFieldTranslators(elasticsearchPipelineAggregationVisitor);

		_elasticsearchPipelineAggregationVisitor =
			elasticsearchPipelineAggregationVisitor;
	}

	public ElasticsearchPipelineAggregationVisitor
		getElasticsearchPipelineAggregationVisitor() {

		return _elasticsearchPipelineAggregationVisitor;
	}

	private void _injectSortFieldTranslators(
		ElasticsearchPipelineAggregationVisitor
			elasticsearchPipelineAggregationVisitor) {

		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture =
				new ElasticsearchQueryTranslatorFixture();

		ElasticsearchSortFieldTranslatorFixture
			elasticsearchSortFieldTranslatorFixture =
				new ElasticsearchSortFieldTranslatorFixture(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchPipelineAggregationVisitor, "_sortFieldTranslator",
			elasticsearchSortFieldTranslatorFixture.
				getElasticsearchSortFieldTranslator());
	}

	private final ElasticsearchPipelineAggregationVisitor
		_elasticsearchPipelineAggregationVisitor;

}