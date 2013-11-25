package org.energyos.espi.common.utils;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;

public class EntryBuilder {

    private EntryType entry;

    public EntryType build(IdentifiedObject usagePoint) {
        entry = new EntryType();

        buildMetadata(usagePoint);
        buildContent(usagePoint);

        return entry;
    }

    private void buildContent(IdentifiedObject resource) {
        ContentType content = new ContentType();
        content.setResource(resource);
        entry.setContent(content);
    }

    private void buildMetadata(IdentifiedObject resource) {
        entry.setId("urn:uuid:" + resource.getUUID().toString());
        entry.setTitle(resource.getDescription());
        entry.setPublished(DateConverter.toDateTimeType(resource.getPublished()));
        entry.setUpdated(DateConverter.toDateTimeType(resource.getUpdated()));

        buildLinks(resource);
    }

    private void buildLinks(IdentifiedObject resource) {
        entry.getLinks().add(resource.getUpLink());
        entry.getLinks().add(new LinkType("self", resource.getSelfLink()));

        for (LinkType link : resource.getRelatedLinks()) {
            entry.getLinks().add(link);
        }
    }
}