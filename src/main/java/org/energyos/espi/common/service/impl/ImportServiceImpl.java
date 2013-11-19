package org.energyos.espi.common.service.impl;

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

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

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
    }
}
