package org.energyos.espi.common.test;

import org.energyos.espi.common.domain.*;
import org.energyos.espi.common.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class EspiPersistenceFactory {
    @Autowired
    private ApplicationInformationService applicationInformationService;
    @Autowired
    private RetailCustomerService retailCustomerService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UsagePointService usagePointService;
    @Autowired
    private MeterReadingService meterReadingService;
    @Autowired
    private DataCustodianService dataCustodianService;

    public Subscription createSubscription() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);

        return createSubscription(retailCustomer);
    }

    public Subscription createSubscription(RetailCustomer retailCustomer) {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        applicationInformationService.persist(applicationInformation);

        Subscription subscription = EspiFactory.newSubscription(retailCustomer, applicationInformation);
        subscription.setLastUpdate(Calendar.getInstance());
        subscriptionService.persist(subscription);

        return subscription;
    }

    public Authorization createAuthorization(RetailCustomer retailCustomer) {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        dataCustodianService.persist(dataCustodian);
        Authorization authorization = EspiFactory.newAuthorization(retailCustomer, dataCustodian);
        authorizationService.persist(authorization);

        return authorization;
    }

    public Authorization createAuthorization() {
        Subscription subscription = createSubscription();
        Authorization authorization = EspiFactory.newAuthorization(subscription);
        authorizationService.persist(authorization);

        return authorization;
    }

    public RetailCustomer createRetailCustomer() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);

        return retailCustomer;
    }

    public UsagePoint createUsagePoint() {
        UsagePoint usagePoint = EspiFactory.newUsagePoint(createRetailCustomer());
        usagePointService.persist(usagePoint);

        return usagePoint;
    }

    public MeterReading createMeterReading() {
        MeterReading meterReading = EspiFactory.newMeterReading();
        meterReadingService.persist(meterReading);

        return meterReading;
    }

    public ApplicationInformation createApplicationInformation() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        applicationInformationService.persist(applicationInformation);

        return applicationInformation;
    }
}
