
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

package org.naesb.req21.espi.ver3_3.usage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.naesb.req21.espi.ver3_3.energy package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IdentifiedObject_QNAME = new QName("http://naesb.org/espi", "IdentifiedObject");
    private final static QName _ApplicationInformation_QNAME = new QName("http://naesb.org/espi", "ApplicationInformation");
    private final static QName _ReadingQuality_QNAME = new QName("http://naesb.org/espi", "ReadingQuality");
    private final static QName _SummaryMeasurement_QNAME = new QName("http://naesb.org/espi", "SummaryMeasurement");
    private final static QName _Object_QNAME = new QName("http://naesb.org/espi", "Object");
    private final static QName _ProgramIdMappings_QNAME = new QName("http://naesb.org/espi", "ProgramIdMappings");
    private final static QName _IntervalReading_QNAME = new QName("http://naesb.org/espi", "IntervalReading");
    private final static QName _MeterReading_QNAME = new QName("http://naesb.org/espi", "MeterReading");
    private final static QName _ElectricPowerUsageSummary_QNAME = new QName("http://naesb.org/espi", "ElectricPowerUsageSummary");
    private final static QName _ServiceStatus_QNAME = new QName("http://naesb.org/espi", "ServiceStatus");
    private final static QName _Authorization_QNAME = new QName("http://naesb.org/espi", "Authorization");
    private final static QName _ReadingType_QNAME = new QName("http://naesb.org/espi", "ReadingType");
    private final static QName _UsageSummary_QNAME = new QName("http://naesb.org/espi", "UsageSummary");
    private final static QName _BatchList_QNAME = new QName("http://naesb.org/espi", "BatchList");
    private final static QName _DateTimeInterval_QNAME = new QName("http://naesb.org/espi", "DateTimeInterval");
    private final static QName _Feed_QNAME = new QName("http://www.w3.org/2005/Atom", "feed");
    private final static QName _Entry_QNAME = new QName("http://www.w3.org/2005/Atom", "entry");
    private final static QName _BatchItemInfo_QNAME = new QName("http://naesb.org/espi", "BatchItemInfo");
    private final static QName _LocalTimeParameters_QNAME = new QName("http://naesb.org/espi", "LocalTimeParameters");
    private final static QName _IntervalBlock_QNAME = new QName("http://naesb.org/espi", "IntervalBlock");
    private final static QName _ElectricPowerQualitySummary_QNAME = new QName("http://naesb.org/espi", "ElectricPowerQualitySummary");
    private final static QName _UsagePoint_QNAME = new QName("http://naesb.org/espi", "UsagePoint");
    private final static QName _SourceTypeRights_QNAME = new QName("http://www.w3.org/2005/Atom", "rights");
    private final static QName _SourceTypeSubtitle_QNAME = new QName("http://www.w3.org/2005/Atom", "subtitle");
    private final static QName _SourceTypeAuthor_QNAME = new QName("http://www.w3.org/2005/Atom", "author");
    private final static QName _SourceTypeContributor_QNAME = new QName("http://www.w3.org/2005/Atom", "contributor");
    private final static QName _SourceTypeUpdated_QNAME = new QName("http://www.w3.org/2005/Atom", "updated");
    private final static QName _SourceTypeCategory_QNAME = new QName("http://www.w3.org/2005/Atom", "category");
    private final static QName _SourceTypeTitle_QNAME = new QName("http://www.w3.org/2005/Atom", "title");
    private final static QName _SourceTypeId_QNAME = new QName("http://www.w3.org/2005/Atom", "id");
    private final static QName _SourceTypeGenerator_QNAME = new QName("http://www.w3.org/2005/Atom", "generator");
    private final static QName _SourceTypeLogo_QNAME = new QName("http://www.w3.org/2005/Atom", "logo");
    private final static QName _SourceTypeIcon_QNAME = new QName("http://www.w3.org/2005/Atom", "icon");
    private final static QName _SourceTypeLink_QNAME = new QName("http://www.w3.org/2005/Atom", "link");
    private final static QName _EntryTypeSummary_QNAME = new QName("http://www.w3.org/2005/Atom", "summary");
    private final static QName _EntryTypeContent_QNAME = new QName("http://www.w3.org/2005/Atom", "content");
    private final static QName _EntryTypePublished_QNAME = new QName("http://www.w3.org/2005/Atom", "published");
    private final static QName _EntryTypeSource_QNAME = new QName("http://www.w3.org/2005/Atom", "source");
    private final static QName _PersonTypeEmail_QNAME = new QName("http://www.w3.org/2005/Atom", "email");
    private final static QName _PersonTypeUri_QNAME = new QName("http://www.w3.org/2005/Atom", "uri");
    private final static QName _PersonTypeName_QNAME = new QName("http://www.w3.org/2005/Atom", "name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.naesb.req21.espi.ver3_3.energy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProgramIdMappings }
     * 
     */
    public ProgramIdMappings createProgramIdMappings() {
        return new ProgramIdMappings();
    }

    /**
     * Create an instance of {@link IntervalBlock }
     * 
     */
    public IntervalBlock createIntervalBlock() {
        return new IntervalBlock();
    }

    /**
     * Create an instance of {@link ElectricPowerQualitySummary }
     * 
     */
    public ElectricPowerQualitySummary createElectricPowerQualitySummary() {
        return new ElectricPowerQualitySummary();
    }

    /**
     * Create an instance of {@link UsagePoint }
     * 
     */
    public UsagePoint createUsagePoint() {
        return new UsagePoint();
    }

    /**
     * Create an instance of {@link DateTimeInterval }
     * 
     */
    public DateTimeInterval createDateTimeInterval() {
        return new DateTimeInterval();
    }

    /**
     * Create an instance of {@link BatchItemInfo }
     * 
     */
    public BatchItemInfo createBatchItemInfo() {
        return new BatchItemInfo();
    }

    /**
     * Create an instance of {@link TimeConfiguration }
     * 
     */
    public TimeConfiguration createTimeConfiguration() {
        return new TimeConfiguration();
    }

    /**
     * Create an instance of {@link BatchListType }
     * 
     */
    public BatchListType createBatchListType() {
        return new BatchListType();
    }

    /**
     * Create an instance of {@link UsageSummary }
     * 
     */
    public UsageSummary createUsageSummary() {
        return new UsageSummary();
    }

    /**
     * Create an instance of {@link Authorization }
     * 
     */
    public Authorization createAuthorization() {
        return new Authorization();
    }

    /**
     * Create an instance of {@link ReadingType }
     * 
     */
    public ReadingType createReadingType() {
        return new ReadingType();
    }

    /**
     * Create an instance of {@link ServiceStatus }
     * 
     */
    public ServiceStatus createServiceStatus() {
        return new ServiceStatus();
    }

    /**
     * Create an instance of {@link IntervalReading }
     * 
     */
    public IntervalReading createIntervalReading() {
        return new IntervalReading();
    }

    /**
     * Create an instance of {@link MeterReading }
     * 
     */
    public MeterReading createMeterReading() {
        return new MeterReading();
    }

    /**
     * Create an instance of {@link ElectricPowerUsageSummary }
     * 
     */
    public ElectricPowerUsageSummary createElectricPowerUsageSummary() {
        return new ElectricPowerUsageSummary();
    }

    /**
     * Create an instance of {@link ReadingQuality }
     * 
     */
    public ReadingQuality createReadingQuality() {
        return new ReadingQuality();
    }

    /**
     * Create an instance of {@link SummaryMeasurement }
     * 
     */
    public SummaryMeasurement createSummaryMeasurement() {
        return new SummaryMeasurement();
    }

    /**
     * Create an instance of {@link Object }
     * 
     */
    public Object createObject() {
        return new Object();
    }

    /**
     * Create an instance of {@link IdentifiedObject }
     * 
     */
    public IdentifiedObject createIdentifiedObject() {
        return new IdentifiedObject();
    }

    /**
     * Create an instance of {@link ApplicationInformation }
     * 
     */
    public ApplicationInformation createApplicationInformation() {
        return new ApplicationInformation();
    }

    /**
     * Create an instance of {@link PnodeRef }
     * 
     */
    public PnodeRef createPnodeRef() {
        return new PnodeRef();
    }

    /**
     * Create an instance of {@link ReadingInterharmonic }
     * 
     */
    public ReadingInterharmonic createReadingInterharmonic() {
        return new ReadingInterharmonic();
    }

    /**
     * Create an instance of {@link ServiceCategory }
     * 
     */
    public ServiceCategory createServiceCategory() {
        return new ServiceCategory();
    }

    /**
     * Create an instance of {@link TariffRiderRef }
     * 
     */
    public TariffRiderRef createTariffRiderRef() {
        return new TariffRiderRef();
    }

    /**
     * Create an instance of {@link RationalNumber }
     * 
     */
    public RationalNumber createRationalNumber() {
        return new RationalNumber();
    }

    /**
     * Create an instance of {@link PnodeRefs }
     * 
     */
    public PnodeRefs createPnodeRefs() {
        return new PnodeRefs();
    }

    /**
     * Create an instance of {@link AggregateNodeRefs }
     * 
     */
    public AggregateNodeRefs createAggregateNodeRefs() {
        return new AggregateNodeRefs();
    }

    /**
     * Create an instance of {@link LineItem }
     * 
     */
    public LineItem createLineItem() {
        return new LineItem();
    }

    /**
     * Create an instance of {@link TariffRiderRefs }
     * 
     */
    public TariffRiderRefs createTariffRiderRefs() {
        return new TariffRiderRefs();
    }

    /**
     * Create an instance of {@link ServiceDeliveryPoint }
     * 
     */
    public ServiceDeliveryPoint createServiceDeliveryPoint() {
        return new ServiceDeliveryPoint();
    }

    /**
     * Create an instance of {@link BillingChargeSource }
     * 
     */
    public BillingChargeSource createBillingChargeSource() {
        return new BillingChargeSource();
    }

    /**
     * Create an instance of {@link AggregateNodeRef }
     * 
     */
    public AggregateNodeRef createAggregateNodeRef() {
        return new AggregateNodeRef();
    }

    /**
     * Create an instance of {@link FeedType }
     * 
     */
    public FeedType createFeedType() {
        return new FeedType();
    }

    /**
     * Create an instance of {@link EntryType }
     * 
     */
    public EntryType createEntryType() {
        return new EntryType();
    }

    /**
     * Create an instance of {@link IdType }
     * 
     */
    public IdType createIdType() {
        return new IdType();
    }

    /**
     * Create an instance of {@link TextType }
     * 
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link DateTimeType }
     * 
     */
    public DateTimeType createDateTimeType() {
        return new DateTimeType();
    }

    /**
     * Create an instance of {@link GeneratorType }
     * 
     */
    public GeneratorType createGeneratorType() {
        return new GeneratorType();
    }

    /**
     * Create an instance of {@link CategoryType }
     * 
     */
    public CategoryType createCategoryType() {
        return new CategoryType();
    }

    /**
     * Create an instance of {@link SourceType }
     * 
     */
    public SourceType createSourceType() {
        return new SourceType();
    }

    /**
     * Create an instance of {@link IconType }
     * 
     */
    public IconType createIconType() {
        return new IconType();
    }

    /**
     * Create an instance of {@link UriType }
     * 
     */
    public UriType createUriType() {
        return new UriType();
    }

    /**
     * Create an instance of {@link LinkType }
     * 
     */
    public LinkType createLinkType() {
        return new LinkType();
    }

    /**
     * Create an instance of {@link PersonType }
     * 
     */
    public PersonType createPersonType() {
        return new PersonType();
    }

    /**
     * Create an instance of {@link LogoType }
     * 
     */
    public LogoType createLogoType() {
        return new LogoType();
    }

    /**
     * Create an instance of {@link ContentType }
     * 
     */
    public ContentType createContentType() {
        return new ContentType();
    }

    /**
     * Create an instance of {@link ProgramIdMappings.ProgramIdMapping }
     * 
     */
    public ProgramIdMappings.ProgramIdMapping createProgramIdMappingsProgramIdMapping() {
        return new ProgramIdMappings.ProgramIdMapping();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifiedObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "IdentifiedObject")
    public JAXBElement<IdentifiedObject> createIdentifiedObject(IdentifiedObject value) {
        return new JAXBElement<IdentifiedObject>(_IdentifiedObject_QNAME, IdentifiedObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "ApplicationInformation")
    public JAXBElement<ApplicationInformation> createApplicationInformation(ApplicationInformation value) {
        return new JAXBElement<ApplicationInformation>(_ApplicationInformation_QNAME, ApplicationInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadingQuality }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "ReadingQuality")
    public JAXBElement<ReadingQuality> createReadingQuality(ReadingQuality value) {
        return new JAXBElement<ReadingQuality>(_ReadingQuality_QNAME, ReadingQuality.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SummaryMeasurement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "SummaryMeasurement")
    public JAXBElement<SummaryMeasurement> createSummaryMeasurement(SummaryMeasurement value) {
        return new JAXBElement<SummaryMeasurement>(_SummaryMeasurement_QNAME, SummaryMeasurement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "Object")
    public JAXBElement<Object> createObject(Object value) {
        return new JAXBElement<Object>(_Object_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramIdMappings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "ProgramIdMappings")
    public JAXBElement<ProgramIdMappings> createProgramIdMappings(ProgramIdMappings value) {
        return new JAXBElement<ProgramIdMappings>(_ProgramIdMappings_QNAME, ProgramIdMappings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntervalReading }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "IntervalReading")
    public JAXBElement<IntervalReading> createIntervalReading(IntervalReading value) {
        return new JAXBElement<IntervalReading>(_IntervalReading_QNAME, IntervalReading.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeterReading }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "MeterReading")
    public JAXBElement<MeterReading> createMeterReading(MeterReading value) {
        return new JAXBElement<MeterReading>(_MeterReading_QNAME, MeterReading.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElectricPowerUsageSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "ElectricPowerUsageSummary")
    public JAXBElement<ElectricPowerUsageSummary> createElectricPowerUsageSummary(ElectricPowerUsageSummary value) {
        return new JAXBElement<ElectricPowerUsageSummary>(_ElectricPowerUsageSummary_QNAME, ElectricPowerUsageSummary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "ServiceStatus")
    public JAXBElement<ServiceStatus> createServiceStatus(ServiceStatus value) {
        return new JAXBElement<ServiceStatus>(_ServiceStatus_QNAME, ServiceStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Authorization }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "Authorization")
    public JAXBElement<Authorization> createAuthorization(Authorization value) {
        return new JAXBElement<Authorization>(_Authorization_QNAME, Authorization.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadingType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "ReadingType")
    public JAXBElement<ReadingType> createReadingType(ReadingType value) {
        return new JAXBElement<ReadingType>(_ReadingType_QNAME, ReadingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsageSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "UsageSummary")
    public JAXBElement<UsageSummary> createUsageSummary(UsageSummary value) {
        return new JAXBElement<UsageSummary>(_UsageSummary_QNAME, UsageSummary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BatchListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "BatchList")
    public JAXBElement<BatchListType> createBatchList(BatchListType value) {
        return new JAXBElement<BatchListType>(_BatchList_QNAME, BatchListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeInterval }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "DateTimeInterval")
    public JAXBElement<DateTimeInterval> createDateTimeInterval(DateTimeInterval value) {
        return new JAXBElement<DateTimeInterval>(_DateTimeInterval_QNAME, DateTimeInterval.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FeedType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "feed")
    public JAXBElement<FeedType> createFeed(FeedType value) {
        return new JAXBElement<FeedType>(_Feed_QNAME, FeedType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "entry")
    public JAXBElement<EntryType> createEntry(EntryType value) {
        return new JAXBElement<EntryType>(_Entry_QNAME, EntryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BatchItemInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "BatchItemInfo")
    public JAXBElement<BatchItemInfo> createBatchItemInfo(BatchItemInfo value) {
        return new JAXBElement<BatchItemInfo>(_BatchItemInfo_QNAME, BatchItemInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "LocalTimeParameters")
    public JAXBElement<TimeConfiguration> createLocalTimeParameters(TimeConfiguration value) {
        return new JAXBElement<TimeConfiguration>(_LocalTimeParameters_QNAME, TimeConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntervalBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "IntervalBlock")
    public JAXBElement<IntervalBlock> createIntervalBlock(IntervalBlock value) {
        return new JAXBElement<IntervalBlock>(_IntervalBlock_QNAME, IntervalBlock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElectricPowerQualitySummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "ElectricPowerQualitySummary")
    public JAXBElement<ElectricPowerQualitySummary> createElectricPowerQualitySummary(ElectricPowerQualitySummary value) {
        return new JAXBElement<ElectricPowerQualitySummary>(_ElectricPowerQualitySummary_QNAME, ElectricPowerQualitySummary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsagePoint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi", name = "UsagePoint")
    public JAXBElement<UsagePoint> createUsagePoint(UsagePoint value) {
        return new JAXBElement<UsagePoint>(_UsagePoint_QNAME, UsagePoint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights", scope = SourceType.class)
    public JAXBElement<TextType> createSourceTypeRights(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeRights_QNAME, TextType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "subtitle", scope = SourceType.class)
    public JAXBElement<TextType> createSourceTypeSubtitle(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeSubtitle_QNAME, TextType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "author", scope = SourceType.class)
    public JAXBElement<PersonType> createSourceTypeAuthor(PersonType value) {
        return new JAXBElement<PersonType>(_SourceTypeAuthor_QNAME, PersonType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "contributor", scope = SourceType.class)
    public JAXBElement<PersonType> createSourceTypeContributor(PersonType value) {
        return new JAXBElement<PersonType>(_SourceTypeContributor_QNAME, PersonType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "updated", scope = SourceType.class)
    public JAXBElement<DateTimeType> createSourceTypeUpdated(DateTimeType value) {
        return new JAXBElement<DateTimeType>(_SourceTypeUpdated_QNAME, DateTimeType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "category", scope = SourceType.class)
    public JAXBElement<CategoryType> createSourceTypeCategory(CategoryType value) {
        return new JAXBElement<CategoryType>(_SourceTypeCategory_QNAME, CategoryType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title", scope = SourceType.class)
    public JAXBElement<TextType> createSourceTypeTitle(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeTitle_QNAME, TextType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "id", scope = SourceType.class)
    public JAXBElement<IdType> createSourceTypeId(IdType value) {
        return new JAXBElement<IdType>(_SourceTypeId_QNAME, IdType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneratorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "generator", scope = SourceType.class)
    public JAXBElement<GeneratorType> createSourceTypeGenerator(GeneratorType value) {
        return new JAXBElement<GeneratorType>(_SourceTypeGenerator_QNAME, GeneratorType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "logo", scope = SourceType.class)
    public JAXBElement<LogoType> createSourceTypeLogo(LogoType value) {
        return new JAXBElement<LogoType>(_SourceTypeLogo_QNAME, LogoType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IconType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "icon", scope = SourceType.class)
    public JAXBElement<IconType> createSourceTypeIcon(IconType value) {
        return new JAXBElement<IconType>(_SourceTypeIcon_QNAME, IconType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "link", scope = SourceType.class)
    public JAXBElement<LinkType> createSourceTypeLink(LinkType value) {
        return new JAXBElement<LinkType>(_SourceTypeLink_QNAME, LinkType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights", scope = EntryType.class)
    public JAXBElement<TextType> createEntryTypeRights(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeRights_QNAME, TextType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "author", scope = EntryType.class)
    public JAXBElement<PersonType> createEntryTypeAuthor(PersonType value) {
        return new JAXBElement<PersonType>(_SourceTypeAuthor_QNAME, PersonType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "contributor", scope = EntryType.class)
    public JAXBElement<PersonType> createEntryTypeContributor(PersonType value) {
        return new JAXBElement<PersonType>(_SourceTypeContributor_QNAME, PersonType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "summary", scope = EntryType.class)
    public JAXBElement<TextType> createEntryTypeSummary(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeSummary_QNAME, TextType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "content", scope = EntryType.class)
    public JAXBElement<ContentType> createEntryTypeContent(ContentType value) {
        return new JAXBElement<ContentType>(_EntryTypeContent_QNAME, ContentType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "updated", scope = EntryType.class)
    public JAXBElement<DateTimeType> createEntryTypeUpdated(DateTimeType value) {
        return new JAXBElement<DateTimeType>(_SourceTypeUpdated_QNAME, DateTimeType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "category", scope = EntryType.class)
    public JAXBElement<CategoryType> createEntryTypeCategory(CategoryType value) {
        return new JAXBElement<CategoryType>(_SourceTypeCategory_QNAME, CategoryType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title", scope = EntryType.class)
    public JAXBElement<TextType> createEntryTypeTitle(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeTitle_QNAME, TextType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "id", scope = EntryType.class)
    public JAXBElement<IdType> createEntryTypeId(IdType value) {
        return new JAXBElement<IdType>(_SourceTypeId_QNAME, IdType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "published", scope = EntryType.class)
    public JAXBElement<DateTimeType> createEntryTypePublished(DateTimeType value) {
        return new JAXBElement<DateTimeType>(_EntryTypePublished_QNAME, DateTimeType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "source", scope = EntryType.class)
    public JAXBElement<TextType> createEntryTypeSource(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeSource_QNAME, TextType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "link", scope = EntryType.class)
    public JAXBElement<LinkType> createEntryTypeLink(LinkType value) {
        return new JAXBElement<LinkType>(_SourceTypeLink_QNAME, LinkType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "author", scope = FeedType.class)
    public JAXBElement<PersonType> createFeedTypeAuthor(PersonType value) {
        return new JAXBElement<PersonType>(_SourceTypeAuthor_QNAME, PersonType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title", scope = FeedType.class)
    public JAXBElement<TextType> createFeedTypeTitle(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeTitle_QNAME, TextType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneratorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "generator", scope = FeedType.class)
    public JAXBElement<GeneratorType> createFeedTypeGenerator(GeneratorType value) {
        return new JAXBElement<GeneratorType>(_SourceTypeGenerator_QNAME, GeneratorType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IconType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "icon", scope = FeedType.class)
    public JAXBElement<IconType> createFeedTypeIcon(IconType value) {
        return new JAXBElement<IconType>(_SourceTypeIcon_QNAME, IconType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "link", scope = FeedType.class)
    public JAXBElement<LinkType> createFeedTypeLink(LinkType value) {
        return new JAXBElement<LinkType>(_SourceTypeLink_QNAME, LinkType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights", scope = FeedType.class)
    public JAXBElement<TextType> createFeedTypeRights(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeRights_QNAME, TextType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "subtitle", scope = FeedType.class)
    public JAXBElement<TextType> createFeedTypeSubtitle(TextType value) {
        return new JAXBElement<TextType>(_SourceTypeSubtitle_QNAME, TextType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "contributor", scope = FeedType.class)
    public JAXBElement<PersonType> createFeedTypeContributor(PersonType value) {
        return new JAXBElement<PersonType>(_SourceTypeContributor_QNAME, PersonType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "entry", scope = FeedType.class)
    public JAXBElement<EntryType> createFeedTypeEntry(EntryType value) {
        return new JAXBElement<EntryType>(_Entry_QNAME, EntryType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "updated", scope = FeedType.class)
    public JAXBElement<DateTimeType> createFeedTypeUpdated(DateTimeType value) {
        return new JAXBElement<DateTimeType>(_SourceTypeUpdated_QNAME, DateTimeType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "category", scope = FeedType.class)
    public JAXBElement<CategoryType> createFeedTypeCategory(CategoryType value) {
        return new JAXBElement<CategoryType>(_SourceTypeCategory_QNAME, CategoryType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "id", scope = FeedType.class)
    public JAXBElement<IdType> createFeedTypeId(IdType value) {
        return new JAXBElement<IdType>(_SourceTypeId_QNAME, IdType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "logo", scope = FeedType.class)
    public JAXBElement<LogoType> createFeedTypeLogo(LogoType value) {
        return new JAXBElement<LogoType>(_SourceTypeLogo_QNAME, LogoType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "email", scope = PersonType.class)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createPersonTypeEmail(String value) {
        return new JAXBElement<String>(_PersonTypeEmail_QNAME, String.class, PersonType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UriType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "uri", scope = PersonType.class)
    public JAXBElement<UriType> createPersonTypeUri(UriType value) {
        return new JAXBElement<UriType>(_PersonTypeUri_QNAME, UriType.class, PersonType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "name", scope = PersonType.class)
    public JAXBElement<String> createPersonTypeName(String value) {
        return new JAXBElement<String>(_PersonTypeName_QNAME, String.class, PersonType.class, value);
    }

}
