package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.IntervalBlock;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pivotal
 * Date: 9/5/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IntervalBlockRepository {
    List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId);
}
