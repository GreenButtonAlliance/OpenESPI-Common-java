/*
22 * Copyright 2013, 2014 EnergyOS.org
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

package org.energyos.espi.common.service.impl;

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
import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.service.EntryProcessorService;
import org.energyos.espi.common.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional (rollbackFor = {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
@Service

public class EntryProcessorServiceImpl implements EntryProcessorService {
	
    
    @Autowired
    private ResourceService resourceService;
    
 public EntryType process(EntryType entry) {
     convert(entry);
     for(IdentifiedObject resource : entry.getContent().getResources()) {

            try {

                IdentifiedObject existingResource = resourceService.findByUUID(resource.getUUID(), resource.getClass());
                existingResource = resourceService.merge(existingResource);
                
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
               	    // System.out.printf("*****We have an existing IntervalBlock??: %s\n", existingResource.toString());
                    List <IntervalBlock> intervalBlocks = entry.getContent().getIntervalBlocks();
                    List <IntervalBlock> newList = new ArrayList<IntervalBlock> ();
                    Iterator<IntervalBlock> blocks = intervalBlocks.iterator();
                    while (blocks.hasNext()) {
                       IntervalBlock bl = blocks.next();
                       if (bl.getUUID().equals(existingResource.getUUID())) {
                    	   existingResource.merge(bl);
                    	   newList.add((IntervalBlock) existingResource);                           
                       } else {
                    	   newList.add(bl);
                       }
                    }
                }

                if (existingResource instanceof ReadingType) {              
                	
                	entry.getContent().setReadingType((ReadingType) existingResource); 
                }

                if (existingResource instanceof TimeConfiguration) {
                	
                    entry.getContent().setLocalTimeParameters((TimeConfiguration) existingResource);
                }
                
                if (existingResource instanceof ElectricPowerUsageSummary) {
                          	
                	entry.getContent().setElectricPowerUsageSummary((ElectricPowerUsageSummary) existingResource);               
                }
                
                if (existingResource instanceof ElectricPowerQualitySummary) {
                       	
                	entry.getContent().setElectricPowerQualitySummary((ElectricPowerQualitySummary) existingResource);               
                }
                
                if (existingResource instanceof ApplicationInformation) {
                 	
                	entry.getContent().setApplicationInformation((ApplicationInformation) existingResource);               
                }

                link(existingResource);
                
            } catch (EmptyResultDataAccessException x) {
            	resourceService.persist(resource);
                link(resource);
            }

        }

       return entry;
    }

    // the private functions that do the work
    // 
    private void link(IdentifiedObject resource) {
        linkUp(resource);
        linkUpMember(resource);
        linkRelatedCollection(resource);
    }

    // Copy the entry attributes into the resource attributes 
    //
    public void convert(EntryType entry) {
        for(IdentifiedObject resource : entry.getContent().getResources()) {
            resource.setMRID(entry.getId());
            for(LinkType link : entry.getLinks()) {
                if (link.getRel().equals(LinkType.SELF))
                    resource.setSelfLink(link);
                if (link.getRel().equals(LinkType.UP))
                    resource.setUpLink(link);
                if (link.getRel().equals(LinkType.RELATED))
                    resource.getRelatedLinks().add(link);
            }
            resource.setDescription(entry.getTitle());
            resource.setPublished(entry.getPublished().getValue().toGregorianCalendar());
            resource.setUpdated(entry.getUpdated().getValue().toGregorianCalendar());
        }

    }
    
    // Establish the rel="up" links into the parent collections
    //
	private void linkUp(IdentifiedObject resource) {
		if (resource.getUpLink() != null) {
			List<IdentifiedObject> parents = resourceService
					.findByAllParentsHref(resource.getUpLink().getHref(),resource);
			for (IdentifiedObject parent : parents) {
				// add the parent to the transaction
				resourceService.merge(parent);
				// add the resource to the parent collection
				resource.setUpResource(parent);
			}
		}
	}

	// Establish the rel="related" links to the associated resources
	//
    private void linkUpMember(IdentifiedObject resource) {
    	
        if (resource.getSelfLink() != null) {
            List<IdentifiedObject> parents = resourceService.findByAllParentsHref(resource.getSelfLink().getHref(), resource);
            for(IdentifiedObject parent : parents) {
                    // put the existing resource in the transaction
                    resourceService.merge(parent);
                    
                    // Based on the kind of resource, do the appropriate fixup
                    if (resource instanceof TimeConfiguration) {
                        UsagePoint usagePoint = (UsagePoint)parent;
                        
                        if (usagePoint.getLocalTimeParameters() == null) {
                        	
                           usagePoint.setLocalTimeParameters((TimeConfiguration)resource);

                        }
                    }

                    if (resource instanceof ReadingType) {
                    	
                        MeterReading meterReading = (MeterReading) parent;
                        if (meterReading.getReadingType() == null) {
                          meterReading.setReadingType((ReadingType) resource);
                        }
                    }
                }
            }
        }
    

    private void linkRelatedCollection(IdentifiedObject resource) {

        List<IdentifiedObject> relatedResources = resourceService.findAllRelated(resource);

            for(IdentifiedObject relatedResource : relatedResources) {
            	// Put the relatedResource in the Transaction
            	resourceService.merge(relatedResource);
            	
            	if (resource instanceof UsagePoint) {
            		if (relatedResource instanceof TimeConfiguration ) {
            			((UsagePoint) resource).setLocalTimeParameters((TimeConfiguration) relatedResource);            			
            		}
            		
            		if (relatedResource instanceof MeterReading) {
            			((UsagePoint) resource).addMeterReading((MeterReading) relatedResource);
            		}
            		
            		if (relatedResource instanceof ElectricPowerUsageSummary) {
            			((UsagePoint) resource).addElectricPowerUsageSummary((ElectricPowerUsageSummary) relatedResource);
            		}
            		
            		if (relatedResource instanceof ElectricPowerQualitySummary) {
            			((UsagePoint) resource).addElectricPowerQualitySummary((ElectricPowerQualitySummary) relatedResource);
            		}
            	}
            	if (resource instanceof MeterReading) {
            		
            		if (relatedResource instanceof ReadingType) {           		
            			((MeterReading) resource).setReadingType((ReadingType) relatedResource);
            		}
            		
            		if (relatedResource instanceof IntervalBlock) {
            			((MeterReading) resource).addIntervalBlock((IntervalBlock) relatedResource);
            		}
            	}

            }
          
    }
    
    public void setResourceService(ResourceService resourceService) {
    	this.resourceService = resourceService;
    }
}
