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

package org.greenbuttonalliance.espi.common.service.impl;

import org.greenbuttonalliance.espi.common.domain.legacy.BatchList;
import org.greenbuttonalliance.espi.common.repositories.usage.BatchListRepository;
import org.greenbuttonalliance.espi.common.service.BatchListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		jakarta.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class BatchListServiceImpl implements BatchListService {

	private final BatchListRepository repository;

	public BatchListServiceImpl(BatchListRepository repository) {
		this.repository = repository;
	}

	@Override
	public void persist(BatchList batchList) {
		repository.save(batchList);
	}

	@Override
	public List<BatchList> findAll() {
		return repository.findAll();
	}


	public BatchListRepository getRepository(BatchListRepository repository) {
		return repository;
	}
}
