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
import org.greenbuttonalliance.espi.common.domain.legacy.*;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.DateTimeType;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.service.*;
import org.greenbuttonalliance.espi.common.utils.AtomMarshallerListener;
import org.greenbuttonalliance.espi.common.utils.DateConverter;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletOutputStream;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ExportServiceImpl implements ExportService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private RetailCustomerService retailCustomerService;

	@Autowired
	private UsagePointService usagePointService;

	@Autowired
	private MeterReadingService meterReadingService;

	@Autowired
	private ReadingTypeService readingTypeService;

	@Autowired
	private IntervalBlockService intervalBlockService;

	@Autowired
	private ElectricPowerQualitySummaryService electricPowerQualitySummaryService;

	// ElectricPowerUsageSummaryService removed - deprecated resource

	@Autowired
	private UsageSummaryService usageSummaryService;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ApplicationInformationService applicationInformationService;

	@Autowired
	private TimeConfigurationService timeConfigurationService;

	@Autowired
	@Qualifier("fragmentMarshaller")
	private Jaxb2Marshaller fragmentMarshaller;

	// setup the services
	//

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService() {
		return this.resourceService;
	}

	public void setSubscriptionService(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	public SubscriptionService getSubscriptionService() {
		return this.subscriptionService;
	}

	public void setRetailCustomerService(
			RetailCustomerService retailCustomerService) {
		this.retailCustomerService = retailCustomerService;
	}

	public RetailCustomerService getRetailCustomerService() {
		return this.retailCustomerService;
	}

	public void setUsagePointService(UsagePointService usagePointService) {
		this.usagePointService = usagePointService;
	}

	public UsagePointService getUsagePointService() {
		return this.usagePointService;
	}

	public void setMeterReadingService(MeterReadingService meterReadingService) {
		this.meterReadingService = meterReadingService;
	}

	public MeterReadingService getMeterReadingService() {
		return this.meterReadingService;
	}

	public void setReadingTypeService(ReadingTypeService readingTypeService) {
		this.readingTypeService = readingTypeService;
	}

	public ReadingTypeService getReadingTypeService() {
		return this.readingTypeService;
	}

	public void setIntervalBlockService(
			IntervalBlockService intervalBlockService) {
		this.intervalBlockService = intervalBlockService;
	}

	public IntervalBlockService getIntervalBlockService() {
		return this.intervalBlockService;
	}

	public void setElectricPowerQualitySummaryService(
			ElectricPowerQualitySummaryService electricPowerQualitySummaryService) {
		this.electricPowerQualitySummaryService = electricPowerQualitySummaryService;
	}

	public ElectricPowerQualitySummaryService getElectricPowerQualitySummaryService() {
		return this.electricPowerQualitySummaryService;
	}

	// ElectricPowerUsageSummaryService methods removed - deprecated resource

	public void setUsageSummaryService(
			UsageSummaryService usageSummaryService) {
		this.usageSummaryService = usageSummaryService;
	}

	public UsageSummaryService getUsageSummaryService() {
		return this.usageSummaryService;
	}

	public void setAuthorizationService(
			AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public AuthorizationService getAuthorizationService() {
		return this.authorizationService;
	}

	public void setApplicationInformationService(
			ApplicationInformationService applicationInformationService) {
		this.applicationInformationService = applicationInformationService;
	}

	public ApplicationInformationService getApplicationInformationService() {
		return this.applicationInformationService;
	}

	public void setTimeConfigurationService(
			TimeConfigurationService timeConfigurationService) {
		this.timeConfigurationService = timeConfigurationService;
	}

	public TimeConfigurationService getTimeConfigurationService() {
		return this.timeConfigurationService;
	}

	public void setJaxb2Marshaller(Jaxb2Marshaller fragmentMarshaller) {
		this.fragmentMarshaller = fragmentMarshaller;
	}

	@Override
	public <T extends IdentifiedObject> void exportResource(Long resourceId,
															Class<T> clazz, OutputStream stream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends IdentifiedObject> void exportResources(Class<T> clazz,
			OutputStream stream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub

	}

	// ApplicationInformation

	// - ROOT form
	@Override
	public void exportApplicationInformation(Long applicationInformationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ApplicationInformation/"
				+ applicationInformationId;
		exportEntry(resourceService.findEntryType(applicationInformationId,
				ApplicationInformation.class), stream, exportFilter,
				hrefFragment);
	}

	@Override
	public void exportApplicationInformations(OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ApplicationInformation";
		exportEntries(
				resourceService
						.findEntryTypeIterator(ApplicationInformation.class),
				stream, exportFilter, ApplicationInformation.class,
				hrefFragment);
	}

	// Authorization

	// - ROOT form
	@Override
	public void exportAuthorization(Long authorizationId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Authorization/" + authorizationId;
		exportEntry(resourceService.findEntryType(authorizationId,
				Authorization.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportAuthorizations(OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Authorization";
		exportEntries(
				resourceService.findEntryTypeIterator(Authorization.class),
				stream, exportFilter, Authorization.class, hrefFragment);
	}

	@Override
	public void exportAuthorizations(Authorization authorization,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Authorization";

		exportEntries(resourceService.findEntryTypeIterator(
				authorizationService
						.findAllIdsByApplicationInformationId(authorization
								.getApplicationInformation().getId()),
				Authorization.class), stream, exportFilter,
				Authorization.class, hrefFragment);
	}

	// - XPath form
	@Override
	public void exportAuthorization(Long retailCustomerId,
			Long authorizationId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId
				+ "/Authorization/" + authorizationId;
		exportEntry(authorizationService.findEntryType(retailCustomerId,
				authorizationId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportAuthorizations(Long retailCustomerId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId
				+ "/Authorization";
		exportEntries(
				authorizationService.findEntryTypeIterator(retailCustomerId),
				stream, exportFilter, Authorization.class, hrefFragment);
	}

	// ElectricPowerQualitySummary

	// - ROOT form
	@Override
	public void exportElectricPowerQualitySummary_Root(Long subscriptionId,
			Long electricPowerQualitySummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		ElectricPowerQualitySummary electricPowerQualitySummary = resourceService
				.findById(electricPowerQualitySummaryId,
						ElectricPowerQualitySummary.class);
		UsagePoint usagePoint = electricPowerQualitySummary.getUsagePoint();

		String hrefFragment = "/Subscription/" + subscriptionId + "/UsagePoint"
				+ usagePoint.getId() + "/ElectricPowerQualitySummary/"
				+ electricPowerQualitySummaryId;

		exportEntry(subscriptionId, resourceService.findEntryType(
				electricPowerQualitySummaryId,
				ElectricPowerQualitySummary.class), stream, exportFilter,
				hrefFragment);
	}

	@Override
	public void exportElectricPowerQualitySummarys_Root(Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {

		exportRootForm("/ElectricPowerQualitySummary", subscriptionId, stream,
				exportFilter, ElectricPowerQualitySummary.class);
	}

	// - XPath form
	@Override
	public void exportElectricPowerQualitySummary(Long subscriptionId,
			Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription" + subscriptionId + "/UsagePoint/"
				+ usagePointId + "/ElectricPowerQualitySummary/"
				+ electricPowerQualitySummaryId;
		exportEntry(subscriptionId,
				electricPowerQualitySummaryService.findEntryType(
						retailCustomerId, usagePointId,
						electricPowerQualitySummaryId), stream, exportFilter,
				hrefFragment);
	}

	@Override
	public void exportElectricPowerQualitySummarys(Long subscriptionId,
			Long retailCustomerId, Long usagePointId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription" + subscriptionId + "/UsagePoint/"
				+ usagePointId + "/ElectricPowerQualitySummary";
		exportEntries(subscriptionId,
				electricPowerQualitySummaryService.findEntryTypeIterator(
						retailCustomerId, usagePointId), stream, exportFilter,
				ElectricPowerQualitySummary.class, hrefFragment);
	}

	// ElectricPowerUsageSummary

	// - ROOT form
	// ElectricPowerUsageSummary removed - deprecated resource
	// public void exportElectricPowerUsageSummarys_Root(Long subscriptionId,
	//		ServletOutputStream outputStream, ExportFilter exportFilter)
	// throws IOException {
	//	exportRootForm("/ElectricPowerUsageSummary", subscriptionId,
	//			outputStream, exportFilter, ElectricPowerUsageSummary.class);
	// }

	// ElectricPowerUsageSummary removed - deprecated resource
	// @Override
	// public void exportElectricPowerUsageSummary_Root(Long subscriptionId,
	//		long electricPowerUsageSummaryId, ServletOutputStream stream,
	//		ExportFilter exportFilter) throws IOException {
	//	ElectricPowerUsageSummary electricPowerUsageSummary = resourceService
	//			.findById(electricPowerUsageSummaryId,
	//					ElectricPowerUsageSummary.class);
	//	UsagePoint usagePoint = electricPowerUsageSummary.getUsagePoint();
	//
	//	String hrefFragment = "/Subscription/" + subscriptionId + "/UsagePoint"
	//			+ usagePoint.getId() + "/ElectricPowerUsageSummary/"
	//			+ electricPowerUsageSummaryId;
	//
	//	exportEntry(subscriptionId, resourceService.findEntryType(
	//			electricPowerUsageSummaryId, ElectricPowerUsageSummary.class),
	//			stream, exportFilter, hrefFragment);
	// }

	// - XPath form
	// ElectricPowerUsageSummary removed - deprecated resource
	// @Override
	// public void exportElectricPowerUsageSummary(Long subscriptionId,
	//		Long retailCustomerId, Long usagePointId,
	//		Long electricPowerUsageSummaryId, OutputStream stream,
	//		ExportFilter exportFilter) throws IOException {
	//	String hrefFragment = "/Subscription/" + subscriptionId
	//			+ "/UsagePoint/" + usagePointId + "/ElectricPowerUsageSummary/"
	//			+ electricPowerUsageSummaryId;
	//	exportEntry(subscriptionId,
	//			electricPowerUsageSummaryService.findEntryType(
	//					retailCustomerId, usagePointId,
	//					electricPowerUsageSummaryId), stream, exportFilter,
	//			hrefFragment);
	// }

	// ElectricPowerUsageSummary removed - deprecated resource
	// @Override
	// public void exportElectricPowerUsageSummarys(Long subscriptionId,
	//		Long retailCustomerId, Long usagePointId, OutputStream stream,
	//		ExportFilter exportFilter) throws IOException {
	//	String hrefFragment = "/Subscription/" + subscriptionId
	//			+ "/UsagePoint/" + usagePointId + "/ElectricPowerUsageSummary";
	//	exportEntries(subscriptionId,
	//			electricPowerUsageSummaryService.findEntryTypeIterator(
	//					retailCustomerId, usagePointId), stream, exportFilter,
	//			ElectricPowerUsageSummary.class, hrefFragment);
	// }

	// UsageSummary

	// - ROOT form
	@Override
	public void exportUsageSummarys_Root(Long subscriptionId,
										 ServletOutputStream outputStream, ExportFilter exportFilter)

			throws IOException {
		exportRootForm("/UsageSummary", subscriptionId,
				outputStream, exportFilter, UsageSummary.class);
	}

	@Override
	public void exportUsageSummary_Root(Long subscriptionId,
										long usageSummaryId, ServletOutputStream stream,
										ExportFilter exportFilter) throws IOException {
		UsageSummary usageSummary = resourceService
				.findById(usageSummaryId,
						UsageSummary.class);
		UsagePoint usagePoint = usageSummary.getUsagePoint();

		String hrefFragment = "/Subscription/" + subscriptionId + "/UsagePoint"
				+ usagePoint.getId() + "/UsageSummary/"
				+ usageSummaryId;

		exportEntry(subscriptionId, resourceService.findEntryType(
				usageSummaryId, UsageSummary.class),
				stream, exportFilter, hrefFragment);
	}

	// - XPath form
	@Override
	public void exportUsageSummary(Long subscriptionId,
								   Long retailCustomerId, Long usagePointId,
								   Long usageSummaryId, OutputStream stream,
								   ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId + "/UsageSummary/"
				+ usageSummaryId;
		exportEntry(subscriptionId,
				usageSummaryService.findEntryType(
						retailCustomerId, usagePointId,
						usageSummaryId), stream, exportFilter,
				hrefFragment);
	}

	@Override
	public void exportUsageSummarys(Long subscriptionId,
									Long retailCustomerId, Long usagePointId, OutputStream stream,
									ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId + "/UsageSummary";
		exportEntries(subscriptionId,
				usageSummaryService.findEntryTypeIterator(
						retailCustomerId, usagePointId), stream, exportFilter,
				UsageSummary.class, hrefFragment);
	}

	// IntervalBlock

	// - ROOT form
	@Override
	public void exportIntervalBlock_Root(Long subscriptionId,
			Long intervalBlockId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		IntervalBlock intervalBlock = resourceService.findById(intervalBlockId,
				IntervalBlock.class);
		MeterReading meterReading = intervalBlock.getMeterReading();
		UsagePoint usagePoint = meterReading.getUsagePoint();

		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePoint.getId() + "/MeterReading/"
				+ meterReading.getId() + "/IntervalBlock/" + intervalBlockId;
		exportEntry(subscriptionId, resourceService.findEntryType(
				intervalBlockId, IntervalBlock.class), stream, exportFilter,
				hrefFragment);
	}

	@Override
	public void exportIntervalBlocks_Root(Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {

		exportRootForm("/IntervalBlock", subscriptionId, stream, exportFilter,
				IntervalBlock.class);

	}

	// - XPath form
	@Override
	public void exportIntervalBlock(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, Long meterReadingId, Long intervalBlockId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId + "/IntervalBlock/"
				+ intervalBlockId;
		exportEntry(
				subscriptionId,
				findEntryTypeXPath(subscriptionId, retailCustomerId,
						usagePointId, meterReadingId, intervalBlockId,
						IntervalBlock.class), stream, exportFilter,
				hrefFragment);
	}

	@Override
	public void exportIntervalBlocks(Long subscriptionId,
			Long retailCustomerId, Long usagePointId, Long meterReadingId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId + "/IntervalBlock";
		exportEntries(
				subscriptionId,
				findEntryTypeIteratorXPath(subscriptionId, retailCustomerId,
						usagePointId, meterReadingId, IntervalBlock.class),
				stream, exportFilter, IntervalBlock.class, hrefFragment);
	}

	// MeterReading

	// - ROOT form
	@Override
	public void exportMeterReadings_Root(Long subscriptionId,
			ServletOutputStream stream, ExportFilter exportFilter)
			throws IOException {

		exportRootForm("/MeterReading", subscriptionId, stream, exportFilter,
				MeterReading.class);

	}

	@Override
	public void exportMeterReading_Root(Long subscriptionId,
			long meterReadingId, ServletOutputStream stream,
			ExportFilter exportFilter) throws IOException {
		MeterReading meterReading = resourceService.findById(meterReadingId,
				MeterReading.class);
		UsagePoint usagePoint = meterReading.getUsagePoint();

		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePoint.getId() + "/MeterReading/"
				+ meterReadingId;
		exportEntry(subscriptionId, resourceService.findEntryType(
				meterReadingId, MeterReading.class), stream, exportFilter,
				hrefFragment);
	}

	// - XPath
	@Override
	public void exportMeterReading(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, Long meterReadingId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId + "/MeterReading/"
				+ meterReadingId;
		exportEntry(
				subscriptionId,
				findEntryTypeXPath(subscriptionId, retailCustomerId,
						usagePointId, meterReadingId, 0L, MeterReading.class),
				stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportMeterReadings(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId + "/MeterReading";
		exportEntries(
				subscriptionId,
				findEntryTypeIteratorXPath(subscriptionId, retailCustomerId,
						usagePointId, 0L, MeterReading.class), stream,
				exportFilter, MeterReading.class, hrefFragment);
	}

	// ReadingType

	// ROOT form
	@Override
	public void exportReadingType(Long readingTypeId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ReadingType/" + readingTypeId;
		exportEntry(
				resourceService.findEntryType(readingTypeId, ReadingType.class),
				stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportReadingTypes(OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ReadingType";
		exportEntries(resourceService.findEntryTypeIterator(ReadingType.class),
				stream, exportFilter, ReadingType.class, hrefFragment);
	}

	// RetailCustomer

	// ROOT form
	@Override
	public void exportRetailCustomer(Long subscriptionId,
			Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId;
		exportEntry(
				findEntryTypeXPath(subscriptionId, retailCustomerId, 0L, 0L,
						0L, RetailCustomer.class), stream, exportFilter,
				hrefFragment);
	}

	@Override
	public void exportRetailCustomers(Long subscriptionId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer";
		exportEntries(
				findEntryTypeIteratorXPath(0L, 0L, 0L, 0L, RetailCustomer.class),
				stream, exportFilter, UsagePoint.class, hrefFragment);
	}

	// Subscription

	// - Root form

	@Override
	public void exportSubscription(Long subscriptionId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId;
		exportEntry(resourceService.findEntryType(subscriptionId,
				Subscription.class), stream, exportFilter, hrefFragment);

	}

	@Override
	public void exportSubscriptions(OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription";
		exportEntries(
				resourceService.findEntryTypeIterator(Subscription.class),
				stream, exportFilter, RetailCustomer.class, hrefFragment);
	}

	// - XPath form
	@Override
	public void exportSubscription(Long retailCustomerId, Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/ + retailCustomerId + "
				+ "/Subscription/" + subscriptionId;
		exportEntry(subscriptionService.findEntryType(retailCustomerId,
				subscriptionId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportSubscriptions(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId
				+ "/Subscription";
		exportEntries(
				subscriptionService.findEntryTypeIterator(retailCustomerId),
				stream, exportFilter, RetailCustomer.class, hrefFragment);
	}

	// TimeConfiguration

	// - ROOT form
	@Override
	public void exportTimeConfiguration(Long timeConfigurationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/LocalTimeParameters/" + timeConfigurationId;
		exportEntry(resourceService.findEntryType(timeConfigurationId,
				TimeConfiguration.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportTimeConfigurations(OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/LocalTimeParameters";
		exportEntries(
				resourceService.findEntryTypeIterator(TimeConfiguration.class),
				stream, exportFilter, TimeConfiguration.class, hrefFragment);
	}

	// UsagePoints
	//
	// ROOT form
	@Override
	public void exportUsagePoint_Root(Long subscriptionId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId;
		exportEntry(subscriptionId,
				resourceService.findEntryType(usagePointId, UsagePoint.class),
				stream, exportFilter, hrefFragment);

	}

	@Override
	public void exportUsagePoints_Root(Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportRootForm("/UsagePoint", subscriptionId, stream, exportFilter,
				UsagePoint.class);
	}

	// XPath form
	@Override
	public void exportUsagePoint(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId;
		exportEntry(
				subscriptionId,
				findEntryTypeXPath(subscriptionId, retailCustomerId,
						usagePointId, 0L, 0L, UsagePoint.class), stream,
				exportFilter, hrefFragment);
	}

	@Override
	public void exportUsagePoints(Long subscriptionId, Long retailCustomerId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		// here we need to get only a subset of the usagepoints are obtained
		String hrefFragment = "/Subscription/" + subscriptionId + "/UsagePoint";
		exportEntries(
				subscriptionId,
				findEntryTypeIteratorXPath(subscriptionId, retailCustomerId,
						0L, 0L, UsagePoint.class), stream, exportFilter,
				UsagePoint.class, hrefFragment);
	}

	// Special forms for Subscription, Bulk, Download
	//
	// -- original Pivotal export function (used in pub/sub flow)
	@Override
	public void exportSubscription(String subscriptionHashedId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionHashedId;
		exportEntriesFull(
				subscriptionService.findEntriesByHashedId(subscriptionHashedId),
				stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportBatchSubscription(Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Batch/Subscription/" + subscriptionId;
		// first find all the usagePointIds this subscription is related to
		List<Long> usagePointIdList = subscriptionService
				.findUsagePointIds(subscriptionId);
		exportEntriesFull(subscriptionId,
				resourceService.findEntryTypeIterator(usagePointIdList,
						UsagePoint.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportBatchSubscriptionUsagePoint(Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Batch/Subscription/" + subscriptionId
				+ "/UsagePoint";
		// first find all the usagePointIds this subscription is related to
		List<Long> usagePointIdList = subscriptionService
				.findUsagePointIds(subscriptionId);
		exportEntriesFull(subscriptionId,
				resourceService.findEntryTypeIterator(usagePointIdList,
						UsagePoint.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportBatchSubscriptionUsagePoint(Long subscriptionId,
			Long usagePointId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		String hrefFragment = "/Batch/Subscription/" + subscriptionId
				+ "/UsagePoint/" + usagePointId;
		List<Long> usagePointIdList = new ArrayList<Long>();
		List<Long> temp = subscriptionService.findUsagePointIds(subscriptionId);
		if (temp.contains(usagePointId)) {
			usagePointIdList.add(usagePointId);
		}
		exportEntriesFull(subscriptionId,
				resourceService.findEntryTypeIterator(usagePointIdList,
						UsagePoint.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportBatchBulk(Long bulkId, String thirdParty,
			OutputStream outputStream, ExportFilter exportFilter)
			throws IOException {

		String hrefFragment = "/Batch/Bulk/" + bulkId;
		List<Long> usagePoints = new ArrayList<Long>();
		List<Long> authorizations = authorizationService.findAllIdsByBulkId(
				thirdParty, bulkId);

		for (Long authorizationId : authorizations) {
			Subscription subscription = subscriptionService
					.findByAuthorizationId(authorizationId);
			for (UsagePoint up : subscription.getUsagePoints()) {
				usagePoints.add(up.getId());
			}

		}

		exportEntriesFull(resourceService.findEntryTypeIterator(usagePoints,
				UsagePoint.class), outputStream, exportFilter, hrefFragment);
	}

	// export full usage point object tree (Download My Data)
	//
	@Override
	public void exportUsagePointsFull(Long subscriptionId,
			Long retailCustomerId, ServletOutputStream outputStream,
			ExportFilter exportFilter) throws IOException {
		// TODO right now subscriptionId is 1- ...
		String hrefFragment = "/Batch/RetailCustomer/" + retailCustomerId;
		// TODO: Implement modern export using DTOs
		// Legacy findEntryTypeIterator no longer supported
		// exportEntriesFull(subscriptionId, null, outputStream, exportFilter, hrefFragment);
	}

	@Override
	public void exportUsagePointFull(Long subscriptionId,
			Long retailCustomerId, Long usagePointId,
			ServletOutputStream outputStream, ExportFilter exportFilter)
			throws IOException {
		// TODO right now subscriptionId is 1- ...
		String hrefFragment = "/Batch/RetailCustomer/" + retailCustomerId
				+ "/UsagePoint/" + usagePointId;
		// TODO: Implement modern export using DTOs
		// Legacy findEntryTypeIterator no longer supported
		// exportEntriesFull(subscriptionId, null, outputStream, exportFilter, hrefFragment);

	}

	// worker functions
	//

	private void buildHeader(OutputStream stream, String hrefFragment)
			throws IOException {
		// TODO Remove the ApplicationInformation(1L) dependency
		String dataCustodianResourceEndpoint = resourceService.findById(1L,
				ApplicationInformation.class)
				.getDataCustodianResourceEndpoint();

		String selfRef = "<link href=\"" + dataCustodianResourceEndpoint
				+ hrefFragment + "\" rel=\"self\"/>";
		DateTimeType updated = DateConverter.toDateTimeType(new Date());
		String temp = updated.getValue().toXMLFormat();
		String uuid = UUID.randomUUID().toString();
		String certLink = "<link href=\"https://cert.greenbuttonalliance.org/certificate/" + Routes.GBA_CERT_ID + "\" rel=\"related\"/>\n";

		stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
		stream.write("<?xml-stylesheet type=\"text/xsl\" href=\"GreenButtonDataStyleSheet.xslt\"?>\n"
				.getBytes());
		stream.write("<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				.getBytes());
		stream.write("<id>urn:uuid:".getBytes());
		stream.write(uuid.getBytes());
		stream.write("</id>\n".getBytes());
		stream.write(certLink.getBytes());		
		stream.write("<title>Green Button Usage Feed</title>\n".getBytes());
		stream.write("<updated>".getBytes());
		stream.write(temp.getBytes());
		stream.write("</updated>\n".getBytes());
		stream.write(selfRef.getBytes());
	}

	@SuppressWarnings({"rawtypes"})
	private void exportEntries(EntryTypeIterator entries, OutputStream stream,
			ExportFilter exportFilter, Class resourceClass, String hrefFragment)
			throws IOException {
		exportEntries(0L, entries, stream, exportFilter, resourceClass,
				hrefFragment);
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	private void exportEntries(Long subscriptionId, EntryTypeIterator entries,
			OutputStream stream, ExportFilter exportFilter,
			Class resourceClass, String hrefFragment) throws IOException {

		buildHeader(stream, hrefFragment);
		// hrefFragment = adjustFragment(hrefFragment);
		if (entries != null) {

			while (entries.hasNext()) {
				try {
					EntryType entry = entries.nextEntry(resourceClass);
					// export the Entry and put the right tail on the URI
					exportEntry(
							subscriptionId,
							entry,
							stream,
							exportFilter,
							hrefFragment
									+ "/"
									+ entry.getContent().getContentId(
											resourceClass));
				} catch (Exception e) {
					if(logger.isErrorEnabled()) {
						logger.error("exportEntries: The requested collection contains no resources: " +
								hrefFragment + ": " + resourceClass.getSimpleName() + "&n");
					}
				}
			}
		}
		stream.write("</feed>".getBytes());
	}

	@SuppressWarnings({"rawtypes"})
	private void exportRootForm(String hrefFragment, Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter, Class targetClass)
			throws IOException {

		buildHeader(stream, hrefFragment);

		// start the recursion
		exportRootForm_Internal("/Subscription/" + subscriptionId,
				subscriptionId, Long.valueOf(0L), Long.valueOf(0L),
				Long.valueOf(0L), resourceService.findAllIds(UsagePoint.class),
				UsagePoint.class, targetClass, stream, exportFilter);

		stream.write("</feed>\n".getBytes());

	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void exportRootForm_Internal(String workingFragment,
			Long subscriptionId, Long retailCustomerId, Long usagePointId,
			Long meterReadingId, List<Long> resourceList, Class currentClass,
			Class targetClass, OutputStream stream, ExportFilter exportFilter) {

		List<Long> nextResourceList = new ArrayList<>();

		if (targetClass.equals(currentClass)) {
			// we are at the end; build the final fragment and export this leaf
			// of the tree
			workingFragment = workingFragment + "/" + targetClass.getName();
			try {
				exportEntries_Root(subscriptionId,
						resourceService.findEntryTypeIterator(resourceList,
								targetClass), stream, exportFilter,
						targetClass, workingFragment);
			} catch (IOException e) {
                if(logger.isErrorEnabled()) {
                    logger.error("**** exportRootForm_Internal Exception: " + e.toString() + "&n");
				e.printStackTrace();
                }
			}

		} else {

			for (Long id : resourceList) {

				if (currentClass.equals(UsagePoint.class)) {
					usagePointId = id;
					RetailCustomer retailCustomer = (resourceService.findById(
							usagePointId, UsagePoint.class))
							.getRetailCustomer();
					if (retailCustomer != null) {
						retailCustomerId = retailCustomer.getId();
					}
					if (!(targetClass.equals(IntervalBlock.class))) {
						// this covers /ElectricPowerUsageSummary
						// /ElectricPowerQualitySummary and /MeterReading
						nextResourceList = resourceService.findAllIdsByXPath(
								retailCustomerId, id, targetClass);
						exportRootForm_Internal(workingFragment
								+ "/UsagePoint/" + usagePointId,
								subscriptionId, retailCustomerId, usagePointId,
								meterReadingId, nextResourceList, targetClass,
								targetClass, stream, exportFilter);

					} else {
						// this covers /IntervalBlock
						for (Long id2 : resourceService.findAllIdsByXPath(
								retailCustomerId, id, MeterReading.class)) {

							nextResourceList = resourceService
									.findAllIdsByXPath(retailCustomerId,
											usagePointId, id2,
											IntervalBlock.class);
							exportRootForm_Internal(workingFragment
									+ "/MeterReading/" + id2, subscriptionId,
									retailCustomerId, usagePointId, id2,
									nextResourceList, IntervalBlock.class,
									targetClass, stream, exportFilter);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// TODO: this needs to be templated and RetailCustomer inherited from
	// IdentifiedObject to remove the above suppress warnings
	private void exportEntries_Root(Long subscriptionId,
			EntryTypeIterator entries, OutputStream stream,
			ExportFilter exportFilter, Class resourceClass, String hrefFragment)
			throws IOException {

		if (entries != null) {

			while (entries.hasNext()) {
				try {
					EntryType entry = entries.nextEntry(resourceClass);
					// export the Entry and put the right tail on the URI
					exportEntry(
							subscriptionId,
							entry,
							stream,
							exportFilter,
							hrefFragment
									+ "/"
									+ entry.getContent().getContentId(
											resourceClass));
				} catch (Exception e) {
					if(logger.isErrorEnabled()) {
						logger.error("exportEntries_Root: The requested collection contains no resources: " +
								hrefFragment + ": " + resourceClass.getSimpleName() + "&n");
					}
				}
			}
		}

	}

	private String adjustFragment(String fragment, EntryType entry) {

		String result = fragment;
		if (fragment.contains("DownloadMyData")) {
			result.replace("DownloadMyData", "UsagePoint");
		}
		if (fragment.contains("Batch")) {
			if (fragment.contains("Bulk")) {

				UsagePoint up = entry.getContent().getUsagePoint();
				RetailCustomer rc = up.getRetailCustomer();

				result = "/RetailCustomer/" + rc.getId() + "/UsagePoint";
			}
			if (fragment.contains("/Batch/RetailCustomer")) {
				result = fragment.replace("/Batch", "");
				if (!(fragment.contains("/UsagePoint"))) {
					result = result + "/UsagePoint";
				}
			}
		}
		return result;
	}

	// to export a single entry (w/o the <feed>...</feed> wrappers

	private void exportEntry(EntryType entry, OutputStream stream,
			ExportFilter exportFilter, String hrefFragment) throws IOException {
		exportEntry(0L, entry, stream, exportFilter, hrefFragment);
	}

	private void exportEntry(Long subscriptionId, EntryType entry,
			OutputStream stream, ExportFilter exportFilter, String hrefFragment)
			throws IOException {
		// TODO Remove the ApplicationInformation(1L) dependency
		String dataCustodianResourceEndpoint = (resourceService.findById(1L,
				ApplicationInformation.class))
				.getDataCustodianResourceEndpoint();

		AtomMarshallerListener uriListener = new AtomMarshallerListener(
				dataCustodianResourceEndpoint + hrefFragment);
		uriListener.setRelList(entry.getContent().buildRelHref(subscriptionId,
				dataCustodianResourceEndpoint + hrefFragment));
		uriListener.setSubscriptionId(subscriptionId);

		fragmentMarshaller.setMarshallerListener(uriListener);

		StreamResult result = new StreamResult(stream);
		try {
			if (exportFilter.matches(entry)) {
				fragmentMarshaller.marshal(entry, result);
			}
		} catch (Exception e) {
            if(logger.isErrorEnabled()) {
                logger.error("**** exportEntry Exception: " + e.toString() + "&n", e);
            }
		}
	}

	private void exportEntriesInternal(Long subscriptionId,
			EntryTypeIterator entries, OutputStream stream,
			ExportFilter exportFilter, String hrefFragment) throws IOException {
		while (entries.hasNext() == true) {
			try {
				EntryType entry = entries.next();
				// last minute fixup b/f doing the full dump
				// changing self of the feed to self of the root resource
				//
				if (entry.getContent().getUsagePoint() != null) {
					hrefFragment = adjustFragment(hrefFragment, entry);
				}
				exportEntryFull(subscriptionId, entry, stream, exportFilter,
						hrefFragment);
			} catch (Exception e) {
				if(logger.isErrorEnabled()) {
					logger.error("exportEntriesInternal: The requested collection contains no resources: " +
							hrefFragment + "&n");
				}

			}
		}
	}

	private void exportEntriesFull(EntryTypeIterator entries,
			OutputStream stream, ExportFilter exportFilter, String hrefFragment)
			throws IOException {
		exportEntriesFull(0L, entries, stream, exportFilter, hrefFragment);
	}

	private void exportEntriesFull(Long subscriptionId,
			EntryTypeIterator entries, OutputStream stream,
			ExportFilter exportFilter, String hrefFragment) throws IOException {

		buildHeader(stream, hrefFragment);

		exportEntriesInternal(subscriptionId, entries, stream, exportFilter,
				hrefFragment);

		stream.write("</feed>".getBytes());

	}

	private void exportEntryFull(Long subscriptionId, EntryType entry,
			OutputStream stream, ExportFilter exportFilter, String hrefFragment)
			throws IOException {

		// TODO Remove the ApplicationInformation(1L) dependency
		String dataCustodianResourceEndpoint = resourceService.findById(1L,
				ApplicationInformation.class)
				.getDataCustodianResourceEndpoint();

		//TODO Setup a listener so that the adapters may later be fed the fragment;
        AtomMarshallerListener uriListener = new AtomMarshallerListener(
				dataCustodianResourceEndpoint + hrefFragment);

		uriListener.setRelList(entry.getContent().buildRelHref(subscriptionId,
				dataCustodianResourceEndpoint + hrefFragment));

		uriListener.setSubscriptionId(subscriptionId);

		fragmentMarshaller.setMarshallerListener(uriListener);

		StreamResult result = new StreamResult(stream);
		try {
			if (exportFilter.matches(entry)) {
				fragmentMarshaller.marshal(entry, result);
			}
		} catch (Exception e) {
            if(logger.isErrorEnabled()) {
                logger.error("**** exportEntryFull Exception: " + e.toString() + "&n");
            }
			throw (e);
		}
	}

	@SuppressWarnings("unchecked")
	// TODO: need to make RetailCustomer inherit from IdentifiedObject to remove the above
	private EntryType findEntryTypeXPath(Long subscriptionId, Long id1,
			Long id2, Long id3, Long id4,
			@SuppressWarnings("rawtypes") Class clazz) {
		EntryType result = null;
		List<Long> temp = new ArrayList<>();
		Subscription subscription = null;

		try {

			if (subscriptionId != 0) {
				subscription = resourceService.findById(subscriptionId,
						Subscription.class);
				Authorization authorization = subscription.getAuthorization();
				if (!(authorization.getThirdParty()
						.contentEquals("third_party"))) {
					// a special case (client credentials base) access. So the
					// retailCustomerId is not
					// correct
					if (id2 != 0) {
						// we have a request for (at least) a usagePoint
						// so use the relevant retail customer Id to get the ID
						// collection
						UsagePoint usagePoint = resourceService.findById(id2,
								UsagePoint.class);
						id1 = usagePoint.getRetailCustomer().getId();
					}
				}
			}

			if (id4 != 0) {
				temp.add(resourceService.findIdByXPath(id1, id2, id3, id4,
						clazz));
			} else {
				if (id3 != 0) {
					temp.add(resourceService
							.findIdByXPath(id1, id2, id3, clazz));
				} else {
					if (id2 != 0) {
						temp.add(resourceService.findIdByXPath(id1, id2, clazz));
					} else {
						temp.add(resourceService.findIdByXPath(id1, clazz));
					}
				}
			}

		} catch (Exception e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** Error in Query: " + e.toString() + "&n");
			}
		}

		result = (new EntryTypeIterator(resourceService, temp, clazz)).nextEntry(clazz);

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// TODO: need to make RetailCustomer inherit from IdentifiedObject to remove
	// the above @Suppress
	private EntryTypeIterator findEntryTypeIteratorXPath(Long subscriptionId,
			Long id1, Long id2, Long id3, Class clazz) {
		EntryTypeIterator result = null;
		List<Long> temp = new ArrayList<Long>();
		Subscription subscription = null;
		boolean valid = false;

		try {

			if (!(subscriptionId.equals(0L))) {
				subscription = resourceService.findById(subscriptionId,Subscription.class);
				Authorization authorization = subscription.getAuthorization();

				if (!(authorization.getThirdParty().contentEquals("third_party")) &&
                        id2 != 0) {
					// a special case (client credentials base) access. So the retailCustomerId
                    // is not correct. We have a request for (at least) a usagePoint so use the
                    // relevant retail customer Id to get the ID collection
						UsagePoint usagePoint = resourceService.findById(id2,
								UsagePoint.class);
						id1 = usagePoint.getRetailCustomer().getId();
				}
			}

			// do we have a usagepointId?
			if (!(id2.equals(0L))) {
				// is it in the subscription?
				for (UsagePoint up : subscription.getUsagePoints()) {
					if (up.getId().equals(id2)) {
						valid = true;
					}
				}
			} else {
				// we don't have a usagePoint, so it is all usage points in the
				// subscription
				valid = true;
			}

			if (valid) {
				if (!(id3.equals(0L))) {
					temp = resourceService.findAllIdsByXPath(id1, id2, id3,
							clazz);
				} else {
					if (!(id2.equals(0L))) {
						temp = resourceService.findAllIdsByXPath(id1, id2,
								clazz);
					} else {
						if (!(id1.equals(0L))) {
							// we just want the UsagePoints in the Subscription
							for (UsagePoint up : subscription.getUsagePoints()) {
								temp.add(up.getId());
							}
						} else {
							// otherwise, just get all the RetailCustomers
							temp = resourceService
									.findAllIdsByXPath(RetailCustomer.class);
						}
					}
				}
			}

			result = (new EntryTypeIterator(resourceService, temp, clazz));

		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("**** Error in Query: " + e.toString() + "&n");
			}
			result = null;
		}
		return result;
	}

}