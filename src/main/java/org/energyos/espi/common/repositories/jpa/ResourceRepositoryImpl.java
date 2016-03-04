/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.repositories.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
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
			try {
				// we did not find one, so now try to parse the URL and find the
				// parent that way
				// this needed only b/c we are not storing the up and self hrefs
				// in
				// the underlying db but rather
				// relying upon a structured URL for resources that we have
				// exported.
				//
				Boolean usagePointFlag = false;
				Boolean meterReadingFlag = false;

				Long usagePointId = null;
				Long meterReadingId = null;

				for (String token : href.split("/")) {
					if (usagePointFlag) {
						usagePointId = Long.parseLong(token);
						usagePointFlag = false;
					}

					if (meterReadingFlag) {
						meterReadingId = Long.parseLong(token);
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
			} catch (Exception e) {
				// nothing to do, just return the empty result and
				// we'll find it later.
				System.out.printf("**** findAllParentsByRelatedHref(String href) Exception: %s\n",
						e.toString());

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
		// now check for specific related that do not have their href's stored
		// in the DB
		// or imported objects that have the external href's stored
		if (temp.isEmpty()) {
			try {
				Boolean localTimeParameterFlag = false;
				Boolean readingTypeFlag = false;
				Boolean electricPowerUsageFlag = false;
				Boolean electricPowerQualityFlag = false;

				Long localTimeParameterId = null;
				Long readingTypeId = null;
				Long electricPowerUsageId = null;
				Long electricPowerQualityId = null;

				for (String href : linkable.getRelatedLinkHrefs()) {

					for (String token : href.split("/")) {
						if (localTimeParameterFlag) {
							localTimeParameterId = Long.decode(token);
							localTimeParameterFlag = false;
						}

						if (readingTypeFlag) {
							readingTypeId = Long.decode(token);
							readingTypeFlag = false;
						}

						if (electricPowerUsageFlag) {
							electricPowerUsageId = Long.decode(token);
							electricPowerUsageFlag = false;
						}

						if (electricPowerQualityFlag) {
							electricPowerQualityId = Long.decode(token);
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
			} catch (Exception e) {
				// nothing to do -- we'll find it later, just return the
				// empty temp
				System.out.printf("**** findAllRelated(Linkable linkable) Exception: %s\n",
						e.toString());

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
			System.out.printf("**** findByUUID(UUID uuid) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** FindbyId(Long id) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** FindAllIds Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf(
					"**** FindAllIdsByUsagePointId(Long usagePointId) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findAllIdsByXPath Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findAllIdsByXPath(Long id1) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findAllIdsByXPath(Long id1, Long id2) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findAllIdsByXPath(Long id1, Long id2, Long id3) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findIdByXPath(Long id1) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findIdByXPath(Long id1, Long id2) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findIdByXPath(Long id1, Long id2, Long id3) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** findIdByXPath(Long id1, Long id2, Long id3, Long id4) Exception: %s - %s\n",
					clazz.toString(), e.toString());
			throw new RuntimeException(e);
		}
	}

	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
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
			System.out.printf("**** findByResourceUri(String uri) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
			System.out.printf("**** deleteById(Long id) Exception: %s - %s\n",
					clazz.toString(), e.toString());
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
