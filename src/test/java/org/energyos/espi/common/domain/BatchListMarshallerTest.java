package org.energyos.espi.common.domain;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class BatchListMarshallerTest extends XMLTest {
    @Autowired
    @Qualifier(value = "domainMarshaller")
    Jaxb2Marshaller jaxb2Marshaller;

    public String newXML() {
        BatchList batchList = new BatchList();
        batchList.getResources().add("foo");
        batchList.getResources().add("bar");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        jaxb2Marshaller.marshal(batchList, new StreamResult(os));
        return os.toString();
    }

    @Test
    public void batchList() throws SAXException, IOException, XpathException {
        assertXpathExists("/espi:BatchList", newXML());
    }
    @Test
    public void resources() throws SAXException, IOException, XpathException {
        assertXpathEvaluatesTo("foo", "/espi:BatchList/espi:resource[1]", newXML());
        assertXpathEvaluatesTo("bar", "/espi:BatchList/espi:resource[2]", newXML());
    }
}
