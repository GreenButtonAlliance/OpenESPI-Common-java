
/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.naesb.req21.espi.ver3_3.retailcustomer;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.naesb.req21.espi.ver3_3.retailcustomer package. 
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

    private final static QName _Status_QNAME = new QName("http://naesb.org/espi/customer", "Status");
    private final static QName _Priority_QNAME = new QName("http://naesb.org/espi/customer", "Priority");
    private final static QName _Organisation_QNAME = new QName("http://naesb.org/espi/customer", "Organisation");
    private final static QName _BatchItemInfo_QNAME = new QName("http://naesb.org/espi/customer", "BatchItemInfo");
    private final static QName _CustomerAccount_QNAME = new QName("http://naesb.org/espi/customer", "CustomerAccount");
    private final static QName _LocalTimeParameters_QNAME = new QName("http://naesb.org/espi/customer", "LocalTimeParameters");
    private final static QName _DateTimeInterval_QNAME = new QName("http://naesb.org/espi/customer", "DateTimeInterval");
    private final static QName _ElectronicAddress_QNAME = new QName("http://naesb.org/espi/customer", "ElectronicAddress");
    private final static QName _DemandResponseProgram_QNAME = new QName("http://naesb.org/espi/customer", "DemandResponseProgram");
    private final static QName _OrganisationRole_QNAME = new QName("http://naesb.org/espi/customer", "OrganisationRole");
    private final static QName _ProgramDate_QNAME = new QName("http://naesb.org/espi/customer", "ProgramDate");
    private final static QName _StreetAddress_QNAME = new QName("http://naesb.org/espi/customer", "StreetAddress");
    private final static QName _StreetDetail_QNAME = new QName("http://naesb.org/espi/customer", "StreetDetail");
    private final static QName _Agreement_QNAME = new QName("http://naesb.org/espi/customer", "Agreement");
    private final static QName _Object_QNAME = new QName("http://naesb.org/espi/customer", "Object");
    private final static QName _SummaryMeasurement_QNAME = new QName("http://naesb.org/espi/customer", "SummaryMeasurement");
    private final static QName _AssetContainer_QNAME = new QName("http://naesb.org/espi/customer", "AssetContainer");
    private final static QName _Location_QNAME = new QName("http://naesb.org/espi/customer", "Location");
    private final static QName _IdentifiedObject_QNAME = new QName("http://naesb.org/espi/customer", "IdentifiedObject");
    private final static QName _EndDevice_QNAME = new QName("http://naesb.org/espi/customer", "EndDevice");
    private final static QName _CustomerAgreement_QNAME = new QName("http://naesb.org/espi/customer", "CustomerAgreement");
    private final static QName _PricingStructure_QNAME = new QName("http://naesb.org/espi/customer", "PricingStructure");
    private final static QName _UsagePoint_QNAME = new QName("http://naesb.org/espi/customer", "UsagePoint");
    private final static QName _WorkLocation_QNAME = new QName("http://naesb.org/espi/customer", "WorkLocation");
    private final static QName _Customer_QNAME = new QName("http://naesb.org/espi/customer", "Customer");
    private final static QName _Meter_QNAME = new QName("http://naesb.org/espi/customer", "Meter");
    private final static QName _ServiceSupplier_QNAME = new QName("http://naesb.org/espi/customer", "ServiceSupplier");
    private final static QName _ProgramDateIdMapping_QNAME = new QName("http://naesb.org/espi/customer", "ProgramDateIdMapping");
    private final static QName _AccountNotification_QNAME = new QName("http://naesb.org/espi/customer", "AccountNotification");
    private final static QName _BatchList_QNAME = new QName("http://naesb.org/espi/customer", "BatchList");
    private final static QName _TownDetail_QNAME = new QName("http://naesb.org/espi/customer", "TownDetail");
    private final static QName _MeterMultiplier_QNAME = new QName("http://naesb.org/espi/customer", "MeterMultiplier");
    private final static QName _Document_QNAME = new QName("http://naesb.org/espi/customer", "Document");
    private final static QName _TelephoneNumber_QNAME = new QName("http://naesb.org/espi/customer", "TelephoneNumber");
    private final static QName _PositionPoint_QNAME = new QName("http://naesb.org/espi/customer", "PositionPoint");
    private final static QName _ServiceLocation_QNAME = new QName("http://naesb.org/espi/customer", "ServiceLocation");
    private final static QName _Feed_QNAME = new QName("http://www.w3.org/2005/Atom", "feed");
    private final static QName _Entry_QNAME = new QName("http://www.w3.org/2005/Atom", "entry");
    private final static QName _AcceptanceTest_QNAME = new QName("http://naesb.org/espi/customer", "AcceptanceTest");
    private final static QName _Asset_QNAME = new QName("http://naesb.org/espi/customer", "Asset");
    private final static QName _TimeConfiguration_QNAME = new QName("http://naesb.org/espi/customer", "TimeConfiguration");
    private final static QName _UsagePoints_QNAME = new QName("http://naesb.org/espi/customer", "UsagePoints");
    private final static QName _ProgramDateIdMappings_QNAME = new QName("http://naesb.org/espi/customer", "ProgramDateIdMappings");
    private final static QName _LifecycleDate_QNAME = new QName("http://naesb.org/espi/customer", "LifecycleDate");
    private final static QName _EntryTypeRights_QNAME = new QName("http://www.w3.org/2005/Atom", "rights");
    private final static QName _EntryTypeAuthor_QNAME = new QName("http://www.w3.org/2005/Atom", "author");
    private final static QName _EntryTypeContributor_QNAME = new QName("http://www.w3.org/2005/Atom", "contributor");
    private final static QName _EntryTypeSummary_QNAME = new QName("http://www.w3.org/2005/Atom", "summary");
    private final static QName _EntryTypeContent_QNAME = new QName("http://www.w3.org/2005/Atom", "content");
    private final static QName _EntryTypeUpdated_QNAME = new QName("http://www.w3.org/2005/Atom", "updated");
    private final static QName _EntryTypeCategory_QNAME = new QName("http://www.w3.org/2005/Atom", "category");
    private final static QName _EntryTypeTitle_QNAME = new QName("http://www.w3.org/2005/Atom", "title");
    private final static QName _EntryTypeId_QNAME = new QName("http://www.w3.org/2005/Atom", "id");
    private final static QName _EntryTypePublished_QNAME = new QName("http://www.w3.org/2005/Atom", "published");
    private final static QName _EntryTypeSource_QNAME = new QName("http://www.w3.org/2005/Atom", "source");
    private final static QName _EntryTypeLink_QNAME = new QName("http://www.w3.org/2005/Atom", "link");
    private final static QName _PersonTypeEmail_QNAME = new QName("http://www.w3.org/2005/Atom", "email");
    private final static QName _PersonTypeUri_QNAME = new QName("http://www.w3.org/2005/Atom", "uri");
    private final static QName _PersonTypeName_QNAME = new QName("http://www.w3.org/2005/Atom", "name");
    private final static QName _SourceTypeSubtitle_QNAME = new QName("http://www.w3.org/2005/Atom", "subtitle");
    private final static QName _SourceTypeGenerator_QNAME = new QName("http://www.w3.org/2005/Atom", "generator");
    private final static QName _SourceTypeLogo_QNAME = new QName("http://www.w3.org/2005/Atom", "logo");
    private final static QName _SourceTypeIcon_QNAME = new QName("http://www.w3.org/2005/Atom", "icon");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.naesb.req21.espi.ver3_3.retailcustomer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CustomerAgreement }
     * 
     */
    public CustomerAgreement createCustomerAgreement() {
        return new CustomerAgreement();
    }

    /**
     * Create an instance of {@link EndDevice }
     * 
     */
    public EndDevice createEndDevice() {
        return new EndDevice();
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link Meter }
     * 
     */
    public Meter createMeter() {
        return new Meter();
    }

    /**
     * Create an instance of {@link PricingStructure }
     * 
     */
    public PricingStructure createPricingStructure() {
        return new PricingStructure();
    }

    /**
     * Create an instance of {@link UsagePoint }
     * 
     */
    public UsagePoint createUsagePoint() {
        return new UsagePoint();
    }

    /**
     * Create an instance of {@link WorkLocation }
     * 
     */
    public WorkLocation createWorkLocation() {
        return new WorkLocation();
    }

    /**
     * Create an instance of {@link ServiceSupplier }
     * 
     */
    public ServiceSupplier createServiceSupplier() {
        return new ServiceSupplier();
    }

    /**
     * Create an instance of {@link ProgramDateIdMapping }
     * 
     */
    public ProgramDateIdMapping createProgramDateIdMapping() {
        return new ProgramDateIdMapping();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link TelephoneNumber }
     * 
     */
    public TelephoneNumber createTelephoneNumber() {
        return new TelephoneNumber();
    }

    /**
     * Create an instance of {@link AccountNotification }
     * 
     */
    public AccountNotification createAccountNotification() {
        return new AccountNotification();
    }

    /**
     * Create an instance of {@link BatchListType }
     * 
     */
    public BatchListType createBatchListType() {
        return new BatchListType();
    }

    /**
     * Create an instance of {@link TownDetail }
     * 
     */
    public TownDetail createTownDetail() {
        return new TownDetail();
    }

    /**
     * Create an instance of {@link MeterMultiplier }
     * 
     */
    public MeterMultiplier createMeterMultiplier() {
        return new MeterMultiplier();
    }

    /**
     * Create an instance of {@link PositionPoint }
     * 
     */
    public PositionPoint createPositionPoint() {
        return new PositionPoint();
    }

    /**
     * Create an instance of {@link ServiceLocation }
     * 
     */
    public ServiceLocation createServiceLocation() {
        return new ServiceLocation();
    }

    /**
     * Create an instance of {@link Asset }
     * 
     */
    public Asset createAsset() {
        return new Asset();
    }

    /**
     * Create an instance of {@link TimeConfiguration }
     * 
     */
    public TimeConfiguration createTimeConfiguration() {
        return new TimeConfiguration();
    }

    /**
     * Create an instance of {@link AcceptanceTest }
     * 
     */
    public AcceptanceTest createAcceptanceTest() {
        return new AcceptanceTest();
    }

    /**
     * Create an instance of {@link LifecycleDate }
     * 
     */
    public LifecycleDate createLifecycleDate() {
        return new LifecycleDate();
    }

    /**
     * Create an instance of {@link UsagePoints }
     * 
     */
    public UsagePoints createUsagePoints() {
        return new UsagePoints();
    }

    /**
     * Create an instance of {@link ProgramDateIdMappings }
     * 
     */
    public ProgramDateIdMappings createProgramDateIdMappings() {
        return new ProgramDateIdMappings();
    }

    /**
     * Create an instance of {@link Status }
     * 
     */
    public Status createStatus() {
        return new Status();
    }

    /**
     * Create an instance of {@link Organisation }
     * 
     */
    public Organisation createOrganisation() {
        return new Organisation();
    }

    /**
     * Create an instance of {@link Priority }
     * 
     */
    public Priority createPriority() {
        return new Priority();
    }

    /**
     * Create an instance of {@link DateTimeInterval }
     * 
     */
    public DateTimeInterval createDateTimeInterval() {
        return new DateTimeInterval();
    }

    /**
     * Create an instance of {@link ElectronicAddress }
     * 
     */
    public ElectronicAddress createElectronicAddress() {
        return new ElectronicAddress();
    }

    /**
     * Create an instance of {@link BatchItemInfo }
     * 
     */
    public BatchItemInfo createBatchItemInfo() {
        return new BatchItemInfo();
    }

    /**
     * Create an instance of {@link CustomerAccount }
     * 
     */
    public CustomerAccount createCustomerAccount() {
        return new CustomerAccount();
    }

    /**
     * Create an instance of {@link ProgramDate }
     * 
     */
    public ProgramDate createProgramDate() {
        return new ProgramDate();
    }

    /**
     * Create an instance of {@link DemandResponseProgram }
     * 
     */
    public DemandResponseProgram createDemandResponseProgram() {
        return new DemandResponseProgram();
    }

    /**
     * Create an instance of {@link OrganisationRole }
     * 
     */
    public OrganisationRole createOrganisationRole() {
        return new OrganisationRole();
    }

    /**
     * Create an instance of {@link Agreement }
     * 
     */
    public Agreement createAgreement() {
        return new Agreement();
    }

    /**
     * Create an instance of {@link StreetAddress }
     * 
     */
    public StreetAddress createStreetAddress() {
        return new StreetAddress();
    }

    /**
     * Create an instance of {@link StreetDetail }
     * 
     */
    public StreetDetail createStreetDetail() {
        return new StreetDetail();
    }

    /**
     * Create an instance of {@link Object }
     * 
     */
    public Object createObject() {
        return new Object();
    }

    /**
     * Create an instance of {@link SummaryMeasurement }
     * 
     */
    public SummaryMeasurement createSummaryMeasurement() {
        return new SummaryMeasurement();
    }

    /**
     * Create an instance of {@link IdentifiedObject }
     * 
     */
    public IdentifiedObject createIdentifiedObject() {
        return new IdentifiedObject();
    }

    /**
     * Create an instance of {@link AssetContainer }
     * 
     */
    public AssetContainer createAssetContainer() {
        return new AssetContainer();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Status }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Status")
    public JAXBElement<Status> createStatus(Status value) {
        return new JAXBElement<Status>(_Status_QNAME, Status.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Priority }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Priority")
    public JAXBElement<Priority> createPriority(Priority value) {
        return new JAXBElement<Priority>(_Priority_QNAME, Priority.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Organisation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Organisation")
    public JAXBElement<Organisation> createOrganisation(Organisation value) {
        return new JAXBElement<Organisation>(_Organisation_QNAME, Organisation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BatchItemInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "BatchItemInfo")
    public JAXBElement<BatchItemInfo> createBatchItemInfo(BatchItemInfo value) {
        return new JAXBElement<BatchItemInfo>(_BatchItemInfo_QNAME, BatchItemInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "CustomerAccount")
    public JAXBElement<CustomerAccount> createCustomerAccount(CustomerAccount value) {
        return new JAXBElement<CustomerAccount>(_CustomerAccount_QNAME, CustomerAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "LocalTimeParameters")
    public JAXBElement<TimeConfiguration> createLocalTimeParameters(TimeConfiguration value) {
        return new JAXBElement<TimeConfiguration>(_LocalTimeParameters_QNAME, TimeConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeInterval }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "DateTimeInterval")
    public JAXBElement<DateTimeInterval> createDateTimeInterval(DateTimeInterval value) {
        return new JAXBElement<DateTimeInterval>(_DateTimeInterval_QNAME, DateTimeInterval.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElectronicAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "ElectronicAddress")
    public JAXBElement<ElectronicAddress> createElectronicAddress(ElectronicAddress value) {
        return new JAXBElement<ElectronicAddress>(_ElectronicAddress_QNAME, ElectronicAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DemandResponseProgram }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "DemandResponseProgram")
    public JAXBElement<DemandResponseProgram> createDemandResponseProgram(DemandResponseProgram value) {
        return new JAXBElement<DemandResponseProgram>(_DemandResponseProgram_QNAME, DemandResponseProgram.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrganisationRole }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "OrganisationRole")
    public JAXBElement<OrganisationRole> createOrganisationRole(OrganisationRole value) {
        return new JAXBElement<OrganisationRole>(_OrganisationRole_QNAME, OrganisationRole.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "ProgramDate")
    public JAXBElement<ProgramDate> createProgramDate(ProgramDate value) {
        return new JAXBElement<ProgramDate>(_ProgramDate_QNAME, ProgramDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StreetAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "StreetAddress")
    public JAXBElement<StreetAddress> createStreetAddress(StreetAddress value) {
        return new JAXBElement<StreetAddress>(_StreetAddress_QNAME, StreetAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StreetDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "StreetDetail")
    public JAXBElement<StreetDetail> createStreetDetail(StreetDetail value) {
        return new JAXBElement<StreetDetail>(_StreetDetail_QNAME, StreetDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Agreement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Agreement")
    public JAXBElement<Agreement> createAgreement(Agreement value) {
        return new JAXBElement<Agreement>(_Agreement_QNAME, Agreement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Object")
    public JAXBElement<Object> createObject(Object value) {
        return new JAXBElement<Object>(_Object_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SummaryMeasurement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "SummaryMeasurement")
    public JAXBElement<SummaryMeasurement> createSummaryMeasurement(SummaryMeasurement value) {
        return new JAXBElement<SummaryMeasurement>(_SummaryMeasurement_QNAME, SummaryMeasurement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssetContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "AssetContainer")
    public JAXBElement<AssetContainer> createAssetContainer(AssetContainer value) {
        return new JAXBElement<AssetContainer>(_AssetContainer_QNAME, AssetContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Location }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Location")
    public JAXBElement<Location> createLocation(Location value) {
        return new JAXBElement<Location>(_Location_QNAME, Location.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifiedObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "IdentifiedObject")
    public JAXBElement<IdentifiedObject> createIdentifiedObject(IdentifiedObject value) {
        return new JAXBElement<IdentifiedObject>(_IdentifiedObject_QNAME, IdentifiedObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EndDevice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "EndDevice")
    public JAXBElement<EndDevice> createEndDevice(EndDevice value) {
        return new JAXBElement<EndDevice>(_EndDevice_QNAME, EndDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerAgreement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "CustomerAgreement")
    public JAXBElement<CustomerAgreement> createCustomerAgreement(CustomerAgreement value) {
        return new JAXBElement<CustomerAgreement>(_CustomerAgreement_QNAME, CustomerAgreement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PricingStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "PricingStructure")
    public JAXBElement<PricingStructure> createPricingStructure(PricingStructure value) {
        return new JAXBElement<PricingStructure>(_PricingStructure_QNAME, PricingStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsagePoint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "UsagePoint")
    public JAXBElement<UsagePoint> createUsagePoint(UsagePoint value) {
        return new JAXBElement<UsagePoint>(_UsagePoint_QNAME, UsagePoint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkLocation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "WorkLocation")
    public JAXBElement<WorkLocation> createWorkLocation(WorkLocation value) {
        return new JAXBElement<WorkLocation>(_WorkLocation_QNAME, WorkLocation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Customer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Customer")
    public JAXBElement<Customer> createCustomer(Customer value) {
        return new JAXBElement<Customer>(_Customer_QNAME, Customer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Meter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Meter")
    public JAXBElement<Meter> createMeter(Meter value) {
        return new JAXBElement<Meter>(_Meter_QNAME, Meter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceSupplier }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "ServiceSupplier")
    public JAXBElement<ServiceSupplier> createServiceSupplier(ServiceSupplier value) {
        return new JAXBElement<ServiceSupplier>(_ServiceSupplier_QNAME, ServiceSupplier.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramDateIdMapping }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "ProgramDateIdMapping")
    public JAXBElement<ProgramDateIdMapping> createProgramDateIdMapping(ProgramDateIdMapping value) {
        return new JAXBElement<ProgramDateIdMapping>(_ProgramDateIdMapping_QNAME, ProgramDateIdMapping.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountNotification }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "AccountNotification")
    public JAXBElement<AccountNotification> createAccountNotification(AccountNotification value) {
        return new JAXBElement<AccountNotification>(_AccountNotification_QNAME, AccountNotification.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BatchListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "BatchList")
    public JAXBElement<BatchListType> createBatchList(BatchListType value) {
        return new JAXBElement<BatchListType>(_BatchList_QNAME, BatchListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TownDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "TownDetail")
    public JAXBElement<TownDetail> createTownDetail(TownDetail value) {
        return new JAXBElement<TownDetail>(_TownDetail_QNAME, TownDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeterMultiplier }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "MeterMultiplier")
    public JAXBElement<MeterMultiplier> createMeterMultiplier(MeterMultiplier value) {
        return new JAXBElement<MeterMultiplier>(_MeterMultiplier_QNAME, MeterMultiplier.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Document }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Document")
    public JAXBElement<Document> createDocument(Document value) {
        return new JAXBElement<Document>(_Document_QNAME, Document.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TelephoneNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "TelephoneNumber")
    public JAXBElement<TelephoneNumber> createTelephoneNumber(TelephoneNumber value) {
        return new JAXBElement<TelephoneNumber>(_TelephoneNumber_QNAME, TelephoneNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PositionPoint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "PositionPoint")
    public JAXBElement<PositionPoint> createPositionPoint(PositionPoint value) {
        return new JAXBElement<PositionPoint>(_PositionPoint_QNAME, PositionPoint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceLocation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "ServiceLocation")
    public JAXBElement<ServiceLocation> createServiceLocation(ServiceLocation value) {
        return new JAXBElement<ServiceLocation>(_ServiceLocation_QNAME, ServiceLocation.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AcceptanceTest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "AcceptanceTest")
    public JAXBElement<AcceptanceTest> createAcceptanceTest(AcceptanceTest value) {
        return new JAXBElement<AcceptanceTest>(_AcceptanceTest_QNAME, AcceptanceTest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Asset }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "Asset")
    public JAXBElement<Asset> createAsset(Asset value) {
        return new JAXBElement<Asset>(_Asset_QNAME, Asset.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "TimeConfiguration")
    public JAXBElement<TimeConfiguration> createTimeConfiguration(TimeConfiguration value) {
        return new JAXBElement<TimeConfiguration>(_TimeConfiguration_QNAME, TimeConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsagePoints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "UsagePoints")
    public JAXBElement<UsagePoints> createUsagePoints(UsagePoints value) {
        return new JAXBElement<UsagePoints>(_UsagePoints_QNAME, UsagePoints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramDateIdMappings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "ProgramDateIdMappings")
    public JAXBElement<ProgramDateIdMappings> createProgramDateIdMappings(ProgramDateIdMappings value) {
        return new JAXBElement<ProgramDateIdMappings>(_ProgramDateIdMappings_QNAME, ProgramDateIdMappings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LifecycleDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://naesb.org/espi/customer", name = "LifecycleDate")
    public JAXBElement<LifecycleDate> createLifecycleDate(LifecycleDate value) {
        return new JAXBElement<LifecycleDate>(_LifecycleDate_QNAME, LifecycleDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights", scope = EntryType.class)
    public JAXBElement<TextType> createEntryTypeRights(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeRights_QNAME, TextType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "author", scope = EntryType.class)
    public JAXBElement<PersonType> createEntryTypeAuthor(PersonType value) {
        return new JAXBElement<PersonType>(_EntryTypeAuthor_QNAME, PersonType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "contributor", scope = EntryType.class)
    public JAXBElement<PersonType> createEntryTypeContributor(PersonType value) {
        return new JAXBElement<PersonType>(_EntryTypeContributor_QNAME, PersonType.class, EntryType.class, value);
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
        return new JAXBElement<DateTimeType>(_EntryTypeUpdated_QNAME, DateTimeType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "category", scope = EntryType.class)
    public JAXBElement<CategoryType> createEntryTypeCategory(CategoryType value) {
        return new JAXBElement<CategoryType>(_EntryTypeCategory_QNAME, CategoryType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title", scope = EntryType.class)
    public JAXBElement<TextType> createEntryTypeTitle(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeTitle_QNAME, TextType.class, EntryType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "id", scope = EntryType.class)
    public JAXBElement<IdType> createEntryTypeId(IdType value) {
        return new JAXBElement<IdType>(_EntryTypeId_QNAME, IdType.class, EntryType.class, value);
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
        return new JAXBElement<LinkType>(_EntryTypeLink_QNAME, LinkType.class, EntryType.class, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights", scope = SourceType.class)
    public JAXBElement<TextType> createSourceTypeRights(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeRights_QNAME, TextType.class, SourceType.class, value);
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
        return new JAXBElement<PersonType>(_EntryTypeAuthor_QNAME, PersonType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "contributor", scope = SourceType.class)
    public JAXBElement<PersonType> createSourceTypeContributor(PersonType value) {
        return new JAXBElement<PersonType>(_EntryTypeContributor_QNAME, PersonType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "updated", scope = SourceType.class)
    public JAXBElement<DateTimeType> createSourceTypeUpdated(DateTimeType value) {
        return new JAXBElement<DateTimeType>(_EntryTypeUpdated_QNAME, DateTimeType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "category", scope = SourceType.class)
    public JAXBElement<CategoryType> createSourceTypeCategory(CategoryType value) {
        return new JAXBElement<CategoryType>(_EntryTypeCategory_QNAME, CategoryType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title", scope = SourceType.class)
    public JAXBElement<TextType> createSourceTypeTitle(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeTitle_QNAME, TextType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "id", scope = SourceType.class)
    public JAXBElement<IdType> createSourceTypeId(IdType value) {
        return new JAXBElement<IdType>(_EntryTypeId_QNAME, IdType.class, SourceType.class, value);
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
        return new JAXBElement<LinkType>(_EntryTypeLink_QNAME, LinkType.class, SourceType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "author", scope = FeedType.class)
    public JAXBElement<PersonType> createFeedTypeAuthor(PersonType value) {
        return new JAXBElement<PersonType>(_EntryTypeAuthor_QNAME, PersonType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title", scope = FeedType.class)
    public JAXBElement<TextType> createFeedTypeTitle(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeTitle_QNAME, TextType.class, FeedType.class, value);
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
        return new JAXBElement<LinkType>(_EntryTypeLink_QNAME, LinkType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights", scope = FeedType.class)
    public JAXBElement<TextType> createFeedTypeRights(TextType value) {
        return new JAXBElement<TextType>(_EntryTypeRights_QNAME, TextType.class, FeedType.class, value);
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
        return new JAXBElement<PersonType>(_EntryTypeContributor_QNAME, PersonType.class, FeedType.class, value);
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
        return new JAXBElement<DateTimeType>(_EntryTypeUpdated_QNAME, DateTimeType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "category", scope = FeedType.class)
    public JAXBElement<CategoryType> createFeedTypeCategory(CategoryType value) {
        return new JAXBElement<CategoryType>(_EntryTypeCategory_QNAME, CategoryType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "id", scope = FeedType.class)
    public JAXBElement<IdType> createFeedTypeId(IdType value) {
        return new JAXBElement<IdType>(_EntryTypeId_QNAME, IdType.class, FeedType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "logo", scope = FeedType.class)
    public JAXBElement<LogoType> createFeedTypeLogo(LogoType value) {
        return new JAXBElement<LogoType>(_SourceTypeLogo_QNAME, LogoType.class, FeedType.class, value);
    }

}
