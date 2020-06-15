
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set of spatial coordinates that determine a point, defined in the coordinate system specified in 'Location.CoordinateSystem'. Use a single position point instance to desribe a point-oriented location. Use a sequence of position points to describe a line-oriented object (physical location of non-point oriented objects like cables or lines), or area of an object (like a substation or a geographical zone - in this case, have first and last position point with the same values).
 * 
 * <p>Java class for PositionPoint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PositionPoint">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="xPosition" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="yPosition" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="zPosition" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PositionPoint", namespace = "http://naesb.org/espi/customer", propOrder = {
    "xPosition",
    "yPosition",
    "zPosition"
})
public class PositionPoint
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String xPosition;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String yPosition;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String zPosition;

    /**
     * Gets the value of the xPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXPosition() {
        return xPosition;
    }

    /**
     * Sets the value of the xPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXPosition(String value) {
        this.xPosition = value;
    }

    /**
     * Gets the value of the yPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYPosition() {
        return yPosition;
    }

    /**
     * Sets the value of the yPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYPosition(String value) {
        this.yPosition = value;
    }

    /**
     * Gets the value of the zPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZPosition() {
        return zPosition;
    }

    /**
     * Sets the value of the zPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZPosition(String value) {
        this.zPosition = value;
    }

}
