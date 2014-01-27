package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.BatchList;

public interface BatchListService {

    void persist(BatchList batchList);

    List<BatchList> findAll();
}
