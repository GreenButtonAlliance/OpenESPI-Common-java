package org.energyos.espi.common.utils.factories;

import static org.energyos.espi.common.test.EspiFactory.newUsagePointWithId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.utils.SubscriptionBuilder;

import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Link;
import com.sun.syndication.io.FeedException;

public class FeedFactory {
    private FeedFactory() {}

    @SuppressWarnings({ "deprecation", "unchecked" })
	public static Feed newFeed() throws FeedException {
        SubscriptionBuilder subscriptionBuilder = new SubscriptionBuilder();
        List<UsagePoint> usagePoints = new ArrayList<>();
        usagePoints.add(newUsagePointWithId());

        Feed feed = subscriptionBuilder.buildFeed(usagePoints);
        feed.setId("urn:uuid:0071C5A7-91CF-434E-8BCE-C38AC8AF215D");
        feed.setTitle("Feed title");
        feed.setUpdated(new Date(113, 11, 28));

        Link self = newLink("self", "/ThirdParty/83e269c1/Batch");
        feed.getOtherLinks().add(self);

        return feed;
    }

    public static Link newLink(String rel, String href) {
        Link entrySelf = new Link();
        entrySelf.setRel(rel);
        entrySelf.setHref(href);
        return entrySelf;
    }
}
