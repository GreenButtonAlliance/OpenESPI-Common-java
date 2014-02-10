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

package org.energyos.espi.common.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.ATOMContentHandler;
import org.energyos.espi.common.utils.EntryProcessor;
import org.energyos.espi.common.utils.ResourceConverter;
import org.energyos.espi.common.utils.ResourceLinker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


@Service
@Transactional
public class ImportServiceImpl implements ImportService {
    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;
    
    @Autowired
    private ResourceLinker resourceLinker;
    
    @Autowired
    private ResourceService resourceService;
    
    // this is a list of the UsagePointIds referenced during
    // this import
    private List<EntryType> entries;
    
    // Min Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar minUpdated = null;
    
    // Max Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar maxUpdated = null;
    
    @Override
    public List <EntryType> getEntries() {
    	List<EntryType> result = entries;
    	entries = null;
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
    
    @Transactional
    @Override
    public void importData(InputStream stream) throws IOException, SAXException, ParserConfigurationException {
        
    	JAXBContext context = marshaller.getJaxbContext();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XMLReader reader = factory.newSAXParser().getXMLReader();

        EntryProcessor processor = new EntryProcessor(resourceLinker, new ResourceConverter(), resourceService);
        ATOMContentHandler atomContentHandler = new ATOMContentHandler(context, processor);
        reader.setContentHandler(atomContentHandler);

        reader.parse(new InputSource(stream));
        
        entries = atomContentHandler.getEntries();
        
        minUpdated = atomContentHandler.getMinUpdated();
        
        maxUpdated = atomContentHandler.getMaxUpdated();
       

    }
}
