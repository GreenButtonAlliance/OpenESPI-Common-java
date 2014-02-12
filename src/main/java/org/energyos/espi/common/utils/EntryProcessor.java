/*
 * Copyright 2013, 2014 EnergyOS.org
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

package org.energyos.espi.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ResourceService;
import org.springframework.dao.EmptyResultDataAccessException;

public class EntryProcessor {
    private ResourceLinker resourceLinker;
    private ResourceConverter resourceConverter;
    private ResourceService resourceService;

    public EntryProcessor() {
        this.resourceLinker = new ResourceLinker();
        this.resourceConverter = new ResourceConverter();
    }

    public EntryProcessor(ResourceLinker resourceLinker, ResourceConverter resourceConverter, ResourceService resourceService) {
        this.resourceLinker = resourceLinker;
        this.resourceConverter = resourceConverter;
        this.resourceService = resourceService;
    }

    public EntryType process(EntryType entry) {
        convert(entry);
        for(IdentifiedObject resource : entry.getContent().getResources()) {
            try {
                IdentifiedObject existingResource = resourceService.findByUUID(resource.getUUID(), resource.getClass());
               	
                System.out.printf("**** Importing an Existing Resource: %s\n", resource.getUUID());
                // merge the new into the old and throw the new away
                //
                existingResource.merge(resource);

                // now put the existing resource back into the structure so it will 
                // be available for later processing
                
                if (existingResource instanceof UsagePoint) {
                    entry.getContent().setUsagePoint((UsagePoint) existingResource);
                }
                
                if (existingResource instanceof MeterReading) {
                	entry.getContent().setMeterReading((MeterReading) existingResource);
                }
                
                if (existingResource instanceof IntervalBlock) {
                   	System.out.printf("**** Importing an Existing IntervalBlock: %s\n", resource.getUUID());
                    List <IntervalBlock> intervalBlocks = entry.getContent().getIntervalBlocks();
                    List <IntervalBlock> newList = new ArrayList<IntervalBlock> ();
                    Iterator<IntervalBlock> blocks = intervalBlocks.iterator();
                    while (blocks.hasNext()) {
                       IntervalBlock bl = blocks.next();
                       if (bl.getUUID().equals(existingResource.getUUID())) {
                    	   existingResource.merge(bl);
                    	   // must persist this list to get the IntervalReadings in place
                    	   resourceService.persist(existingResource);;
                    	   newList.add((IntervalBlock) existingResource);                           
                       } else {
                    	   newList.add(bl);
                       }
                    }
                }
                
                if (existingResource instanceof ReadingType) {              
                	System.out.printf("**** Importing an Existing ReadingType: %s\n", resource.getUUID()); 
                	entry.getContent().setReadingType((ReadingType) existingResource); 
                }
                
                if (existingResource instanceof TimeConfiguration) {
                	System.out.printf("**** Importing an Existing TimeConfiguration: %s\n", resource.getUUID());
                    entry.getContent().setLocalTimeParameters((TimeConfiguration) existingResource);
                }
                
                if (existingResource instanceof ElectricPowerUsageSummary) {
                	System.out.printf("**** Importing an Existing ElectricPowerUsageSummary: %s\n", resource.getUUID());                	
                	entry.getContent().setElectricPowerUsageSummary((ElectricPowerUsageSummary) existingResource);               
                }
                
                if (existingResource instanceof ElectricPowerQualitySummary) {
                	System.out.printf("**** Importing an Existing ElectricPowerQualitySummary: %s\n", resource.getUUID());                	
                	entry.getContent().setElectricPowerQualitySummary((ElectricPowerQualitySummary) existingResource);               
                }
                
                if (existingResource instanceof ApplicationInformation) {
                	System.out.printf("**** Importing an Existing ApplicationInformation: %s\n", resource.getUUID());                	
                	entry.getContent().setApplicationInformation((ApplicationInformation) existingResource);               
                }
                
                link(existingResource);
                
            } catch (EmptyResultDataAccessException x) {
            	System.out.printf("****Exception 007: %s\n", x.toString());
                link(resource);
            }
        }
        return entry;
    }

    public void link(IdentifiedObject resources) {
        resourceLinker.link(resources);
    }

    public void convert(EntryType entry) {
        resourceConverter.convert(entry);
    }
}
