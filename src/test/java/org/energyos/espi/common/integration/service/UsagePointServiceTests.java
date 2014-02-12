package org.energyos.espi.common.integration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.test.EspiFactory;
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

public class UsagePointServiceTests {
    @Autowired
    private UsagePointService usagePointService;
    @Autowired
    private RetailCustomerService retailCustomerService;

    @Test
    public void associateByUUID_nonExistentUsagePoint() {
        UUID uuid = UUID.randomUUID();
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);

        usagePointService.associateByUUID(retailCustomer, uuid);

        assertNotNull(usagePointService.findByUUID(uuid));
        assertEquals(retailCustomer.getId(), usagePointService.findByUUID(uuid).getRetailCustomer().getId());
        assertTrue(usagePointService.findAllByRetailCustomer(retailCustomer).size() > 0);
    }

    @Test
    public void associateByUUID_existingUsagePoint() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);

        UsagePoint usagePoint = EspiFactory.newUsagePoint(retailCustomer);

        usagePoint.setRetailCustomer(null);
        usagePointService.persist(usagePoint);

        usagePointService.associateByUUID(retailCustomer, usagePoint.getUUID());

        UsagePoint existingUsagePoint = usagePointService.findByUUID(usagePoint.getUUID());

        assertNotNull(existingUsagePoint.getRetailCustomer());
        assertEquals(retailCustomer.getId(), existingUsagePoint.getRetailCustomer().getId());
    }

    @Test
    public void createOrReplacebyUUID_existingUsagePoint_persistsLocalTimeParameters() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);

        UsagePoint usagePoint = EspiFactory.newUsagePoint(null);
        usagePointService.persist(usagePoint);

        UsagePoint newUsagePoint = EspiFactory.newUsagePoint(retailCustomer);
        newUsagePoint.setUUID(usagePoint.getUUID());
        newUsagePoint.setLocalTimeParameters(null);

        usagePointService.createOrReplaceByUUID(newUsagePoint);

        UsagePoint existingUsagePoint = usagePointService.findByUUID(newUsagePoint.getUUID());

        assertNotNull(existingUsagePoint.getLocalTimeParameters());
    }

    @Test
    public void createOrReplacebyUUID_existingUsagePoint_associatesRetailCustomer() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);

        UsagePoint usagePoint = EspiFactory.newUsagePoint(null);
        usagePointService.persist(usagePoint);

        UsagePoint newUsagePoint = EspiFactory.newUsagePoint(retailCustomer);
        newUsagePoint.setUUID(usagePoint.getUUID());


        usagePointService.createOrReplaceByUUID(newUsagePoint);

        UsagePoint existingUsagePoint = usagePointService.findByUUID(newUsagePoint.getUUID());

        assertEquals(retailCustomer.getId(), existingUsagePoint.getRetailCustomer().getId());
    }
}
