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

import org.greenbuttonalliance.espi.common.domain.BatchList;
import org.greenbuttonalliance.espi.common.repositories.BatchListRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class BatchListRepositoryImpl implements BatchListRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void persist(BatchList batchList) {
		em.persist(batchList);
	}

	@SuppressWarnings("unchecked")
	// as BatchList is an orphaned object. unchecked is needed.
	@Override
	public List<BatchList> findAll() {
		String selectAll = "select list from BatchList list";
		return (List<BatchList>) this.em.createQuery(selectAll).getResultList();
	}
}
