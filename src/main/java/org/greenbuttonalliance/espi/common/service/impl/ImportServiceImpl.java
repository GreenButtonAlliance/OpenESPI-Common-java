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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenbuttonalliance.espi.common.domain.usage.RetailCustomerEntity;
import org.greenbuttonalliance.espi.common.domain.usage.SubscriptionEntity;
import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.greenbuttonalliance.espi.common.dto.atom.AtomFeedDto;
import org.greenbuttonalliance.espi.common.dto.atom.AtomEntryDto;
import org.greenbuttonalliance.espi.common.dto.atom.AtomContentDto;
import org.greenbuttonalliance.espi.common.dto.usage.UsagePointDto;
import org.greenbuttonalliance.espi.common.dto.usage.ApplicationInformationDto;
import org.greenbuttonalliance.espi.common.dto.usage.AuthorizationDto;
import org.greenbuttonalliance.espi.common.dto.usage.MeterReadingDto;
import org.greenbuttonalliance.espi.common.dto.usage.ReadingTypeDto;
import org.greenbuttonalliance.espi.common.dto.usage.IntervalBlockDto;
import org.greenbuttonalliance.espi.common.dto.customer.CustomerDto;
import org.greenbuttonalliance.espi.common.mapper.usage.UsagePointMapper;
import org.greenbuttonalliance.espi.common.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {

	private final Log logger = LogFactory.getLog(getClass());

	private final Jaxb2Marshaller jaxb2Marshaller;
	private final AuthorizationService authorizationService;
	private final SubscriptionService subscriptionService;
	private final UsagePointService usagePointService;
	private final ResourceService resourceService;
	private final RetailCustomerService retailCustomerService;
	private final EntryProcessorService entryProcessorService;
	private final UsagePointMapper usagePointMapper;

	public ImportServiceImpl(@Qualifier("atom") Jaxb2Marshaller jaxb2Marshaller,
							AuthorizationService authorizationService,
							SubscriptionService subscriptionService,
							UsagePointService usagePointService,
							ResourceService resourceService,
							RetailCustomerService retailCustomerService,
							EntryProcessorService entryProcessorService,
							UsagePointMapper usagePointMapper) {
		this.jaxb2Marshaller = jaxb2Marshaller;
		this.authorizationService = authorizationService;
		this.subscriptionService = subscriptionService;
		this.usagePointService = usagePointService;
		this.retailCustomerService = retailCustomerService;
		this.resourceService = resourceService;
		this.entryProcessorService = entryProcessorService;
		this.usagePointMapper = usagePointMapper;
	}

	// this is a list of the UsagePointEntities referenced during
	// this import
	private List<UsagePointEntity> usagePoints;

	// Min Updated <== used on time scoping the subscriptions
	//
	private XMLGregorianCalendar minUpdated = null;

	// Max Updated <== used on time scoping the subscriptions
	//
	private XMLGregorianCalendar maxUpdated = null;

	@Override
	public List<UsagePointEntity> getEntries() {
		List<UsagePointEntity> result = usagePoints;
		usagePoints = null;
		return result;
	}

	@Override
	public XMLGregorianCalendar getMinUpdated() {
		return this.minUpdated;
	}

	@Override
	public XMLGregorianCalendar getMaxUpdated() {
		return this.maxUpdated;
	}

	@Override
	public void importData(InputStream stream, Long retailCustomerId) {
		try {
			// Initialize modern entity collections
			this.usagePoints = new ArrayList<>();
			this.minUpdated = null;
			this.maxUpdated = null;
			
			// Create JAXB context for DTO classes
			JAXBContext context = JAXBContext.newInstance(
				"org.greenbuttonalliance.espi.common.dto.atom:" +
				"org.greenbuttonalliance.espi.common.dto.usage:" +
				"org.greenbuttonalliance.espi.common.dto.customer"
			);
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Try to unmarshal as an Atom feed first
			try {
				Object unmarshalled = unmarshaller.unmarshal(stream);
				logger.info("Successfully unmarshalled object of type: " + unmarshalled.getClass().getSimpleName());
				
				// Process the unmarshalled object based on its type
				if (unmarshalled instanceof org.greenbuttonalliance.espi.common.dto.atom.AtomFeedDto) {
					processAtomFeed((org.greenbuttonalliance.espi.common.dto.atom.AtomFeedDto) unmarshalled, retailCustomerId);
				} else if (unmarshalled instanceof org.greenbuttonalliance.espi.common.dto.atom.AtomEntryDto) {
					processAtomEntry((org.greenbuttonalliance.espi.common.dto.atom.AtomEntryDto) unmarshalled, retailCustomerId);
				} else {
					logger.warn("Unrecognized XML content type: " + unmarshalled.getClass().getSimpleName());
				}
				
			} catch (JAXBException e) {
				logger.error("Failed to parse XML as Atom feed/entry: " + e.getMessage());
				// Could try other parsing strategies here
			}
			
		} catch (JAXBException e) {
			logger.error("Failed to create JAXB context for import: " + e.getMessage());
		}
	}
	
	private void processAtomFeed(AtomFeedDto feed, Long retailCustomerId) {
		logger.info("Processing Atom feed with title: " + feed.title());
		
		if (feed.entries() != null) {
			for (AtomEntryDto entry : feed.entries()) {
				processAtomEntry(entry, retailCustomerId);
			}
		}
	}
	
	private void processAtomEntry(AtomEntryDto entry, Long retailCustomerId) {
		logger.info("Processing Atom entry with title: " + entry.title());
		
		if (entry.content() != null && entry.content().resource() != null) {
			Object resource = entry.content().resource();
			
			// Handle different resource types
			if (resource instanceof UsagePointDto) {
				processUsagePointDto((UsagePointDto) resource, retailCustomerId);
			} else if (resource instanceof ApplicationInformationDto) {
				processApplicationInformationDto((ApplicationInformationDto) resource);
			} else if (resource instanceof AuthorizationDto) {
				processAuthorizationDto((AuthorizationDto) resource, retailCustomerId);
			} else if (resource instanceof MeterReadingDto) {
				processMeterReadingDto((MeterReadingDto) resource, retailCustomerId);
			} else if (resource instanceof ReadingTypeDto) {
				processReadingTypeDto((ReadingTypeDto) resource);
			} else if (resource instanceof IntervalBlockDto) {
				processIntervalBlockDto((IntervalBlockDto) resource, retailCustomerId);
			} else if (resource instanceof CustomerDto) {
				processCustomerDto((CustomerDto) resource);
			} else {
				logger.info("Entry contains resource of type: " + resource.getClass().getSimpleName());
				// TODO: Add handlers for other resource types as needed
			}
		}
	}
	
	private void processUsagePointDto(UsagePointDto dto, Long retailCustomerId) {
		logger.info("Processing UsagePointDto with description: " + dto.description());
		
		// Convert DTO to entity using mapper
		UsagePointEntity entity = usagePointMapper.toEntity(dto);
		
		// Associate with retail customer if provided
		if (retailCustomerId != null) {
			logger.info("Associating usage point with retail customer ID: " + retailCustomerId);
			// TODO: Set retail customer relationship
		}
		
		// Add to collection for getEntries() method
		this.usagePoints.add(entity);
		
		logger.info("Successfully processed UsagePoint DTO, total usage points: " + this.usagePoints.size());
	}
	
	private void processApplicationInformationDto(ApplicationInformationDto dto) {
		logger.info("Processing ApplicationInformationDto with client ID: " + dto.clientId());
		// TODO: Convert DTO to entity and store or provide access for services
		// For now, just log that we processed it
	}
	
	private void processAuthorizationDto(AuthorizationDto dto, Long retailCustomerId) {
		logger.info("Processing AuthorizationDto with access token: " + 
		           (dto.accessToken() != null ? dto.accessToken().substring(0, Math.min(10, dto.accessToken().length())) + "..." : "null"));
		// TODO: Convert DTO to entity and store or provide access for services
		// For now, just log that we processed it
	}
	
	private void processMeterReadingDto(MeterReadingDto dto, Long retailCustomerId) {
		logger.info("Processing MeterReadingDto with UUID: " + dto.uuid());
		// TODO: Convert DTO to entity and store or provide access for services
		// For now, just log that we processed it
	}
	
	private void processReadingTypeDto(ReadingTypeDto dto) {
		logger.info("Processing ReadingTypeDto: " + dto.description() + " (" + dto.commodity() + " " + dto.kind() + ")");
		// TODO: Convert DTO to entity and store or provide access for services
		// For now, just log that we processed it
	}
	
	private void processIntervalBlockDto(IntervalBlockDto dto, Long retailCustomerId) {
		logger.info("Processing IntervalBlockDto with " + dto.getIntervalReadingCount() + " readings");
		// TODO: Convert DTO to entity and store or provide access for services
		// For now, just log that we processed it
	}
	
	private void processCustomerDto(CustomerDto dto) {
		logger.info("Processing CustomerDto: " + dto.customerName() + " (" + dto.kind() + ")");
		// TODO: Convert DTO to entity and store or provide access for services
		// For now, just log that we processed it
	}
}