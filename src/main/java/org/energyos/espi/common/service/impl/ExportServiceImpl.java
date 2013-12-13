package org.energyos.espi.common.service.impl;


import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ExportService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamResult;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private Jaxb2Marshaller fragmentMarshaller;

    @Override
    public void exportSubscription(String subscriptionHashedId, OutputStream stream, ExportFilter exportFilter) throws IOException {
        exportEntries(subscriptionService.findEntriesByHashedId(subscriptionHashedId), stream, exportFilter);
    }

    @Override
    public void exportUsagePoints(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws IOException {
        exportEntries(subscriptionService.findEntriesByRetailCustomerId(retailCustomerId), stream, exportFilter);
    }

    private void exportEntries(EntryTypeIterator entries, OutputStream stream, ExportFilter exportFilter) throws IOException {

        stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
        stream.write("<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">".getBytes());

        StreamResult result = new StreamResult(stream);

        while (entries.hasNext()) {
            EntryType entryType = entries.next();
            if (exportFilter.matches(entryType)) {
                fragmentMarshaller.marshal(entryType, result);
            }
        }

        stream.write("</feed>".getBytes());
    }

    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public void setMarshaller(Jaxb2Marshaller fragmentMarshaller) {
        this.fragmentMarshaller = fragmentMarshaller;
    }
}
