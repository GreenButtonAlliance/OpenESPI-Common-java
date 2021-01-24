
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

package org.naesb.req21.espi.ver3_3.retailcustomer;

import javax.xml.bind.annotation.*;


/**
 * Parent class for different groupings of information collected and managed as a part of a business process. It will frequently contain references to other objects, such as assets, people and power system resources.
 * 
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="authorName" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="createdDateTime" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="lastModifiedDateTime" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="revisionNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="electronicAddress" type="{http://naesb.org/espi/customer}ElectronicAddress" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="title" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="docStatus" type="{http://naesb.org/espi/customer}Status" minOccurs="0"/>
 *         &lt;element name="status" type="{http://naesb.org/espi/customer}Status" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", namespace = "http://naesb.org/espi/customer", propOrder = {
    "type",
    "authorName",
    "createdDateTime",
    "lastModifiedDateTime",
    "revisionNumber",
    "electronicAddress",
    "subject",
    "title",
    "docStatus",
    "status",
    "comment"
})
@XmlSeeAlso({
    CustomerAccount.class,
    Agreement.class
})
public class Document
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String type;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String authorName;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long createdDateTime;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long lastModifiedDateTime;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String revisionNumber;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected ElectronicAddress electronicAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String subject;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String title;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Status docStatus;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Status status;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String comment;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the authorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Sets the value of the authorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorName(String value) {
        this.authorName = value;
    }

    /**
     * Gets the value of the createdDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Sets the value of the createdDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCreatedDateTime(Long value) {
        this.createdDateTime = value;
    }

    /**
     * Gets the value of the lastModifiedDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    /**
     * Sets the value of the lastModifiedDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLastModifiedDateTime(Long value) {
        this.lastModifiedDateTime = value;
    }

    /**
     * Gets the value of the revisionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevisionNumber() {
        return revisionNumber;
    }

    /**
     * Sets the value of the revisionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevisionNumber(String value) {
        this.revisionNumber = value;
    }

    /**
     * Gets the value of the electronicAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ElectronicAddress }
     *     
     */
    public ElectronicAddress getElectronicAddress() {
        return electronicAddress;
    }

    /**
     * Sets the value of the electronicAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElectronicAddress }
     *     
     */
    public void setElectronicAddress(ElectronicAddress value) {
        this.electronicAddress = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the docStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getDocStatus() {
        return docStatus;
    }

    /**
     * Sets the value of the docStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setDocStatus(Status value) {
        this.docStatus = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

}
