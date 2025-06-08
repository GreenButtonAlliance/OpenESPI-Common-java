/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.repositories.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenbuttonalliance.espi.common.domain.*;
import org.greenbuttonalliance.espi.common.repositories.ResourceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
class ResourceRepositoryImpl implements ResourceRepository {

	private final Log logger = LogFactory.getLog(getClass());

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void persist(IdentifiedObject resource) {
		em.persist(resource);
	}

	@Override
	public void flush() {
		em.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdentifiedObject> findAllParentsByRelatedHref(String href,
			Linkable linkable) {
		String queryString = linkable.getParentQuery();
		List<IdentifiedObject> result = em.createNamedQuery(queryString)
				.setParameter("href", href).getResultList();
		if (result.isEmpty()) {
				// we did not find one, so now try to parse the URL and find the
				// parent that way
				// this needed only b/c we are not storing the up and self hrefs
				// in
				// the underlying db but rather
				// relying upon a structured URL for resources that we have
				// exported.
				//
				boolean usagePointFlag = false;
				boolean meterReadingFlag = false;

				Long usagePointId = null;
				Long meterReadingId = null;

			try {
				for (String token : href.split("/")) {
					if (usagePointFlag) {
						if (token.length() != 0) {
							usagePointId = Long.decode(token);
						}
						usagePointFlag = false;
					}

					if (meterReadingFlag) {
						if (token.length() != 0) {
							meterReadingId = Long.decode(token);
						}
						meterReadingFlag = false;
					}

					if (token.equals("UsagePoint")) {
						usagePointFlag = true;
					}

					if (token.equals("MeterReading")) {
						meterReadingFlag = true;
					}

				}

				if (meterReadingId != null) {
					result.add(findById(meterReadingId, MeterReading.class));
				} else {
					if (usagePointId != null) {
						result.add(findById(usagePointId, UsagePoint.class));
					}

				}

			} catch (NoResultException e) {
				// nothing to do, just return the empty result and
				// we'll find it later.
				if(logger.isInfoEnabled()) {
					logger.info("**** findAllParentsByRelatedHref(String href) NoResultException: " + e.toString() + "&n" +
							"     usagePointId: " + usagePointId + "     meterReadingId: " + meterReadingId + "&n" +
							"     href: " + href + "&n");
				}

			} catch (Exception e) {
				// nothing to do, just return the empty result and
				// we'll find it later.
				if(logger.isInfoEnabled()){
					logger.info("**** findAllParentsByRelatedHref(String href) Exception: " + e.toString() + "&n" +
							"     href: " + href + "&n");
				}
			}

		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdentifiedObject> findAllRelated(Linkable linkable) {
		if (linkable.getRelatedLinkHrefs().isEmpty()) {
			return new ArrayList<>();
		}
		List<IdentifiedObject> temp = em
				.createNamedQuery(linkable.getAllRelatedQuery())
				.setParameter("relatedLinkHrefs",
						linkable.getRelatedLinkHrefs()).getResultList();
		// Check for specific related that do not have their href's stored
		// in the DB or imported objects that have the external href's stored
		if (temp.isEmpty()) {
			try {
				boolean localTimeParameterFlag = false;
				boolean readingTypeFlag = false;
				boolean electricPowerUsageFlag = false;
				boolean electricPowerQualityFlag = false;

				Long localTimeParameterId = null;
				Long readingTypeId = null;
				Long electricPowerUsageId = null;
				Long electricPowerQualityId = null;

				for (String href : linkable.getRelatedLinkHrefs()) {
					for (String token : href.split("/")) {
						if (localTimeParameterFlag) {
							if (token.length() != 0) {
								localTimeParameterId = Long.decode(token);
							}
							localTimeParameterFlag = false;
						}

						if (readingTypeFlag) {
							if (token.length() != 0) {
								readingTypeId = Long.decode(token);
							}
							readingTypeFlag = false;
						}

						if (electricPowerUsageFlag) {
							if (token.length() != 0) {
								electricPowerUsageId = Long.decode(token);
							}
							electricPowerUsageFlag = false;
						}

						if (electricPowerQualityFlag) {
							if (token.length() != 0) {
								electricPowerQualityId = Long.decode(token);
							}
							electricPowerQualityFlag = false;
						}

						if (token.equals("LocalTimeParameters")) {
							localTimeParameterFlag = true;
						}

						if (token.equals("ReadingType")) {
							readingTypeFlag = true;
						}

						if (token.equals("ElectricPowerUsageSummary")) {
							electricPowerUsageFlag = true;
						}

						if (token.equals("ElectricPowerQualitySummary")) {
							electricPowerQualityFlag = true;
						}
					}

					if (readingTypeId != null) {
						temp.add(findById(readingTypeId, ReadingType.class));
						readingTypeId = null;
					}

					if ((localTimeParameterId != null)) {
						temp.add(findById(localTimeParameterId,
								TimeConfiguration.class));
					}

					if ((electricPowerUsageId != null)) {
						temp.add(findById(electricPowerUsageId,
								ElectricPowerUsageSummary.class));
					}

					if ((electricPowerQualityId != null)) {
						temp.add(findById(electricPowerQualityId,
								ElectricPowerQualitySummary.class));
					}
				}
				
			} catch (NoResultException e) {
				// We haven't processed the related record yet, so just return the
				// empty temp
				if(logger.isInfoEnabled()){
					logger.info("**** findAllRelated(Linkable linkable) NoResultException: " + e.toString() + "&n" +
							"     Processed 'related' link before processing 'self' link&n");
				}

			} catch (Exception e) {
				// We haven't processed the related record yet, so just return the
				// empty temp
				if(logger.isInfoEnabled()) {
					logger.info("**** findAllRelated(Linkable linkable) Exception: " + e.toString() + "&n");
				}

			}

		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T findByUUID(UUID uuid, Class<T> clazz) {
		try {
			String queryFindById = (String) clazz.getDeclaredField(
					"QUERY_FIND_BY_UUID").get(String.class);

			return (T) em.createNamedQuery(queryFindById)
					.setParameter("uuid", uuid.toString().toUpperCase())
					.getSingleResult();
		} catch (IllegalAccessException | NoSuchFieldException e) {
			if(logger.isErrorEnabled()){
				logger.error("**** findByUUID(UUID uuid) Exception: " + clazz.toString() + " - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> T findById(Long id, Class<T> clazz) {
		try {
			String queryFindById = (String) clazz.getDeclaredField(
					"QUERY_FIND_BY_ID").get(String.class);

			return (T) em.createNamedQuery(queryFindById)
					.setParameter("id", id).getSingleResult();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()){
				logger.error("**** findById(Long id) Exception: " + clazz.toString() + " - " + e.toString() +
						" id: " + id + "&n");
			}
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz) {
		try {
			String queryFindById = (String) clazz.getDeclaredField(
					"QUERY_FIND_ALL_IDS").get(String.class);

			return em.createNamedQuery(queryFindById).getResultList();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()){
				logger.error("**** findAllIds Exception: " + clazz.toString() + " - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(
			Long usagePointId, Class<T> clazz) {
		try {
			String queryFindAllIdsByUsagePointId = (String) clazz
					.getDeclaredField("QUERY_FIND_ALL_IDS_BY_USAGE_POINT_ID")
					.get(String.class);

			return em.createNamedQuery(queryFindAllIdsByUsagePointId)
					.setParameter("usagePointId", usagePointId).getResultList();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findAllIdsByUsagePointId(Long usagePointId) Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
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
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(
			Class<T> clazz) {
		try {
			String findAllIdsByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ALL_IDS_BY_XPATH_0").get(String.class);
			Query query = em.createNamedQuery(findAllIdsByXPath);
			return query.getResultList();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findAllIdsByXPath Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Class<T> clazz) {
		try {
			String findAllIdsByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ALL_IDS_BY_XPATH_1").get(String.class);
			Query query = em.createNamedQuery(findAllIdsByXPath).setParameter(
					"o1Id", id1);
			return query.getResultList();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findAllIdsByXPath(Long id1) Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Class<T> clazz) {
		try {
			String findAllIdsByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ALL_IDS_BY_XPATH_2").get(String.class);
			Query query = em.createNamedQuery(findAllIdsByXPath)
					.setParameter("o1Id", id1).setParameter("o2Id", id2);
			return query.getResultList();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findAllIdsByXPath(Long id1, Long id2) Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Long id3, Class<T> clazz) {
		try {
			String findAllIdsByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ALL_IDS_BY_XPATH_3").get(String.class);
			Query query = em.createNamedQuery(findAllIdsByXPath)
					.setParameter("o1Id", id1).setParameter("o2Id", id2)
					.setParameter("o3Id", id3);
			return query.getResultList();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findAllIdsByXPath(Long id1, Long id2, Long id3) Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	// Member XPath Accessors
	//
	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1,
			Class<T> clazz) {
		try {
			String findIdByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ID_BY_XPATH").get(String.class);
			Query query = em.createNamedQuery(findIdByXPath).setParameter(
					"o1Id", id1);
			return (Long) query.getSingleResult();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findIdByXPath(Long id1) Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Class<T> clazz) {
		try {
			String findIdByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ID_BY_XPATH").get(String.class);
			Query query = em.createNamedQuery(findIdByXPath)
					.setParameter("o1Id", id1).setParameter("o2Id", id2);
			return (Long) query.getSingleResult();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findIdByXPath(Long id1, Long id2) Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Class<T> clazz) {
		try {
			String findIdByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ID_BY_XPATH").get(String.class);
			Query query = em.createNamedQuery(findIdByXPath)
					.setParameter("o1Id", id1).setParameter("o2Id", id2)
					.setParameter("o3Id", id3);
			return (Long) query.getSingleResult();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findIdByXPath(Long id1, Long id2, Long id3) Exception: " + clazz.toString() +
						" - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz) {
		try {
			String findIdByXPath = (String) clazz.getDeclaredField(
					"QUERY_FIND_ID_BY_XPATH").get(String.class);
			Query query = em.createNamedQuery(findIdByXPath)
					.setParameter("o1Id", id1).setParameter("o2Id", id2)
					.setParameter("o3Id", id3).setParameter("o4Id", id4);
			return (Long) query.getSingleResult();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findIdByXPath(Long id1, Long id2, Long id3, Long id4) Exception: " +
						clazz.toString() + " - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void update(UsagePoint updatedUsagePoint) {
		UsagePoint originalUsagePoint = findByUUID(updatedUsagePoint.getUUID());
		originalUsagePoint.setDescription(updatedUsagePoint.getDescription());
		originalUsagePoint.setRoleFlags(updatedUsagePoint.getRoleFlags());
		originalUsagePoint.setServiceCategory(updatedUsagePoint
				.getServiceCategory());
		originalUsagePoint.setStatus(updatedUsagePoint.getStatus());

		em.merge(originalUsagePoint);
	}

  	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> T findByResourceUri(String uri,
			Class<T> clazz) {
		try {
			String findByResourceURI = (String) clazz.getDeclaredField(
					"QUERY_FIND_BY_RESOURCE_URI").get(String.class);
			Query query = em.createNamedQuery(findByResourceURI).setParameter(
					"uri", uri);
			return (T) query.getSingleResult();

		} catch (NoSuchFieldException | IllegalAccessException e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** findByResourceUri(String uri) Exception: " +
						clazz.toString() + " - " + e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends IdentifiedObject> void deleteById(Long id, Class<T> clazz) {

		try {
			T temp = findById(id, clazz);

			em.merge(temp);
			temp.unlink();
			em.remove(temp);

		} catch (Exception e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** deleteById(Long id) Exception: " + clazz.toString() + " - " +
						e.toString() + "&n");
			}
			throw new RuntimeException(e);
		}

	}

	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Class<T> clazz) {
		Long id = findIdByXPath(id1, id2, clazz);
		if (id != null) {
			deleteById(id, clazz);
		}
	}

	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Long id3, Class<T> clazz) {
		Long id = findIdByXPath(id1, id2, id3, clazz);
		if (id != null) {
			deleteById(id, clazz);
		}

	}

	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Long id3, Long id4, Class<T> clazz) {
		Long id = findIdByXPath(id1, id2, id3, id4, clazz);
		if (id != null) {
			deleteById(id, clazz);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdentifiedObject> T merge(IdentifiedObject resource) {
		return (T) em.merge(resource);
	}

}
