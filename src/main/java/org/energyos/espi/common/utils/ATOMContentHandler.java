/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.EntryProcessorService;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.XMLFilterImpl;

public class ATOMContentHandler extends XMLFilterImpl {
	
    private final JAXBContext context;
    private int depth;
    private UnmarshallerHandler unmarshallerHandler;
    private Locator locator;
    private NamespaceSupport namespaces = new NamespaceSupport();
    private EntryProcessorService procssor;
    private List <EntryType> entries = new ArrayList<EntryType>();
    private XMLGregorianCalendar minUpdated = null;
    private XMLGregorianCalendar maxUpdated = null;

    public ATOMContentHandler(JAXBContext context, EntryProcessorService procssor) {
        this.context = context;
        this.procssor = procssor;
    }

    public List<EntryType> getEntries () {
    	return entries;
    }
    
    public XMLGregorianCalendar getMinUpdated() {
    	return minUpdated;
    }
    
    public XMLGregorianCalendar getMaxUpdated() {
    	return maxUpdated;
    }
     
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
            throws SAXException {

        if (depth != 0) {
            depth++;
            super.startElement(namespaceURI, localName, qName, atts);
            return;
        }

        if (localName.equals("entry")) {
            Unmarshaller unmarshaller;
            try {
                unmarshaller = context.createUnmarshaller();
            } catch (JAXBException e) {

                throw new SAXException(e);
            }
            unmarshallerHandler = unmarshaller.getUnmarshallerHandler();

            setContentHandler(unmarshallerHandler);

            unmarshallerHandler.startDocument();
            unmarshallerHandler.setDocumentLocator(locator);
        
            @SuppressWarnings("unchecked")
			Enumeration<String> e = namespaces.getPrefixes();
            while (e.hasMoreElements()) {
                String prefix = e.nextElement();
                String uri = namespaces.getURI(prefix);

                unmarshallerHandler.startPrefixMapping(prefix, uri);
            }
            String defaultURI = namespaces.getURI("");
            if (defaultURI != null)
                unmarshallerHandler.startPrefixMapping("", defaultURI);

            super.startElement(namespaceURI, localName, qName, atts);

            depth = 1;
        }
    }

	@SuppressWarnings("unchecked")
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        super.endElement(namespaceURI, localName, qName);

        if (depth != 0) {
            depth--;
            if (depth == 0) {
                Enumeration<String> e = namespaces.getPrefixes();
                while (e.hasMoreElements()) {
                    String prefix = e.nextElement();
                    unmarshallerHandler.endPrefixMapping(prefix);
                }
                String defaultURI = namespaces.getURI("");
                if (defaultURI != null)
                    unmarshallerHandler.endPrefixMapping("");
                unmarshallerHandler.endDocument();

                setContentHandler(new DefaultHandler());

                if (localName.equals("entry")) {
                    JAXBElement<EntryType> result = null;
                    try {
                        result = (JAXBElement<EntryType>) unmarshallerHandler.getResult();
                    } catch (JAXBException x) {
                        throw new SAXException("Unable to unmarshall <entry>", x);
                    }
                    procssor.process(result.getValue());

                    entries.add(result.getValue());
                    
                    // and update the min/max import range for later subscription publication
                    if ((minUpdated == null) || (result.getValue().getPublished().getValue().compare(minUpdated) == DatatypeConstants.LESSER)) {
                    	minUpdated = result.getValue().getPublished().getValue();
                    }
                    if ((maxUpdated == null) || (result.getValue().getUpdated().getValue().compare(maxUpdated) == DatatypeConstants.GREATER)) {
                    	maxUpdated = result.getValue().getUpdated().getValue();
                    }
                }

                unmarshallerHandler = null;
            }
        }
    }

    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
        this.locator = locator;
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        namespaces.pushContext();
        namespaces.declarePrefix(prefix, uri);

        super.startPrefixMapping(prefix, uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        namespaces.popContext();

        super.endPrefixMapping(prefix);
    }
    
    public void setEntryProcessorService (EntryProcessorService entryProcessorService) {
    	this.procssor = entryProcessorService;
    }
}
