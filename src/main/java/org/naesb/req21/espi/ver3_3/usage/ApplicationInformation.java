
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

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains information about a Third Party Application requesting access to the DataCustodian services. Information requested may include items such as Organization Name, Website, Contact Info, Application Name, Description, Icon, Type, default Notification and Callback endpoints, and may also include agreement with terms of service.
 * 
 * Atom Links:
 * self link to this resource
 * 
 * <p>Java class for ApplicationInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationInformation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="dataCustodianId" type="{http://naesb.org/espi}String64"/>
 *         &lt;element name="dataCustodianApplicationStatus" type="{http://naesb.org/espi}DataCustodianApplicationStatus"/>
 *         &lt;element name="thirdPartyApplicationDescription" type="{http://naesb.org/espi}String256" minOccurs="0"/>
 *         &lt;element name="thirdPartyApplicationStatus" type="{http://naesb.org/espi}ThirdPartyApplicatonStatus" minOccurs="0"/>
 *         &lt;element name="thirdPartyApplicationType" type="{http://naesb.org/espi}ThirdPartyApplicationType" minOccurs="0"/>
 *         &lt;element name="thirdPartyApplicationUse" type="{http://naesb.org/espi}ThirdPartyApplicationUse" minOccurs="0"/>
 *         &lt;element name="thirdPartyPhone" type="{http://naesb.org/espi}String32" minOccurs="0"/>
 *         &lt;element name="authorizationServerUri" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="thirdPartyNotifyUri" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="authorizationServerAuthorizationEndpoint" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="authorizationServerRegistrationEndpoint" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="authorizationServerTokenEndpoint" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="dataCustodianBulkRequestURI" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="dataCustodianResourceEndpoint" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="thirdPartyScopeSelectionScreenURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="thirdPartyUserPortalScreenURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="client_secret" type="{http://naesb.org/espi}String512"/>
 *         &lt;element name="logo_uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="client_name" type="{http://naesb.org/espi}String256"/>
 *         &lt;element name="client_uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="redirect_uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded"/>
 *         &lt;element name="client_id" type="{http://naesb.org/espi}String64"/>
 *         &lt;element name="tos_uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="policy_uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="software_id" type="{http://naesb.org/espi}String256"/>
 *         &lt;element name="software_version" type="{http://naesb.org/espi}String32"/>
 *         &lt;element name="client_id_issued_at" type="{http://naesb.org/espi}TimeType"/>
 *         &lt;element name="client_secret_expires_at" type="{http://naesb.org/espi}TimeType"/>
 *         &lt;element name="contacts" type="{http://naesb.org/espi}String256" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="token_endpoint_auth_method" type="{http://naesb.org/espi}TokenEndPointMethod"/>
 *         &lt;element name="scope" type="{http://naesb.org/espi}String256" maxOccurs="unbounded"/>
 *         &lt;element name="grant_types" type="{http://naesb.org/espi}GrantType" maxOccurs="unbounded" minOccurs="2"/>
 *         &lt;element name="response_types" type="{http://naesb.org/espi}ResponseType"/>
 *         &lt;element name="registration_client_uri" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="registration_access_token" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="dataCustodianScopeSelectionScreenURI" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationInformation", namespace = "http://naesb.org/espi", propOrder = {
    "dataCustodianId",
    "dataCustodianApplicationStatus",
    "thirdPartyApplicationDescription",
    "thirdPartyApplicationStatus",
    "thirdPartyApplicationType",
    "thirdPartyApplicationUse",
    "thirdPartyPhone",
    "authorizationServerUri",
    "thirdPartyNotifyUri",
    "authorizationServerAuthorizationEndpoint",
    "authorizationServerRegistrationEndpoint",
    "authorizationServerTokenEndpoint",
    "dataCustodianBulkRequestURI",
    "dataCustodianResourceEndpoint",
    "thirdPartyScopeSelectionScreenURI",
    "thirdPartyUserPortalScreenURI",
    "clientSecret",
    "logoUri",
    "clientName",
    "clientUri",
    "redirectUri",
    "clientId",
    "tosUri",
    "policyUri",
    "softwareId",
    "softwareVersion",
    "clientIdIssuedAt",
    "clientSecretExpiresAt",
    "contacts",
    "tokenEndpointAuthMethod",
    "scope",
    "grantTypes",
    "responseTypes",
    "registrationClientUri",
    "registrationAccessToken",
    "dataCustodianScopeSelectionScreenURI"
})
public class ApplicationInformation
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected String dataCustodianId;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected String dataCustodianApplicationStatus;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String thirdPartyApplicationDescription;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String thirdPartyApplicationStatus;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String thirdPartyApplicationType;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String thirdPartyApplicationUse;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String thirdPartyPhone;
    @XmlElement(namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String authorizationServerUri;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String thirdPartyNotifyUri;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String authorizationServerAuthorizationEndpoint;
    @XmlElement(namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String authorizationServerRegistrationEndpoint;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String authorizationServerTokenEndpoint;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String dataCustodianBulkRequestURI;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String dataCustodianResourceEndpoint;
    @XmlElement(namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String thirdPartyScopeSelectionScreenURI;
    @XmlElement(namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String thirdPartyUserPortalScreenURI;
    @XmlElement(name = "client_secret", namespace = "http://naesb.org/espi", required = true)
    protected String clientSecret;
    @XmlElement(name = "logo_uri", namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String logoUri;
    @XmlElement(name = "client_name", namespace = "http://naesb.org/espi", required = true)
    protected String clientName;
    @XmlElement(name = "client_uri", namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String clientUri;
    @XmlElement(name = "redirect_uri", namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "anyURI")
    protected List<String> redirectUri;
    @XmlElement(name = "client_id", namespace = "http://naesb.org/espi", required = true)
    protected String clientId;
    @XmlElement(name = "tos_uri", namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String tosUri;
    @XmlElement(name = "policy_uri", namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "anyURI")
    protected String policyUri;
    @XmlElement(name = "software_id", namespace = "http://naesb.org/espi", required = true)
    protected String softwareId;
    @XmlElement(name = "software_version", namespace = "http://naesb.org/espi", required = true)
    protected String softwareVersion;
    @XmlElement(name = "client_id_issued_at", namespace = "http://naesb.org/espi")
    protected long clientIdIssuedAt;
    @XmlElement(name = "client_secret_expires_at", namespace = "http://naesb.org/espi")
    protected long clientSecretExpiresAt;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected List<String> contacts;
    @XmlElement(name = "token_endpoint_auth_method", namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "string")
    protected TokenEndPointMethod tokenEndpointAuthMethod;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected List<String> scope;
    @XmlElement(name = "grant_types", namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "string")
    protected List<GrantType> grantTypes;
    @XmlElement(name = "response_types", namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "string")
    protected ResponseType responseTypes;
    @XmlElement(name = "registration_client_uri", namespace = "http://naesb.org/espi", required = true)
    protected java.lang.Object registrationClientUri;
    @XmlElement(name = "registration_access_token", namespace = "http://naesb.org/espi", required = true)
    protected java.lang.Object registrationAccessToken;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected java.lang.Object dataCustodianScopeSelectionScreenURI;

    /**
     * Gets the value of the dataCustodianId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCustodianId() {
        return dataCustodianId;
    }

    /**
     * Sets the value of the dataCustodianId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCustodianId(String value) {
        this.dataCustodianId = value;
    }

    /**
     * Gets the value of the dataCustodianApplicationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCustodianApplicationStatus() {
        return dataCustodianApplicationStatus;
    }

    /**
     * Sets the value of the dataCustodianApplicationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCustodianApplicationStatus(String value) {
        this.dataCustodianApplicationStatus = value;
    }

    /**
     * Gets the value of the thirdPartyApplicationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyApplicationDescription() {
        return thirdPartyApplicationDescription;
    }

    /**
     * Sets the value of the thirdPartyApplicationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyApplicationDescription(String value) {
        this.thirdPartyApplicationDescription = value;
    }

    /**
     * Gets the value of the thirdPartyApplicationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyApplicationStatus() {
        return thirdPartyApplicationStatus;
    }

    /**
     * Sets the value of the thirdPartyApplicationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyApplicationStatus(String value) {
        this.thirdPartyApplicationStatus = value;
    }

    /**
     * Gets the value of the thirdPartyApplicationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyApplicationType() {
        return thirdPartyApplicationType;
    }

    /**
     * Sets the value of the thirdPartyApplicationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyApplicationType(String value) {
        this.thirdPartyApplicationType = value;
    }

    /**
     * Gets the value of the thirdPartyApplicationUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyApplicationUse() {
        return thirdPartyApplicationUse;
    }

    /**
     * Sets the value of the thirdPartyApplicationUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyApplicationUse(String value) {
        this.thirdPartyApplicationUse = value;
    }

    /**
     * Gets the value of the thirdPartyPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyPhone() {
        return thirdPartyPhone;
    }

    /**
     * Sets the value of the thirdPartyPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyPhone(String value) {
        this.thirdPartyPhone = value;
    }

    /**
     * Gets the value of the authorizationServerUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationServerUri() {
        return authorizationServerUri;
    }

    /**
     * Sets the value of the authorizationServerUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationServerUri(String value) {
        this.authorizationServerUri = value;
    }

    /**
     * Gets the value of the thirdPartyNotifyUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyNotifyUri() {
        return thirdPartyNotifyUri;
    }

    /**
     * Sets the value of the thirdPartyNotifyUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyNotifyUri(String value) {
        this.thirdPartyNotifyUri = value;
    }

    /**
     * Gets the value of the authorizationServerAuthorizationEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationServerAuthorizationEndpoint() {
        return authorizationServerAuthorizationEndpoint;
    }

    /**
     * Sets the value of the authorizationServerAuthorizationEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationServerAuthorizationEndpoint(String value) {
        this.authorizationServerAuthorizationEndpoint = value;
    }

    /**
     * Gets the value of the authorizationServerRegistrationEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationServerRegistrationEndpoint() {
        return authorizationServerRegistrationEndpoint;
    }

    /**
     * Sets the value of the authorizationServerRegistrationEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationServerRegistrationEndpoint(String value) {
        this.authorizationServerRegistrationEndpoint = value;
    }

    /**
     * Gets the value of the authorizationServerTokenEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationServerTokenEndpoint() {
        return authorizationServerTokenEndpoint;
    }

    /**
     * Sets the value of the authorizationServerTokenEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationServerTokenEndpoint(String value) {
        this.authorizationServerTokenEndpoint = value;
    }

    /**
     * Gets the value of the dataCustodianBulkRequestURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCustodianBulkRequestURI() {
        return dataCustodianBulkRequestURI;
    }

    /**
     * Sets the value of the dataCustodianBulkRequestURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCustodianBulkRequestURI(String value) {
        this.dataCustodianBulkRequestURI = value;
    }

    /**
     * Gets the value of the dataCustodianResourceEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCustodianResourceEndpoint() {
        return dataCustodianResourceEndpoint;
    }

    /**
     * Sets the value of the dataCustodianResourceEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCustodianResourceEndpoint(String value) {
        this.dataCustodianResourceEndpoint = value;
    }

    /**
     * Gets the value of the thirdPartyScopeSelectionScreenURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyScopeSelectionScreenURI() {
        return thirdPartyScopeSelectionScreenURI;
    }

    /**
     * Sets the value of the thirdPartyScopeSelectionScreenURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyScopeSelectionScreenURI(String value) {
        this.thirdPartyScopeSelectionScreenURI = value;
    }

    /**
     * Gets the value of the thirdPartyUserPortalScreenURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyUserPortalScreenURI() {
        return thirdPartyUserPortalScreenURI;
    }

    /**
     * Sets the value of the thirdPartyUserPortalScreenURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyUserPortalScreenURI(String value) {
        this.thirdPartyUserPortalScreenURI = value;
    }

    /**
     * Gets the value of the clientSecret property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Sets the value of the clientSecret property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSecret(String value) {
        this.clientSecret = value;
    }

    /**
     * Gets the value of the logoUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoUri() {
        return logoUri;
    }

    /**
     * Sets the value of the logoUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoUri(String value) {
        this.logoUri = value;
    }

    /**
     * Gets the value of the clientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the value of the clientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientName(String value) {
        this.clientName = value;
    }

    /**
     * Gets the value of the clientUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientUri() {
        return clientUri;
    }

    /**
     * Sets the value of the clientUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientUri(String value) {
        this.clientUri = value;
    }

    /**
     * Gets the value of the redirectUri property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the redirectUri property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRedirectUri().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRedirectUri() {
        if (redirectUri == null) {
            redirectUri = new ArrayList<String>();
        }
        return this.redirectUri;
    }

    /**
     * Gets the value of the clientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientId(String value) {
        this.clientId = value;
    }

    /**
     * Gets the value of the tosUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTosUri() {
        return tosUri;
    }

    /**
     * Sets the value of the tosUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTosUri(String value) {
        this.tosUri = value;
    }

    /**
     * Gets the value of the policyUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyUri() {
        return policyUri;
    }

    /**
     * Sets the value of the policyUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyUri(String value) {
        this.policyUri = value;
    }

    /**
     * Gets the value of the softwareId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareId() {
        return softwareId;
    }

    /**
     * Sets the value of the softwareId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareId(String value) {
        this.softwareId = value;
    }

    /**
     * Gets the value of the softwareVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * Sets the value of the softwareVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareVersion(String value) {
        this.softwareVersion = value;
    }

    /**
     * Gets the value of the clientIdIssuedAt property.
     * 
     */
    public long getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    /**
     * Sets the value of the clientIdIssuedAt property.
     * 
     */
    public void setClientIdIssuedAt(long value) {
        this.clientIdIssuedAt = value;
    }

    /**
     * Gets the value of the clientSecretExpiresAt property.
     * 
     */
    public long getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    /**
     * Sets the value of the clientSecretExpiresAt property.
     * 
     */
    public void setClientSecretExpiresAt(long value) {
        this.clientSecretExpiresAt = value;
    }

    /**
     * Gets the value of the contacts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contacts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContacts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getContacts() {
        if (contacts == null) {
            contacts = new ArrayList<String>();
        }
        return this.contacts;
    }

    /**
     * Gets the value of the tokenEndpointAuthMethod property.
     * 
     * @return
     *     possible object is
     *     {@link TokenEndPointMethod }
     *     
     */
    public TokenEndPointMethod getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    /**
     * Sets the value of the tokenEndpointAuthMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TokenEndPointMethod }
     *     
     */
    public void setTokenEndpointAuthMethod(TokenEndPointMethod value) {
        this.tokenEndpointAuthMethod = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scope property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScope().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getScope() {
        if (scope == null) {
            scope = new ArrayList<String>();
        }
        return this.scope;
    }

    /**
     * Gets the value of the grantTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grantTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrantTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GrantType }
     * 
     * 
     */
    public List<GrantType> getGrantTypes() {
        if (grantTypes == null) {
            grantTypes = new ArrayList<GrantType>();
        }
        return this.grantTypes;
    }

    /**
     * Gets the value of the responseTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseType }
     *     
     */
    public ResponseType getResponseTypes() {
        return responseTypes;
    }

    /**
     * Sets the value of the responseTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseType }
     *     
     */
    public void setResponseTypes(ResponseType value) {
        this.responseTypes = value;
    }

    /**
     * Gets the value of the registrationClientUri property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Object }
     *     
     */
    public java.lang.Object getRegistrationClientUri() {
        return registrationClientUri;
    }

    /**
     * Sets the value of the registrationClientUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Object }
     *     
     */
    public void setRegistrationClientUri(java.lang.Object value) {
        this.registrationClientUri = value;
    }

    /**
     * Gets the value of the registrationAccessToken property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Object }
     *     
     */
    public java.lang.Object getRegistrationAccessToken() {
        return registrationAccessToken;
    }

    /**
     * Sets the value of the registrationAccessToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Object }
     *     
     */
    public void setRegistrationAccessToken(java.lang.Object value) {
        this.registrationAccessToken = value;
    }

    /**
     * Gets the value of the dataCustodianScopeSelectionScreenURI property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Object }
     *     
     */
    public java.lang.Object getDataCustodianScopeSelectionScreenURI() {
        return dataCustodianScopeSelectionScreenURI;
    }

    /**
     * Sets the value of the dataCustodianScopeSelectionScreenURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Object }
     *     
     */
    public void setDataCustodianScopeSelectionScreenURI(java.lang.Object value) {
        this.dataCustodianScopeSelectionScreenURI = value;
    }

}
