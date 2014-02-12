package org.energyos.espi.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;

class EntryLookupTable {
    private Map<String, EntryType> entryMap;

    public EntryLookupTable(List<EntryType> entries) {
        entryMap = new HashMap<>();

        addSelfLinks(entries);
        addRelatedLinks(entries);
    }

    public EntryType getUpEntry(EntryType entry) {
        return entryMap.get(getUpLinkHref(entry));
    }

    public List<EntryType> getRelatedEntries(EntryType entryType) {
        List<EntryType> relatedEntries = new ArrayList<>();
        for (LinkType link : entryType.getLinks()) {
            if (link.getRel().equals("related")) {
                relatedEntries.add(entryMap.get(link.getHref()));
            }
        }
        return relatedEntries;
    }

    private void addSelfLinks(List<EntryType> entries) {
        for (EntryType entry : entries) {
            entryMap.put(getSelfLinkHref(entry), entry);
        }
    }

    private void addRelatedLinks(List<EntryType> entries) {
        for (EntryType entry : entries) {
            for (LinkType link : entry.getLinks()) {
                if (link.getRel().equals("related") && !entryMap.containsKey(link.getHref())) {
                    entryMap.put(link.getHref(), entry);
                }
            }
        }
    }

    private String getSelfLinkHref(EntryType entry) {
        return getLinkHrefForType(entry, "self");
    }

    private String getUpLinkHref(EntryType entry) {
        return getLinkHrefForType(entry, "up");
    }

    private String getLinkHrefForType(EntryType entry, String type) {
        for (LinkType link : entry.getLinks()) {
            if (link.getRel().equals(type)) {
                return link.getHref();
            }
        }
        return null;
    }
}

