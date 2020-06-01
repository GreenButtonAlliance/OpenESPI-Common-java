/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.common.utils;

import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.models.atom.LinkType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntryLookupTableTests {

	@Test
	public void getUpEntry_returnsEntryForUpLink() {
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
	public void getRelatedEntries_returnsEntriesForRelatedLinks() {
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
