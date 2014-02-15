package org.energyos.espi.common.atom;

import java.util.ArrayList;
import java.util.List;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.utils.EspiMarshaller;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.io.FeedException;

public class IntervalBlocksEntry extends EspiEntry<IntervalBlock> {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntervalBlocksEntry(List<IntervalBlock> intervalBlocks) throws FeedException {
        super(intervalBlocks.get(0));

        this.setContents(buildContents(intervalBlocks));
    }

    private List<Content> buildContents(List<IntervalBlock> intervalBlocks) throws FeedException {
        Content content = new Content();
        List<Content> contents = new ArrayList<>();
        StringBuilder intervalBlocksXml = new StringBuilder(40);

        for (IntervalBlock intervalBlock : intervalBlocks) {
            intervalBlocksXml.append(EspiMarshaller.marshal(intervalBlock));
            intervalBlocksXml.append("\n");
        }

        content.setValue(intervalBlocksXml.toString());
        contents.add(content);

        return contents;
    }

    protected String getSelfHref() {
        MeterReading meterReading = espiObject.getMeterReading();
        UsagePoint usagePoint = meterReading.getUsagePoint();

        return "RetailCustomer/" + usagePoint.getRetailCustomer().getId() +
                "/UsagePoint/" + usagePoint.getId() +
                "/MeterReading/" + meterReading.getId() +
                "/IntervalBlock/" + espiObject.getId();
    }

    protected String getUpHref() {
        MeterReading meterReading = espiObject.getMeterReading();
        UsagePoint usagePoint = meterReading.getUsagePoint();

        return "RetailCustomer/" + usagePoint.getRetailCustomer().getId() +
                "/UsagePoint/" + usagePoint.getId() +
                "/MeterReading/" + meterReading.getId() +
                "/IntervalBlock";
    }

    protected void buildRelatedLinks() {}
}
