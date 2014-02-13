package org.energyos.espi.common.utils;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathNotExists;
import static org.energyos.espi.common.test.EspiFactory.newElectricPowerQualitySummary;
import static org.energyos.espi.common.test.EspiFactory.newElectricPowerUsageSummary;
import static org.energyos.espi.common.test.EspiFactory.newIntervalBlock;
import static org.energyos.espi.common.test.EspiFactory.newMeterReading;
import static org.energyos.espi.common.test.EspiFactory.newReadingType;
import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;
import com.sun.syndication.io.FeedException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class FragmentMarshallerTests extends XMLTest {

    @Autowired
    private Jaxb2Marshaller fragmentMarshaller;

    @Test
    public void entry() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry", newXML());
    }

    @Test
    public void entry_id() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:id", newXML());
    }

    @Test
    public void entry_link() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:link", newXML());
    }

    @Test
    public void entry_title() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:title", newXML());
    }

    @Test
    public void entry_published() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:published", newXML());
    }

    @Test
    public void entry_updated() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:updated", newXML());
    }

    @Test
    public void content() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content", newXML());
    }

    @Test
    public void content_empty() throws Exception {
        assertXpathNotExists("/:entry/:content/*", newXML(null));
    }

    @Test
    public void content_UsagePoint() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content/espi:UsagePoint", newXML(newUsagePoint()));
    }

    @Test
    public void content_MeterReading() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content/espi:MeterReading", newXML(newMeterReading()));
    }

    @Test
    public void content_ReadingType() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content/espi:ReadingType", newXML(newReadingType()));
    }

    @Test
    public void content_IntervalBlocks() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content/espi:IntervalBlock", newXML(newIntervalBlock()));
    }

    @Test
    public void content_ElectricPowerUsageSummary() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content/espi:ElectricPowerUsageSummary", newXML(newElectricPowerUsageSummary()));
    }

    @Test
    public void content_ElectricPowerQualitySummary() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content/espi:ElectricPowerQualitySummary", newXML(newElectricPowerQualitySummary()));
    }

    @Test
    public void content_LocalTimeParameters() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:entry/:content/espi:LocalTimeParameters", newXML(EspiFactory.newLocalTimeParameters()));
    }

    private String newXML() throws DatatypeConfigurationException, FeedException {
        return newXML(newUsagePoint());
    }

    private String newXML(IdentifiedObject resource) throws DatatypeConfigurationException, FeedException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        EntryType entry = new EntryType();
        entry.getLinks().add(new LinkType(LinkType.SELF, "self"));
        entry.setTitle("entry");
        entry.setId("id");
        entry.setPublished(new DateTimeType());
        entry.setUpdated(new DateTimeType());
        ContentType content = new ContentType();
        content.setResources(Lists.<IdentifiedObject>newArrayList(resource));
        entry.setContent(content);
        
        fragmentMarshaller.marshal(entry, new StreamResult(os)); 
        
        return os.toString();
    }
}
