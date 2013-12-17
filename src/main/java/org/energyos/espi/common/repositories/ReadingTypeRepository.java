package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.ReadingType;

import java.util.UUID;

public interface ReadingTypeRepository {
    ReadingType findById(Long readingTypeId);

    void persist(ReadingType readingType);

    ReadingType findByUUID(UUID uuid);
}
