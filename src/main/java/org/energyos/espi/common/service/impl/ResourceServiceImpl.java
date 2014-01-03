package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.energyos.espi.common.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository repository;

    @Override
    public void persist(IdentifiedObject resource) {
        repository.persist(resource);
    }

    @Override
    public List<IdentifiedObject> findByAllParentsHref(String relatedHref, Linkable linkable) {
        try {
        	// note - for now, anything that will throw an error in the respository.findAllParentsByRelatedHref 
        	// are handled by this special case. when it gets too long (or we figure out a better way to indicate no parent, then 
        	// it should be changed. (a catch on the repository error would do it)
            if (linkable instanceof UsagePoint){
                return new ArrayList<>();
            } else {
                return repository.findAllParentsByRelatedHref(relatedHref, linkable);
            }
  //      } catch(EmptyResultDataAccessException x) {
            // I think the EmptyResultDataAccessException is going to have the same
            // transactional semantics as null (though that needs proven) in tests for 
            // ApplicationInformation and IntervalBlock
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<IdentifiedObject> findAllRelated(Linkable linkable) {
        return repository.findAllRelated(linkable);
    }

    @Override
    public <T> T findByUUID(UUID uuid, Class<T> clazz) {
        return repository.findByUUID(uuid, clazz);
    }

    @Override
    public <T extends IdentifiedObject> T findById(Long id, Class<T> clazz) {
        return repository.findById(id, clazz);
    }

    @Override
    public <T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz) {
        return repository.findAllIds(clazz);
    }

    @Override
    public <T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(Long id, Class<T> clazz) {
        return repository.findAllIdsByUsagePointId(id, clazz);
    }

    public void setRepository(ResourceRepository repository) {
        this.repository = repository;
    }
}
