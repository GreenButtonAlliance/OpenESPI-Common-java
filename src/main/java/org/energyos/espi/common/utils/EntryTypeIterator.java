package org.energyos.espi.common.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.energyos.espi.common.domain.*;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ResourceService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntryTypeIterator {

    private EntryBuilder builder;

    private Iterator<Long> usagePointIds;
    private Iterator<Pair<Long, Class>> childIds = new ArrayList<Pair<Long, Class>>().iterator();
    private ResourceService resourceService;

    public EntryTypeIterator(ResourceService resourceService, List<Long> usagePointIds, EntryBuilder builder) {
        this.resourceService = resourceService;
        this.usagePointIds = usagePointIds.iterator();
        this.builder = builder;
    }

    public EntryTypeIterator(ResourceService resourceService, List<Long> usagePointIds) {
        this.resourceService = resourceService;
        this.usagePointIds = usagePointIds.iterator();
        builder = new EntryBuilder();
    }

    public boolean hasNext() {
        return childIds.hasNext() || usagePointIds.hasNext();
    }

    public EntryType next()  {
        IdentifiedObject resource;
        if(childIds.hasNext()) {
            Pair<Long, Class> pair = childIds.next();
            resource = resourceService.findById(pair.getKey(), pair.getValue());
        } else {
            resource = resourceService.findById(usagePointIds.next(), UsagePoint.class);
            updateChildIds(resource.getId());
        }

        return builder.build(resource);
    }

    private void updateChildIds(Long usagePointId) {
        List<Pair<Long, Class>> pairs = new ArrayList<>();
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, TimeConfiguration.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, TimeConfiguration.class));
        }
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, MeterReading.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, MeterReading.class));
        }
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, IntervalBlock.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, IntervalBlock.class));
        }
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, ElectricPowerUsageSummary.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, ElectricPowerUsageSummary.class));
        }
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, ElectricPowerQualitySummary.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, ElectricPowerQualitySummary.class));
        }
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, ReadingType.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, ReadingType.class));
        }
        childIds = pairs.iterator();
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
}
