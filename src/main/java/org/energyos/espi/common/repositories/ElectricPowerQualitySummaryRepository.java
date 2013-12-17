package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;

import java.util.UUID;

public interface ElectricPowerQualitySummaryRepository {
    ElectricPowerQualitySummary findById(Long electricPowerQualitySummaryId);

    void persist(ElectricPowerQualitySummary electricPowerQualitySummary);

    ElectricPowerQualitySummary findByUUID(UUID uuid);
}
