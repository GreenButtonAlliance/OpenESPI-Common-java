/*
 * Copyright 2013 EnergyOS.org
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

package org.energyos.espi.common.atom;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Link;
import com.sun.syndication.io.FeedException;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.utils.EspiMarshaller;

import java.util.ArrayList;
import java.util.List;

public abstract class EspiEntry<T extends IdentifiedObject> extends Entry {
    private Link selfLink = new Link();
    private Link upLink = new Link();
    private List<Link> relatedLinks = new ArrayList<>();

    protected T espiObject;

    public EspiEntry(T espiObject) throws FeedException {
        this.espiObject = espiObject;
        this.setTitle(espiObject.getDescription());
        this.setId(espiObject.getMRID());
        this.setPublished(espiObject.getCreated());
        this.setUpdated(espiObject.getUpdated());

        selfLink.setRel("self");
        selfLink.setHref(getSelfHref());
        upLink.setRel("up");
        upLink.setHref(getUpHref());

        getOtherLinks().add(selfLink);
        getOtherLinks().add(upLink);

        buildRelatedLinks();

        Content content = new Content();
        content.setValue(EspiMarshaller.marshal(espiObject));
        this.getContents().add(content);
    }

    protected abstract String getSelfHref();
    protected abstract String getUpHref();
    protected abstract void buildRelatedLinks();

    public Link getSelfLink() {
        return selfLink;
    }

    public Link getUpLink() {
        return upLink;
    }

    public List<Link> getRelatedLinks() {
        return relatedLinks;
    }

    protected void addRelatedLink(String href) {
        Link link = new Link();
        link.setRel("related");
        link.setHref(href);

        relatedLinks.add(link);
        getOtherLinks().add(link);
    }

    protected void setUpLinkHref(String href) {
        getUpLink().setHref(href);
    }

    protected void setSelfLinkHref(String href) {
        getSelfLink().setHref(href);
    }
}