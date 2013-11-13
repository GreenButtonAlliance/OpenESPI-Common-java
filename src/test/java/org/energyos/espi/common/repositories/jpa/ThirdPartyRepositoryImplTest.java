package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.ThirdParty;
import org.energyos.espi.common.repositories.ThirdPartyRepository;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class ThirdPartyRepositoryImplTest {

    @Autowired
    private ThirdPartyRepository repository;
    private ThirdParty thirdParty;

    @Before
    public void setUp() throws Exception {
        thirdParty = EspiFactory.newThirdParty();
        repository.persist(thirdParty);
    }

    @Test
    public void persist() throws Exception {
        assertNotNull(thirdParty.getId());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void persist_modelEnforcesUniqueClientId() throws Exception {
        ThirdParty thirdParty2 = EspiFactory.newThirdParty();
        thirdParty2.setClientId(thirdParty.getClientId());

        repository.persist(thirdParty2);
    }

    @Test
    public void findById() throws Exception {
        assertEquals(thirdParty.getId(), repository.findById(thirdParty.getId()).getId());
    }

    @Test
    public void findByClientId() throws Exception {
        assertEquals(thirdParty.getId(), repository.findByClientId(thirdParty.getClientId()).getId());
    }

    @Test
    public void findAll_returnsAllThirdParties() throws Exception {
        assertTrue("Repository has no data", repository.findAll().size() > 0);
    }
}
