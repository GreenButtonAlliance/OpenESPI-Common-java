
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
 * [extension] List of programIDmappings
 * 
 * <p>Java class for ProgramIdMappings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProgramIdMappings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="programIdMapping">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tOUorCPPorConsumptionTier" type="{http://naesb.org/espi}tOUorCPPorConsumptionTier"/>
 *                   &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramIdMappings", namespace = "http://naesb.org/espi", propOrder = {
    "programIdMapping"
})
public class ProgramIdMappings
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected List<ProgramIdMappings.ProgramIdMapping> programIdMapping;

    /**
     * Gets the value of the programIdMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the programIdMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProgramIdMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProgramIdMappings.ProgramIdMapping }
     * 
     * 
     */
    public List<ProgramIdMappings.ProgramIdMapping> getProgramIdMapping() {
        if (programIdMapping == null) {
            programIdMapping = new ArrayList<ProgramIdMappings.ProgramIdMapping>();
        }
        return this.programIdMapping;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="tOUorCPPorConsumptionTier" type="{http://naesb.org/espi}tOUorCPPorConsumptionTier"/>
     *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "toUorCPPorConsumptionTier",
        "code",
        "name",
        "note"
    })
    public static class ProgramIdMapping {

        @XmlElement(name = "tOUorCPPorConsumptionTier", namespace = "http://naesb.org/espi", required = true)
        @XmlSchemaType(name = "string")
        protected TOUorCPPorConsumptionTier toUorCPPorConsumptionTier;
        @XmlElement(namespace = "http://naesb.org/espi", required = true)
        protected java.lang.Object code;
        @XmlElement(namespace = "http://naesb.org/espi", required = true)
        protected java.lang.Object name;
        @XmlElement(namespace = "http://naesb.org/espi")
        protected java.lang.Object note;

        /**
         * Gets the value of the toUorCPPorConsumptionTier property.
         * 
         * @return
         *     possible object is
         *     {@link TOUorCPPorConsumptionTier }
         *     
         */
        public TOUorCPPorConsumptionTier getTOUorCPPorConsumptionTier() {
            return toUorCPPorConsumptionTier;
        }

        /**
         * Sets the value of the toUorCPPorConsumptionTier property.
         * 
         * @param value
         *     allowed object is
         *     {@link TOUorCPPorConsumptionTier }
         *     
         */
        public void setTOUorCPPorConsumptionTier(TOUorCPPorConsumptionTier value) {
            this.toUorCPPorConsumptionTier = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Object }
         *     
         */
        public java.lang.Object getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Object }
         *     
         */
        public void setCode(java.lang.Object value) {
            this.code = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Object }
         *     
         */
        public java.lang.Object getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Object }
         *     
         */
        public void setName(java.lang.Object value) {
            this.name = value;
        }

        /**
         * Gets the value of the note property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Object }
         *     
         */
        public java.lang.Object getNote() {
            return note;
        }

        /**
         * Sets the value of the note property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Object }
         *     
         */
        public void setNote(java.lang.Object value) {
            this.note = value;
        }

    }

}
