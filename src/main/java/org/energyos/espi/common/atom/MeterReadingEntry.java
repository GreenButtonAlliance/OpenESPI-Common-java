/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.atom;

import org.energyos.espi.common.domain.MeterReading;

import com.sun.syndication.io.FeedException;

public class MeterReadingEntry extends EspiEntry<MeterReading> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MeterReadingEntry(MeterReading espiObject) throws FeedException {
        super(espiObject);

    }

    @Override
    protected String getSelfHref() {
        return "RetailCustomer/" + espiObject.getUsagePoint().getRetailCustomer().getId() +
                "/UsagePoint/" + espiObject.getUsagePoint().getId() +
                "/MeterReading/" + espiObject.getId();
    }

    @Override
    protected String getUpHref() {
        return "RetailCustomer/" + espiObject.getUsagePoint().getRetailCustomer().getId() +
                "/UsagePoint/" + espiObject.getUsagePoint().getId() +
                "/MeterReading";
    }

    protected void buildRelatedLinks() {
        if (espiObject.getReadingType() != null) {
            addRelatedLink("ReadingType/" + espiObject.getReadingType().getId());
        }

        addRelatedLink(getSelfLink().getHref() + "/IntervalBlock");
    }
}
