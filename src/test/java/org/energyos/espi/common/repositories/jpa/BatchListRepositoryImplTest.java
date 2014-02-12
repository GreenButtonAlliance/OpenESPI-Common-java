package org.energyos.espi.common.repositories.jpa;

import static org.energyos.espi.common.test.EspiFactory.newBatchList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.repositories.BatchListRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class BatchListRepositoryImplTest {

    @Autowired
    BatchListRepository repository;

    public BatchList batchList;

    @Before
    public void setup() {
        batchList = newBatchList();
        repository.persist(batchList);
    }

    @Test
    public void persist() throws Exception {
        assertNotNull(batchList.getId());
    }

    @Test
    public void findAll() {
        List<BatchList> foundList = repository.findAll();

        assertThat(batchList.getId(), is(foundList.get(0).getId()));
    }
}
