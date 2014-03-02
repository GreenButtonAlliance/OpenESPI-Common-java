package org.energyos.espi.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ResourceService;

public class EntryTypeIterator {

    private EntryBuilder builder;

    private Iterator<Long> resourceIds;
    @SuppressWarnings("rawtypes")

	private Iterator<Pair<Long, Class>> childIds = new ArrayList<Pair<Long, Class>>().iterator();
    private ResourceService resourceService;
    
    @SuppressWarnings("rawtypes")
    // TODO: fix the EntryTypeIterator Typing System
	private Class rootClass;

    public EntryTypeIterator(ResourceService resourceService, List<Long> ids, EntryBuilder builder) {
        this.resourceService = resourceService;
        this.resourceIds = ids.iterator();
        this.builder = builder;
    }

    @SuppressWarnings("rawtypes")
    // TODO: fix the EntryTypeIterator Typing System
	public EntryTypeIterator(ResourceService resourceService, List<Long> ids, Class clazz) {
        this.resourceService = resourceService;
        this.resourceIds = ids.iterator();
        builder = new EntryBuilder();
        rootClass = clazz;
    }

    public boolean hasNext() {
        return childIds.hasNext() || resourceIds.hasNext();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    // TODO: fix the EntryTypeIterator Typing System
	public EntryType next()  {
        IdentifiedObject resource;

        if(childIds.hasNext()) {
            Pair<Long, Class> pair = childIds.next();
            resource = resourceService.findById(pair.getKey(), pair.getValue());
        } else {
            resource = resourceService.findById(resourceIds.next(), rootClass);
            updateChildIds(resource.getId());
        }

        return builder.build(resource);
    }

    // For the RESTful interfaces, we don't want to build the whole child structure, 
    // only the 1 resource is exported.
    //
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // TODO: fix the EntryTypeIterator Typing System
	public EntryType nextEntry(Class resourceClass)  {
        IdentifiedObject resource;
        resource = resourceService.findById(resourceIds.next(), resourceClass);
        return builder.buildEntry(resource);
    }

    @SuppressWarnings("rawtypes")
    // TODO: fix the EntryTypeIterator Typing System
	private void updateChildIds(Long usagePointId) {
    	// TODO: Deal with these Class warnings...
    	
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
