package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;

import java.util.List;
import java.util.UUID;

public interface ResourceService {
    void persist(IdentifiedObject resource);

    List<IdentifiedObject> findByAllParentsHref(String relatedHref, Linkable linkable);

    List<IdentifiedObject> findAllRelated(Linkable resource);

    <T> T findByUUID(UUID uuid, Class<T> clazz);

    <T extends IdentifiedObject> T findById(Long id, Class<T> clazz);

    <T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz);

    <T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(Long id, Class<T> clazz);
}
