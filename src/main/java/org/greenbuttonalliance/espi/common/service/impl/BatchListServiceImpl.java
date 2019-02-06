/*
 *     Copyright (c) 2018-2019 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
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

package org.greenbuttonalliance.espi.common.service.impl;

import org.greenbuttonalliance.espi.common.domain.BatchList;
import org.greenbuttonalliance.espi.common.repositories.BatchListRepository;
import org.greenbuttonalliance.espi.common.service.BatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class BatchListServiceImpl implements BatchListService {

	@Autowired
	private BatchListRepository repository;

	@Override
	public void persist(BatchList batchList) {
		repository.persist(batchList);
	}

	@Override
	public List<BatchList> findAll() {
		return repository.findAll();
	}

	public void setRepository(BatchListRepository repository) {
		this.repository = repository;
	}

	public BatchListRepository getRepository(BatchListRepository repository) {
		return repository;
	}
}
