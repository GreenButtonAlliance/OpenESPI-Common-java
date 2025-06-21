/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.service.impl;

import org.greenbuttonalliance.espi.common.domain.legacy.ApplicationInformation;
import org.greenbuttonalliance.espi.common.domain.legacy.ElectricPowerQualitySummary;
import org.greenbuttonalliance.espi.common.domain.legacy.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.legacy.IntervalBlock;
import org.greenbuttonalliance.espi.common.domain.legacy.MeterReading;
import org.greenbuttonalliance.espi.common.domain.legacy.ReadingType;
import org.greenbuttonalliance.espi.common.domain.legacy.TimeConfiguration;
import org.greenbuttonalliance.espi.common.domain.legacy.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.LinkType;
import org.greenbuttonalliance.espi.common.service.EntryProcessorService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		jakarta.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
@Service
public class EntryProcessorServiceImpl implements EntryProcessorService {

	@Autowired
	private ResourceService resourceService;

	public EntryType process(EntryType entry) {
		convert(entry);
		for (IdentifiedObject resource : entry.getContent().getResources()) {

			try {

				IdentifiedObject existingResource = resourceService.findByUUID(
						resource.getUUID(), resource.getClass());
				existingResource = resourceService.merge(existingResource);

				// merge the new into the old and throw the new away
				//
				existingResource.merge(resource);

				// now put the existing resource back into the structure so it
				// will
				// be available for later processing

				if (existingResource instanceof UsagePoint) {
					entry.getContent().setUsagePoint(
							(UsagePoint) existingResource);
				}

				if (existingResource instanceof MeterReading) {
					entry.getContent().setMeterReading(
							(MeterReading) existingResource);
				}

				if (existingResource instanceof IntervalBlock) {
					// System.out.printf("*****We have an existing IntervalBlock??: %s\n",
					// existingResource.toString());
					List<IntervalBlock> intervalBlocks = entry.getContent()
							.getIntervalBlocks();
					List<IntervalBlock> newList = new ArrayList<IntervalBlock>();
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

					entry.getContent().setReadingType(
							(ReadingType) existingResource);
				}

				if (existingResource instanceof TimeConfiguration) {

					entry.getContent().setLocalTimeParameters(
							(TimeConfiguration) existingResource);
				}

				// ElectricPowerUsageSummary removed - deprecated resource
				// if (existingResource instanceof ElectricPowerUsageSummary) {
				//     entry.getContent().setElectricPowerUsageSummary(
				//         (ElectricPowerUsageSummary) existingResource);
				// }

				if (existingResource instanceof ElectricPowerQualitySummary) {

					entry.getContent().setElectricPowerQualitySummary(
							(ElectricPowerQualitySummary) existingResource);
				}

				if (existingResource instanceof ApplicationInformation) {

					entry.getContent().setApplicationInformation(
							(ApplicationInformation) existingResource);
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
		// support for entries that contain multiple resources has been added here
		// the result of entry.getContent().getResources()) can return a mixed back of resources, only one of which
		// will have appropriate UUID and self references - the rest will need UUIDs and self references created
		// NOTE: that this means, in effect, that if the resources types MUST have the same up reference (because there
		// is only one available in the <entry> structure itself
		Integer resourceCount = 0;
		for (IdentifiedObject resource : entry.getContent().getResources()) {
			resourceCount++;
			if (resourceCount > 1) {
				// make a new UUID and stash it in the entry
				entry.setId(UUID.randomUUID().toString());
			} 
			resource.setMRID(entry.getId());
	
			for (LinkType link : entry.getLinks()) {
				if (link.getRel().equals(LinkType.SELF)) {
					LinkType newLink = link;
					if (resourceCount >1) {
						// it doesn't mater what this is, except that it must be
						// unique - the UUID works just fine
						newLink = new LinkType();
						newLink.setHref(entry.getId());
						newLink.setRel(LinkType.SELF);
					}
					resource.setSelfLink(newLink);
				}
				if (link.getRel().equals(LinkType.UP))
					resource.setUpLink(link);
				if (link.getRel().equals(LinkType.RELATED))
					resource.getRelatedLinks().add(link);
			}
			resource.setDescription(entry.getTitle());
			resource.setPublished(entry.getPublished().getValue()
					.toGregorianCalendar());
			resource.setUpdated(entry.getUpdated().getValue()
					.toGregorianCalendar());
		}

	}

	// Establish the rel="up" links into the parent collections
	//
	private void linkUp(IdentifiedObject resource) {
		if (resource.getUpLink() != null) {
			List<IdentifiedObject> parents = resourceService
					.findByAllParentsHref(resource.getUpLink().getHref(),
							resource);
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
			List<IdentifiedObject> parents = resourceService
					.findByAllParentsHref(resource.getSelfLink().getHref(),
							resource);
			for (IdentifiedObject parent : parents) {
				// put the existing resource in the transaction
				resourceService.merge(parent);

				// Based on the kind of resource, do the appropriate fixup
				if (resource instanceof TimeConfiguration) {
					UsagePoint usagePoint = (UsagePoint) parent;

					if (usagePoint.getLocalTimeParameters() == null) {

						usagePoint
								.setLocalTimeParameters((TimeConfiguration) resource);

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

		List<IdentifiedObject> relatedResources = resourceService
				.findAllRelated(resource);

		for (IdentifiedObject relatedResource : relatedResources) {
			// Put the relatedResource in the Transaction
			resourceService.merge(relatedResource);

			if (resource instanceof UsagePoint) {
				if (relatedResource instanceof TimeConfiguration) {
					((UsagePoint) resource)
							.setLocalTimeParameters((TimeConfiguration) relatedResource);
				}

				if (relatedResource instanceof MeterReading) {
					((UsagePoint) resource)
							.addMeterReading((MeterReading) relatedResource);
				}

				// ElectricPowerUsageSummary removed - deprecated resource
				// if (relatedResource instanceof ElectricPowerUsageSummary) {
				//     ((UsagePoint) resource)
				//         .addElectricPowerUsageSummary((ElectricPowerUsageSummary) relatedResource);
				// }

				if (relatedResource instanceof ElectricPowerQualitySummary) {
					((UsagePoint) resource)
							.addElectricPowerQualitySummary((ElectricPowerQualitySummary) relatedResource);
				}
			}
			if (resource instanceof MeterReading) {

				if (relatedResource instanceof ReadingType) {
					((MeterReading) resource)
							.setReadingType((ReadingType) relatedResource);
				}

				if (relatedResource instanceof IntervalBlock) {
					((MeterReading) resource)
							.addIntervalBlock((IntervalBlock) relatedResource);
				}
			}

		}

	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService(ResourceService resourceService) {
		return resourceService;
	}
}
