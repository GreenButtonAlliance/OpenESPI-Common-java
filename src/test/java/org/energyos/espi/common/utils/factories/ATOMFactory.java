package org.energyos.espi.common.utils.factories;

import com.sun.syndication.io.FeedException;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.FeedType;
import org.energyos.espi.common.utils.DateConverter;
import org.energyos.espi.common.utils.FeedBuilder;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.*;

import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;

public class ATOMFactory {
    private ATOMFactory() {
    }

    public static FeedType newFeedType() throws DatatypeConfigurationException, FeedException {

        UsagePoint usagePoint = newUsagePoint();
        List<UsagePoint> usagePoints = new ArrayList<>();
        usagePoints.add(usagePoint);

        FeedType feed = new FeedBuilder().build(usagePoints);

        feed.setId("urn:uuid:0071C5A7-91CF-434E-8BCE-C38AC8AF215D");
        feed.setTitle("ThirdPartyX Batch Feed");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2012, Calendar.SEPTEMBER, 14, 00, 00, 00);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = gregorianCalendar.getTime();
        feed.setUpdated(DateConverter.toDateTimeType(date));

        return feed;
    }

}
