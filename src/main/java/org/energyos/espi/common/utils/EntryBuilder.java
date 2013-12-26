/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.energyos.espi.common.utils;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;

public class EntryBuilder {

    private EntryType entry;

    public EntryType build(IdentifiedObject reource) {
        entry = new EntryType();

        buildMetadata(reource);
        buildContent(reource);

        return entry;
    }

    public EntryType buildEntry(IdentifiedObject resource) {
        entry = new EntryType();

        buildMetadata(resource);
        buildContent(resource);

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
        entry.getLinks().add(resource.getSelfLink());

        for (LinkType link : resource.getRelatedLinks()) {
            entry.getLinks().add(link);
        }
    }
}