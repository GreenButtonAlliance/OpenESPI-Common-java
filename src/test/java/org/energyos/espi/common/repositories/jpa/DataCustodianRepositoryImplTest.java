package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.DataCustodian;
import org.energyos.espi.common.repositories.DataCustodianRepository;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class DataCustodianRepositoryImplTest {

    @Autowired
    DataCustodianRepository repository;

    @Test
    public void findById() throws Exception {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        repository.persist(dataCustodian);

        assertEquals(dataCustodian.getId(), repository.findById(dataCustodian.getId()).getId());
    }

    @Test
    public void findAll() throws Exception {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        repository.persist(dataCustodian);

        assertTrue(repository.findAll().size() > 0);
    }

    @Test
    public void findByClientId() throws Exception {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        repository.persist(dataCustodian);

        assertEquals(dataCustodian.getId(), repository.findByClientId(dataCustodian.getClientId()).getId());
    }
}
