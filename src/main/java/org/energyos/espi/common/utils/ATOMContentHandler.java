package org.energyos.espi.common.utils;

import org.energyos.espi.common.models.atom.EntryType;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.XMLFilterImpl;

import javax.xml.bind.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ATOMContentHandler extends XMLFilterImpl {
    private final JAXBContext context;
    private int depth;
    private UnmarshallerHandler unmarshallerHandler;
    private Locator locator;
    private NamespaceSupport namespaces = new NamespaceSupport();
    private EntryProcessor procssor;
    private List <EntryType> entries = new ArrayList<EntryType>();

    public ATOMContentHandler(JAXBContext context, EntryProcessor procssor) {
        this.context = context;
        this.procssor = procssor;
    }

    public List<EntryType> getEntries () {
    	return entries;
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

            Enumeration e = namespaces.getPrefixes();
            while (e.hasMoreElements()) {
                String prefix = (String) e.nextElement();
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

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        super.endElement(namespaceURI, localName, qName);

        if (depth != 0) {
            depth--;
            if (depth == 0) {
                Enumeration e = namespaces.getPrefixes();
                while (e.hasMoreElements()) {
                    String prefix = (String) e.nextElement();
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
                        throw new SAXException("Unbale to unmarshall <entry>", x);
                    }
                    procssor.process(result.getValue());
                    // stash the UsagePoints for later processing of retailCustomer Binding
                    // and subscription notification
                    //
                    if (result.getValue().getContent().getUsagePoint() != null) {
                         entries.add(result.getValue());
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
}
