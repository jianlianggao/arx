/*
 * ARX: Powerful Data Anonymization
 * Copyright 2012 - 2016 Fabian Prasser, Florian Kohlmayer and contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deidentifier.arx.metric.v2;

import org.deidentifier.arx.metric.MetricConfiguration;


/**
 * This class provides an implementation of the non-uniform entropy
 * metric. TODO: Add reference
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public class MetricMDNUEntropyPotentiallyPrecomputed extends AbstractMetricMultiDimensionalPotentiallyPrecomputed {

    /** SVUID. */
    private static final long serialVersionUID = 7044684079235440871L;

    /**
     * Creates a new instance. The precomputed variant will be used if 
     * #distinctValues / #rows <= threshold for all quasi-identifiers.
     * 
     * @param threshold
     */
    protected MetricMDNUEntropyPotentiallyPrecomputed(double threshold) {
        super(new MetricMDNUEntropy(),
              new MetricMDNUEntropyPrecomputed(),
              threshold);
    }

    /**
     * Creates a new instance. The precomputed variant will be used if 
     * #distinctValues / #rows <= threshold for all quasi-identifiers.
     * 
     * @param threshold
     * @param gsFactor
     * @param function
     */
    protected MetricMDNUEntropyPotentiallyPrecomputed(double threshold,
                                                      double gsFactor,
                                                      AggregateFunction function) {
        super(new MetricMDNUEntropy(gsFactor, function),
              new MetricMDNUEntropyPrecomputed(gsFactor, function),
              threshold);
    }
    
    /**
     * Returns the configuration of this metric.
     *
     * @return
     */
    public MetricConfiguration getConfiguration() {
        return new MetricConfiguration(true, // monotonic
                                       super.getDefaultMetric().getGeneralizationSuppressionFactor(), // gs-factor
                                       super.isPrecomputed(), // precomputed
                                       super.getThreshold(), // precomputation threshold
                                       super.getDefaultMetric().getAggregateFunction() // aggregate function
        );
    }
    
    @Override
    public boolean isGSFactorSupported() {
        return true;
    }

    @Override
    public String toString() {
        return "Non-uniform entropy";
    }
}
