package org.energyos.espi.common.domain;


import java.util.List;

import org.energyos.espi.common.models.atom.LinkType;

public interface Linkable {
    LinkType getUpLink();

    void setUpResource(IdentifiedObject resource);

    String getParentQuery();

    String getAllRelatedQuery();

    List<String> getRelatedLinkHrefs();
}
