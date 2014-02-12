package org.energyos.espi.common.test;

import java.util.Calendar;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.MeterReadingService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private ResourceService resourceService;

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
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        applicationInformationService.persist(applicationInformation);
        Authorization authorization = EspiFactory.newAuthorization(retailCustomer, applicationInformation);
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
        return createUsagePoint(createRetailCustomer());
    }

    public UsagePoint createUsagePoint(RetailCustomer retailCustomer) {
        UsagePoint usagePoint = EspiFactory.newUsagePoint(retailCustomer);
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

    public TimeConfiguration createLocalTimeParameters() {
        TimeConfiguration localTimeParameters = EspiFactory.newLocalTimeParameters();
        resourceService.persist(localTimeParameters);

        return localTimeParameters;
    }
}
