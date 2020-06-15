
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

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The place, scene, or point of something where someone or something has been, is, and/or will be at a given moment in time. It can be defined with one or more postition points (coordinates) in a given coordinate system.
 * 
 * <p>Java class for Location complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Location">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="mainAddress" type="{http://naesb.org/espi/customer}StreetAddress" minOccurs="0"/>
 *         &lt;element name="secondaryAddress" type="{http://naesb.org/espi/customer}StreetAddress" minOccurs="0"/>
 *         &lt;element name="phone1" type="{http://naesb.org/espi/customer}TelephoneNumber" minOccurs="0"/>
 *         &lt;element name="phone2" type="{http://naesb.org/espi/customer}TelephoneNumber" minOccurs="0"/>
 *         &lt;element name="electronicAddress" type="{http://naesb.org/espi/customer}ElectronicAddress" minOccurs="0"/>
 *         &lt;element name="geoInfoReference" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="direction" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="status" type="{http://naesb.org/espi/customer}Status" minOccurs="0"/>
 *         &lt;element name="positionPoints" type="{http://naesb.org/espi/customer}PositionPoint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Location", namespace = "http://naesb.org/espi/customer", propOrder = {
    "type",
    "mainAddress",
    "secondaryAddress",
    "phone1",
    "phone2",
    "electronicAddress",
    "geoInfoReference",
    "direction",
    "status",
    "positionPoints"
})
@XmlSeeAlso({
    WorkLocation.class
})
public class Location
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String type;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected StreetAddress mainAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected StreetAddress secondaryAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected TelephoneNumber phone1;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected TelephoneNumber phone2;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected ElectronicAddress electronicAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String geoInfoReference;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String direction;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Status status;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected List<PositionPoint> positionPoints;

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
     * Gets the value of the mainAddress property.
     * 
     * @return
     *     possible object is
     *     {@link StreetAddress }
     *     
     */
    public StreetAddress getMainAddress() {
        return mainAddress;
    }

    /**
     * Sets the value of the mainAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link StreetAddress }
     *     
     */
    public void setMainAddress(StreetAddress value) {
        this.mainAddress = value;
    }

    /**
     * Gets the value of the secondaryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link StreetAddress }
     *     
     */
    public StreetAddress getSecondaryAddress() {
        return secondaryAddress;
    }

    /**
     * Sets the value of the secondaryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link StreetAddress }
     *     
     */
    public void setSecondaryAddress(StreetAddress value) {
        this.secondaryAddress = value;
    }

    /**
     * Gets the value of the phone1 property.
     * 
     * @return
     *     possible object is
     *     {@link TelephoneNumber }
     *     
     */
    public TelephoneNumber getPhone1() {
        return phone1;
    }

    /**
     * Sets the value of the phone1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelephoneNumber }
     *     
     */
    public void setPhone1(TelephoneNumber value) {
        this.phone1 = value;
    }

    /**
     * Gets the value of the phone2 property.
     * 
     * @return
     *     possible object is
     *     {@link TelephoneNumber }
     *     
     */
    public TelephoneNumber getPhone2() {
        return phone2;
    }

    /**
     * Sets the value of the phone2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelephoneNumber }
     *     
     */
    public void setPhone2(TelephoneNumber value) {
        this.phone2 = value;
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
     * Gets the value of the geoInfoReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeoInfoReference() {
        return geoInfoReference;
    }

    /**
     * Sets the value of the geoInfoReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeoInfoReference(String value) {
        this.geoInfoReference = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
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
     * Gets the value of the positionPoints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the positionPoints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPositionPoints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PositionPoint }
     * 
     * 
     */
    public List<PositionPoint> getPositionPoints() {
        if (positionPoints == null) {
            positionPoints = new ArrayList<PositionPoint>();
        }
        return this.positionPoints;
    }

}
