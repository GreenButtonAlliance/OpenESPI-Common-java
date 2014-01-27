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

package org.energyos.espi.common.models.atom.adapters;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.models.atom.ObjectFactory;

public class LinkAdapter extends XmlAdapter<JAXBElement<LinkType>, LinkType> {
    @Override
    public LinkType unmarshal(JAXBElement<LinkType> v) throws Exception {
        return (LinkType)v.getValue();
    }

    @Override
    public JAXBElement<LinkType> marshal(LinkType v) throws Exception {
        return new JAXBElement<LinkType>(ObjectFactory.SourceTypeLink_QNAME, LinkType.class, v);
    }
}
