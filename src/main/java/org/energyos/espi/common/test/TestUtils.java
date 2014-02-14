package org.energyos.espi.common.test;

import static org.energyos.espi.common.domain.Configuration.ATOM_PREFIX;
import static org.energyos.espi.common.domain.Configuration.ESPI_PREFIX;
import static org.energyos.espi.common.domain.Configuration.HTTP_NAESB_ORG_ESPI;
import static org.energyos.espi.common.domain.Configuration.HTTP_WWW_W3_ORG_2005_ATOM;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.InputSource;

public class TestUtils {
    public static void setupXMLUnit() {

        XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(namespaces()));
    }

    public static Map<String, String> namespaces() {
        Map<String, String> m = new HashMap<>();
        m.put(ATOM_PREFIX, HTTP_WWW_W3_ORG_2005_ATOM);
        m.put(ESPI_PREFIX, HTTP_NAESB_ORG_ESPI);
        return m;
    }

    public static NamespaceContext getNamespaceContext() {
        return new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                return namespaces().get(prefix);
            }

            @Override
            public String getPrefix(String namespaceURI) {
                return null;
            }

            @SuppressWarnings("rawtypes")
            // TODO: Type this class strongly and hook back into the xml name world
			@Override
            public Iterator getPrefixes(String namespaceURI) {
                return null;
            }
        };
    }

    public static String getXPathValue(String expression, String xml) throws XPathExpressionException {
        String value;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(getNamespaceContext());

        value = xpath.evaluate(expression, new InputSource(new StringReader(xml))).trim();
        return value;
    }
}
