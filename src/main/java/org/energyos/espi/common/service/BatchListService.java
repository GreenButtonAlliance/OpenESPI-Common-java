package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.BatchList;

import java.util.List;

public interface BatchListService {

    void persist(BatchList batchList);

    List<BatchList> findAll();
}
