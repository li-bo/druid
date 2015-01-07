/*
 * Druid - a distributed column store.
 * Copyright (C) 2012, 2013  Metamarkets Group Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package io.druid.query.aggregation.histogram;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import io.druid.initialization.AbstractDruidModule;
import io.druid.segment.serde.ComplexMetrics;

import java.util.List;

/**
 */
public class ApproximateHistogramDruidModule extends AbstractDruidModule
{
  @Override
  public List<? extends Module> getJacksonModules()
  {
    return ImmutableList.of(
        new SimpleModule().registerSubtypes(
            ApproximateHistogramFoldingAggregatorFactory.class,
            ApproximateHistogramAggregatorFactory.class,
            EqualBucketsPostAggregator.class,
            CustomBucketsPostAggregator.class,
            BucketsPostAggregator.class,
            QuantilesPostAggregator.class,
            QuantilePostAggregator.class,
            MinPostAggregator.class,
            MaxPostAggregator.class
        )
    );
  }

  @Override
  public void configure(Binder binder)
  {
    if (ComplexMetrics.getSerdeForType("approximateHistogram") == null) {
      ComplexMetrics.registerSerde("approximateHistogram", new ApproximateHistogramFoldingSerde());
    }
  }
}
