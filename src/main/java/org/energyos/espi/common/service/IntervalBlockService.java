package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.repositories.IntervalBlockRepository;

import java.util.List;

public interface IntervalBlockService {
    List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId);

    void setRepository(IntervalBlockRepository repository);
}
