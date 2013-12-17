package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.utils.EntryTypeIterator;

import java.util.UUID;

public interface TimeConfigurationRepository {
    TimeConfiguration findById(Long timeConfigurationId);

    void persist(TimeConfiguration timeConfiguration);

    TimeConfiguration findByUUID(UUID uuid);
    
    EntryType find(Long retailCustomerId, Long usagePointId, Long timeConfigurationId);

    EntryTypeIterator find(Long retailCustomerId, Long usagePointId);
}
