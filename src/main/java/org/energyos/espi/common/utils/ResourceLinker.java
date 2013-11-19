package org.energyos.espi.common.utils;

import org.energyos.espi.common.domain.*;
import org.energyos.espi.common.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component
@Transactional
public class ResourceLinker {
    @Autowired
    private ResourceService resourceService;

    public void link(IdentifiedObject resource) {
        linkUp(resource);
        linkUpMember(resource);
        resourceService.persist(resource);
        linkRelatedCollection(resource);
    }

    public void linkUp(IdentifiedObject resource) {
        if (resource.getUpLink() != null) {
            List<IdentifiedObject> parents = resourceService.findByAllParentsHref(resource.getUpLink().getHref(), resource);
            for(IdentifiedObject parent : parents) {
                if (parent != null) {
                    resource.setUpResource(parent);
                    resourceService.persist(parent);
                    resourceService.persist(resource);
                }
            }
        }
    }

    public void linkUpMember(IdentifiedObject resource) {
        if (resource.getSelfLink() != null) {
            List<IdentifiedObject> parentResources = resourceService.findByAllParentsHref(resource.getSelfLink(), resource);
            for(IdentifiedObject parentResource : parentResources) {
                if (parentResource != null) {
                    if (resource instanceof TimeConfiguration) {
                        UsagePoint usagePoint = (UsagePoint)parentResource;
                        usagePoint.setLocalTimeParameters((TimeConfiguration)resource);
                    }

                    if (resource instanceof ReadingType) {
                        MeterReading meterReading = (MeterReading) parentResource;
                        meterReading.setReadingType((ReadingType) resource);
                    }
                    resourceService.persist(parentResource);
                }
            }
        }
    }

    public void linkRelatedCollection(IdentifiedObject resource) {
            List<IdentifiedObject> relatedResources = resourceService.findAllRelated(resource);

            for(IdentifiedObject relatedResource : relatedResources) {
                relatedResource.setUpResource(resource);
                resourceService.persist(relatedResource);
            }
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
}
