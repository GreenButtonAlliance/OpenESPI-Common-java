package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;

import java.util.UUID;

public interface ElectricPowerUsageSummaryRepository {
    ElectricPowerUsageSummary findById(Long electricPowerUsageSummaryId);

    void persist(ElectricPowerUsageSummary electricPowerUsageSummary);

    ElectricPowerUsageSummary findByUUID(UUID uuid);
}
