package org.energyos.espi.common.domain;


import org.energyos.espi.common.models.atom.LinkType;

import java.util.List;

public interface Linkable {
    LinkType getUpLink();

    void setUpResource(IdentifiedObject resource);

    String getParentQuery();

    String getAllRelatedQuery();

    List<String> getRelatedLinkHrefs();
}
