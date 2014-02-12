package org.energyos.espi.common.repositories.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.energyos.espi.common.test.EspiFactory;
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

public class ApplicationInformationRepositoryImplTest {

    @Autowired
    private ApplicationInformationRepository repository;
    private ApplicationInformation applicationInformation;

    @Before
    public void setUp() throws Exception {
        applicationInformation = EspiFactory.newApplicationInformation();
        repository.persist(applicationInformation);
    }

    @Test
    public void persist() throws Exception {
        assertNotNull(applicationInformation.getId());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void persist_modelEnforcesUniqueClientId() throws Exception {
        ApplicationInformation duplicateApplicationInformation = EspiFactory.newApplicationInformation();
        duplicateApplicationInformation.setDataCustodianId(applicationInformation.getDataCustodianId());
        duplicateApplicationInformation.setClientId(applicationInformation.getClientId());

        repository.persist(duplicateApplicationInformation);
    }

    @Test
    public void findById() throws Exception {
        assertEquals(applicationInformation.getId(), repository.findById(applicationInformation.getId()).getId());
    }

    @Test
    public void findByClientId() throws Exception {
        assertEquals(applicationInformation.getId(), repository.findByClientId(applicationInformation.getClientId()).getId());
    }

    @Test
    public void findByDataCustodianClientId() throws Exception {
        assertEquals(applicationInformation.getId(), repository.findByDataCustodianClientId(applicationInformation.getDataCustodianId()).getId());
    }

    @Test
    public void findAll_returnsAllThirdParties() throws Exception {
        assertTrue("Repository has no data", repository.findAll().size() > 0);
    }
}
