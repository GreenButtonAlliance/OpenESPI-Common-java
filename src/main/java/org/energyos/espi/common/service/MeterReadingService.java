package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.repositories.MeterReadingRepository;

public interface MeterReadingService {
    MeterReading findById(Long meterReadingId);

    void persist(MeterReading meterReading);

    void setRepository(MeterReadingRepository repository);

}
