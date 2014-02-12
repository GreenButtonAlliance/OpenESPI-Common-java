package org.energyos.espi.common.service.impl;

import static org.energyos.espi.common.test.EspiFactory.newBatchList;
import static org.mockito.Mockito.verify;

import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.repositories.BatchListRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BatchListServiceImplTest {

    @Mock
    public BatchListRepository repository;

    public BatchListServiceImpl batchListService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        batchListService = new BatchListServiceImpl();
        batchListService.setRepository(repository);
    }

    @Test
    public void persist() {
        BatchList batchList = newBatchList();
        batchListService.persist(batchList);
        verify(repository).persist(batchList);
    }

    @Test
    public void findAll() {
        batchListService.findAll();
        verify(repository).findAll();
    }
}
