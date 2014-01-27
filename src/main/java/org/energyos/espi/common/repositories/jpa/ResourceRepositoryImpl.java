package org.energyos.espi.common.repositories.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        try {
            String queryFindById = (String) clazz.getDeclaredField("QUERY_FIND_BY_UUID").get(String.class);

            return (T) em.createNamedQuery(queryFindById).setParameter("uuid", uuid.toString().toUpperCase()).getSingleResult();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public <T extends IdentifiedObject> T findById(Long id, Class<T> clazz) {
        try {
            String queryFindById = (String) clazz.getDeclaredField("QUERY_FIND_BY_ID").get(String.class);

            return (T) em.createNamedQuery(queryFindById).setParameter("id", id).getSingleResult();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public <T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz) {
        try {
            String queryFindById = (String) clazz.getDeclaredField("QUERY_FIND_ALL_IDS").get(String.class);

            return em.createNamedQuery(queryFindById).getResultList();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(Long usagePointId, Class<T> clazz) {
        try {
            String queryFindAllIdsByUsagePointId = (String) clazz.getDeclaredField("QUERY_FIND_ALL_IDS_BY_USAGE_POINT_ID").get(String.class);

            return em.createNamedQuery(queryFindAllIdsByUsagePointId).setParameter("usagePointId", usagePointId).getResultList();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UsagePoint findByUUID(UUID uuid) {
        return findByUUID(uuid, UsagePoint.class);
    }

    // Collection XPath Accessors
    //
    
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Class<T> clazz) {
        try {
            String findAllIdsByXPath = (String) clazz.getDeclaredField("QUERY_FIND_ALL_IDS_BY_XPATH_0").get(String.class);
            Query query = em.createNamedQuery(findAllIdsByXPath);
            return query.getResultList();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Class<T> clazz) {
        try {
            String findAllIdsByXPath = (String) clazz.getDeclaredField("QUERY_FIND_ALL_IDS_BY_XPATH_1").get(String.class);
            Query query = em.createNamedQuery(findAllIdsByXPath).setParameter("o1Id", id1);
            return query.getResultList();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Long id2, Class<T> clazz) {
	        try {
	            String findAllIdsByXPath = (String) clazz.getDeclaredField("QUERY_FIND_ALL_IDS_BY_XPATH_2").get(String.class);
                Query query = em.createNamedQuery(findAllIdsByXPath)
	            		          .setParameter("o1Id", id1)
	            		           .setParameter("o2Id", id2);
	            return query.getResultList();
	        } catch (NoSuchFieldException | IllegalAccessException e) {
	            throw new RuntimeException(e);
	        }
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Long id3, Class<T> clazz) {
        try {
            String findAllIdsByXPath = (String) clazz.getDeclaredField("QUERY_FIND_ALL_IDS_BY_XPATH_3").get(String.class);
            Query query = em.createNamedQuery(findAllIdsByXPath)
            		.setParameter("o1Id", id1)
            		.setParameter("o2Id", id2)
            		.setParameter("o3Id", id3);
            return query.getResultList();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
	}
	
	// Member XPath Accessors
	//
	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1,
			Class<T> clazz) {
        try {
            String findIdByXPath = (String) clazz.getDeclaredField("QUERY_FIND_ID_BY_XPATH").get(String.class);
            Query query = em.createNamedQuery(findIdByXPath)
            		.setParameter("o1Id", id1);
            return (Long) query.getSingleResult();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
	}
	



	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Class<T> clazz) {
        try {
            String findIdByXPath = (String) clazz.getDeclaredField("QUERY_FIND_ID_BY_XPATH").get(String.class);
            Query query = em.createNamedQuery(findIdByXPath)
            		          .setParameter("o1Id", id1)
            		           .setParameter("o2Id", id2);
            return (Long) query.getSingleResult();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Class<T> clazz) {
        try {
            String findIdByXPath= (String) clazz.getDeclaredField("QUERY_FIND_ID_BY_XPATH").get(String.class);
            Query query = em.createNamedQuery(findIdByXPath)
            		.setParameter("o1Id", id1)
            		.setParameter("o2Id", id2)
            		.setParameter("o3Id", id3);
            return (Long) query.getSingleResult();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz) {
        try {
            String findIdByXPath = (String) clazz.getDeclaredField("QUERY_FIND_ID_BY_XPATH").get(String.class);
            Query query = em.createNamedQuery(findIdByXPath)
            		.setParameter("o1Id", id1)
            		.setParameter("o2Id", id2)
            		.setParameter("o3Id", id3)
            		.setParameter("o4Id", id4);
            return (Long) query.getSingleResult();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
	}

    @Transactional
    public void update(UsagePoint updatedUsagePoint) {
        UsagePoint originalUsagePoint = findByUUID(updatedUsagePoint.getUUID());
        originalUsagePoint.setDescription(updatedUsagePoint.getDescription());
        originalUsagePoint.setRoleFlags(updatedUsagePoint.getRoleFlags());
        originalUsagePoint.setServiceCategory(updatedUsagePoint.getServiceCategory());
        originalUsagePoint.setStatus(updatedUsagePoint.getStatus());

        em.merge(originalUsagePoint);
    }


}
