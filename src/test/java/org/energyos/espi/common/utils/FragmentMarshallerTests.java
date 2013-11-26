package org.energyos.espi.common.utils;

import com.google.common.collect.Lists;
import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.common.utils.factories.ATOMFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathNotExists;
import static org.energyos.espi.common.test.Asserts.assertXpathValue;
import static org.energyos.espi.common.test.EspiFactory.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class FragmentMarshallerTests extends XMLTest {

    @Autowired
    private Jaxb2Marshaller fragmentMarshaller;

    @Test
    public void feed() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed", newXML());
    }

    @Test
    public void id() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("urn:uuid:0071C5A7-91CF-434E-8BCE-C38AC8AF215D", "/:feed/:id", newXML());
    }

    @Test
    public void title() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("ThirdPartyX Batch Feed", "/:feed/:title", newXML());
    }

    @Test
    public void updated() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("2012-09-14T00:00:00Z", "/:feed/:updated", newXML());
    }

    @Test
    public void entry() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry", newXML());
    }

    @Test
    public void entry_id() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry", newXML());
    }

    @Test
    public void entry_link() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:link", newXML());
    }

    @Test
    public void entry_title() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:title", newXML());
    }

    @Test
    public void entry_published() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("2012-11-15T00:00:00Z", "/:feed/:entry/:published", newXML());
    }

    @Test
    public void entry_updated() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("2012-10-24T00:00:00Z", "/:feed/:entry/:updated", newXML());
    }

    @Test
    public void content() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:content", newXML());
    }

    @Test
    public void content_empty() throws Exception {
        assertXpathNotExists("/:content/*", newXML(null));
    }

    @Test
    public void content_UsagePoint() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:content/espi:UsagePoint", newXML());
    }

    @Test
    public void content_MeterReading() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:content/espi:MeterReading", newXML(newMeterReading()));
    }

    @Test
    public void content_ReadingType() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:content/espi:ReadingType", newXML(newReadingType()));
    }

    @Test
    public void content_IntervalBlocks() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:content/espi:IntervalBlock", newXML(newIntervalBlock()));
    }

    @Test
    public void content_ElectricPowerUsageSummary() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:content/espi:ElectricPowerUsageSummary", newXML(newElectricPowerUsageSummary()));
    }

    @Test
    public void content_ElectricPowerQualitySummary() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:content/espi:ElectricPowerQualitySummary", newXML(newElectricPowerQualitySummary()));
    }

    @Test
    public void content_LocalTimeParameters() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:content/espi:LocalTimeParameters", newXML(EspiFactory.newLocalTimeParameters()));
    }

    private String newXML() throws DatatypeConfigurationException, FeedException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        fragmentMarshaller.marshal(ATOMFactory.newFeedType(), new StreamResult(os));

        return os.toString();
    }

    private String newXML(IdentifiedObject resource) throws DatatypeConfigurationException, FeedException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ContentType content = new ContentType();
        content.setResources(Lists.<IdentifiedObject>newArrayList(resource));
        fragmentMarshaller.marshal(content, new StreamResult(os));

        return os.toString();
    }
}
