package org.energyos.espi.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;
import org.junit.Test;

public class EntryLookupTableTests {

    @Test
    public void getUpEntry_returnsEntryForUpLink() throws Exception {
        EntryType parentEntry = new EntryType();
        EntryType childEntry = new EntryType();

        childEntry.getLinks().add(newLink("up", "path/to/parent"));
        parentEntry.getLinks().add(newLink("self", "path/to/parent"));

        parentEntry.setTitle("parent");
        childEntry.setTitle("child");

        List<EntryType> entries = new ArrayList<>();
        entries.add(parentEntry);
        entries.add(childEntry);

        EntryLookupTable lookup = new EntryLookupTable(entries);
        assertEquals(parentEntry, lookup.getUpEntry(childEntry));
    }


    @Test
    public void getRelatedEntries_returnsEntriesForRelatedLinks() throws Exception {
        List<EntryType> entries = new ArrayList<>();

        EntryType entry = newEntry("entry");
        EntryType relation1 = newEntry("relation 1");
        EntryType relation2 = newEntry("relation 2");
        EntryType otherEntry = newEntry("other entry");

        entries.add(entry);
        entries.add(relation1);
        entries.add(relation2);
        entries.add(otherEntry);

        entry.getLinks().add(newLink("related", "path/to/relation1"));
        entry.getLinks().add(newLink("related", "path/to/relation2"));
        relation1.getLinks().add(newLink("self", "path/to/relation1"));
        relation2.getLinks().add(newLink("self", "path/to/relation2"));
        otherEntry.getLinks().add(newLink("self", "path/to/otherEntry"));

        EntryLookupTable lookup = new EntryLookupTable(entries);

        List<EntryType> relatedEntries = lookup.getRelatedEntries(entry);

        assertTrue(relatedEntries.contains(relation1));
        assertTrue(relatedEntries.contains(relation2));
        assertEquals(2, relatedEntries.size());
    }

    private EntryType newEntry(String title) {
        EntryType entry = new EntryType();
        entry.setTitle(title);
        return entry;
    }

    private LinkType newLink(String rel, String href) {
        LinkType link = new LinkType();
        link.setRel(rel);
        link.setHref(href);

        return link;
    }
}
