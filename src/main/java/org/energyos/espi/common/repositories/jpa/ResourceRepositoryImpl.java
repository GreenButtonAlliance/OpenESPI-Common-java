package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
class ResourceRepositoryImpl implements ResourceRepository {
    @PersistenceContext
    protected EntityManager em;

    @Override
    public void persist(IdentifiedObject resource) {
        em.persist(resource);
    }

    @Override
    public List<IdentifiedObject> findAllParentsByRelatedHref(String href, Linkable linkable) {
        return em.createNamedQuery(linkable.getParentQuery()).setParameter("href", href).getResultList();
    }

    @Override
    public List<IdentifiedObject> findAllRelated(Linkable linkable) {
        if (linkable.getRelatedLinkHrefs().isEmpty()) {
            return new ArrayList<>();
        }
        return em.createNamedQuery(linkable.getAllRelatedQuery()).setParameter("relatedLinkHrefs", linkable.getRelatedLinkHrefs()).getResultList();
    }

    @Override
    public <T> T findByUUID(UUID uuid, Class<T> clazz) {
        return (T)em.createQuery("SELECT resource FROM " + clazz.getCanonicalName() + " resource WHERE resource.uuid = :uuid")
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }
}
