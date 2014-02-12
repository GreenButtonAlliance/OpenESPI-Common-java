package org.energyos.espi.common.utils;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.energyos.espi.common.utils.factories.FeedFactory.newFeed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.FeedType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import com.sun.syndication.io.FeedException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class ATOMMarshallerTests extends XMLTest {

    String FEED_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"GreenButtonDataStyleSheet.xslt\"?>" +
            "<feed xmlns=\"http://www.w3.org/2005/Atom\" " +
            " xsi:schemaLocation=\"http://naesb.org/espi espiDerived.xsd\"" +
            " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
    String FEED_POSTFIX = "</feed>";
    @Autowired
    private ATOMMarshaller marshaller;

    private String buildContentXml(String espiXml) {
        String XML_PREFIX =
                FEED_PREFIX +
                "  <entry>" +
                "    <id>urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0</id>" +
                "    <title/>" +
                "    <content>";

        String XML_POSTFIX =
                "    </content>" +
                "    <updated>2012-10-24T00:00:00Z</updated>" +
                "  </entry>" +
                FEED_POSTFIX;

        return XML_PREFIX + espiXml + XML_POSTFIX;
    }

    @Test
    public void unmarshal_givenMultipleIntervalBlocks_returnsIntervalBlocks() throws JAXBException {
        String espiXml = "<IntervalBlock xmlns=\"http://naesb.org/espi\"/>\n" +
                "<IntervalBlock xmlns=\"http://naesb.org/espi\"/>\n";

        List<IntervalBlock> intervalBlocks = unmarshalToContentType(espiXml).getIntervalBlocks();

        assertEquals(intervalBlocks.size(), 2);
    }

    @Test
    public void unmarshall_UsagePoint() throws JAXBException {
        String espiXml = "<UsagePoint xmlns=\"http://naesb.org/espi\"/>";

        UsagePoint usagePoint = unmarshalToContentType(espiXml).getUsagePoint();

        assertNotNull(usagePoint);
    }

    @Test
    public void unmarshall_MeterReading() throws JAXBException {
        String espiXml = "<MeterReading xmlns=\"http://naesb.org/espi\"/>";

        MeterReading meterReading = unmarshalToContentType(espiXml).getMeterReading();

        assertNotNull(meterReading);
    }

    @Test
    public void unmarshall_ReadingType() throws JAXBException {
        String espiXml = "<ReadingType xmlns=\"http://naesb.org/espi\"/>";

        ReadingType readingType = unmarshalToContentType(espiXml).getReadingType();

        assertNotNull(readingType);
    }

    @Test
    public void unmarshall_ElectricPowerUsageSummary() throws JAXBException {
        String espiXml = "<ElectricPowerUsageSummary xmlns=\"http://naesb.org/espi\"/>";

        ElectricPowerUsageSummary electricPowerUsageSummary = unmarshalToContentType(espiXml).getElectricPowerUsageSummary();

        assertNotNull(electricPowerUsageSummary);
    }

    @Test
    public void unmarshall_ElectricPowerQualitySummary() throws JAXBException {
        String espiXml = "<ElectricPowerQualitySummary xmlns=\"http://naesb.org/espi\"/>";

        ElectricPowerQualitySummary electricPowerQualitySummary = unmarshalToContentType(espiXml).getElectricPowerQualitySummary();

        assertNotNull(electricPowerQualitySummary);
    }

    @Test
    public void unmarshall_TimeConfiguration() throws JAXBException {
        String espiXml = "<LocalTimeParameters xmlns=\"http://naesb.org/espi\"/>";

        TimeConfiguration timeConfiguration = unmarshalToContentType(espiXml).getLocalTimeParameters();

        assertNotNull(timeConfiguration);
    }

    @Test
    public void marshal_usagePointContent() throws FeedException, SAXException, IOException, XpathException {
        assertXpathExists("/:feed/:entry/:content/espi:UsagePoint", xmlResult());
    }

    @Test
    public void marshal_meterReadingContent() throws FeedException, SAXException, IOException, XpathException {
        assertXpathExists("/:feed/:entry/:content/espi:MeterReading", xmlResult());
    }

    @Test
    public void marshal_readingTypeContent() throws FeedException, SAXException, IOException, XpathException {
        assertXpathExists("/:feed/:entry/:content/espi:ReadingType", xmlResult());
    }

    @Test
    public void marshal_electricPowerUsageSummaryContent() throws FeedException, SAXException, IOException, XpathException {
        assertXpathExists("/:feed/:entry/:content/espi:ElectricPowerUsageSummary", xmlResult());
    }

    @Test
    public void marshal_electricPowerQualitySummaryContent() throws FeedException, SAXException, IOException, XpathException {
        assertXpathExists("/:feed/:entry/:content/espi:ElectricPowerQualitySummary", xmlResult());
    }

    @Test
    public void marshal_intervalBlockContent() throws FeedException, SAXException, IOException, XpathException {
        assertXpathExists("/:feed/:entry/:content/espi:IntervalBlock", xmlResult());
    }

    @Test
    public void marshal_localTimeParamatersContent() throws FeedException, SAXException, IOException, XpathException {
        assertXpathExists("/:feed/:entry/:content/espi:LocalTimeParameters", xmlResult());
    }

    private String xmlResult() throws FeedException {
        return marshaller.marshal(newFeed());
    }

    private ContentType unmarshalToContentType(String espiXml) throws JAXBException {
        InputStream xmlStream = new ByteArrayInputStream(buildContentXml(espiXml).getBytes());
        FeedType feed = marshaller.unmarshal(xmlStream);

        return feed.getEntries().get(0).getContent();
    }
}
