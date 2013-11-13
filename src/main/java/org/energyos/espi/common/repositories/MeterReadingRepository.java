package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.MeterReading;

public interface MeterReadingRepository {
    MeterReading findById(Long meterReadingId);

    void persist(MeterReading meterReading);
}
