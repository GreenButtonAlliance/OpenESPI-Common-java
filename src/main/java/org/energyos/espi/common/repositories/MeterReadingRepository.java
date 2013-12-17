package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.MeterReading;

import java.util.UUID;

public interface MeterReadingRepository {
    MeterReading findById(Long meterReadingId);

    void persist(MeterReading meterReading);

    MeterReading findByUUID(UUID uuid);
}
