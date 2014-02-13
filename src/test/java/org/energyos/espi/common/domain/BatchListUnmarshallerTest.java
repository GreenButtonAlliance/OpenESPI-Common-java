package org.energyos.espi.common.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.InputStreamReader;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.syndication.io.FeedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class BatchListUnmarshallerTest {

    static final String XML_INPUT = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<espi:BatchList xmlns:espi=\"http://naesb.org/espi\">" +
            "   <espi:resource>RetailCustomer/1/UsagePoint/6</espi:resource>" +
            "</espi:BatchList>";

    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;

    private BatchList batchList;

    @Before
    public void before() throws JAXBException, FeedException {
        batchList = (BatchList) marshaller.unmarshal(new StreamSource(new InputStreamReader(IOUtils.toInputStream(XML_INPUT))));
    }

    @Test
    public void BatchList() {
        assertEquals(BatchList.class, batchList.getClass());
        assertEquals(1, batchList.getResources().size());
        assertThat(batchList.getResources().get(0), is("RetailCustomer/1/UsagePoint/6"));
    }

}
