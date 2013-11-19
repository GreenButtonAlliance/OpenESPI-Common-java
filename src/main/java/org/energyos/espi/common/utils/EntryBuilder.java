package org.energyos.espi.common.utils;

import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;

public class EntryBuilder {

    private EntryType entry;

    public EntryBuilder() {
        entry = new EntryType();
    }

    public EntryType build(UsagePoint usagePoint) {
        buildMetadata(usagePoint);
        buildContent(usagePoint);

        return entry;
    }

    private void buildContent(UsagePoint usagePoint) {
        ContentType content = new ContentType();
        content.setUsagePoint(usagePoint);
        entry.setContent(content);
    }

    private void buildMetadata(UsagePoint usagePoint) {
        entry.setId("urn:uuid:" + usagePoint.getUUID().toString());
        entry.setTitle(usagePoint.getDescription());
        entry.setPublished(DateConverter.toDateTimeType(usagePoint.getPublished()));
        entry.setUpdated(DateConverter.toDateTimeType(usagePoint.getUpdated()));

        buildLinks(usagePoint);
    }

    private void buildLinks(UsagePoint usagePoint) {
        entry.getLinks().add(new LinkType("up", usagePoint.getUpHref()));
        entry.getLinks().add(new LinkType("self", usagePoint.getSelfHref()));

        for (LinkType link : usagePoint.getRelatedLinks()) {
            entry.getLinks().add(link);
        }
    }
}