package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.BatchList;

import java.util.List;


public interface BatchListRepository {
    void persist(BatchList batchList);

    List<BatchList> findAll();
}
