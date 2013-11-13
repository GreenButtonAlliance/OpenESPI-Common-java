package org.energyos.espi.common.utils;

import org.energyos.espi.common.BaseTest;
import org.energyos.espi.common.models.atom.EntryType;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class XMLMarshallerTest extends BaseTest {
    @Mock
    Jaxb2Marshaller marshaller;

    @Test
    public void unmarshalStream_returnsEntryType() {
        InputStream inputStream = mock(InputStream.class);

        XMLMarshaller xmlMarshaller = new XMLMarshaller();
        xmlMarshaller.setMarshaller(marshaller);

        JAXBElement jaxbElement = mock(JAXBElement.class);
        EntryType entryType = new EntryType();
        when(jaxbElement.getValue()).thenReturn(entryType);
        when(marshaller.unmarshal(any(StreamSource.class))).thenReturn(jaxbElement);

        assertThat(xmlMarshaller.unmarshal(inputStream, EntryType.class), is(entryType));
    }

    @Test
    public void unmarshalString_returnsEntryType() {
        XMLMarshaller xmlMarshaller = new XMLMarshaller();
        xmlMarshaller.setMarshaller(marshaller);

        JAXBElement jaxbElement = mock(JAXBElement.class);
        EntryType entryType = new EntryType();
        when(jaxbElement.getValue()).thenReturn(entryType);
        when(marshaller.unmarshal(any(StreamSource.class))).thenReturn(jaxbElement);

        assertThat(xmlMarshaller.unmarshal("String", EntryType.class), is(entryType));
    }


}
