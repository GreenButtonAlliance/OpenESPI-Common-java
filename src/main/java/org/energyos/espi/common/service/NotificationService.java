package org.energyos.espi.common.service;

import javax.xml.datatype.XMLGregorianCalendar;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    void notify(Subscription subscription, XMLGregorianCalendar startDate, XMLGregorianCalendar endDate);
    
    void notify(RetailCustomer retailCustomer, XMLGregorianCalendar startDate, XMLGregorianCalendar endDate);
    
    void notifyAllNeed();
    
    void notify(ApplicationInformation applicationInformation, Long bulkId);
    
}
