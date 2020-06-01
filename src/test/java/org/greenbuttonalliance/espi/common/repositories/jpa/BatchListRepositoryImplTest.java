/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.common.repositories.jpa;

import org.greenbuttonalliance.espi.common.domain.BatchList;
import org.greenbuttonalliance.espi.common.repositories.BatchListRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.greenbuttonalliance.espi.common.test.EspiFactory.newBatchList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@ActiveProfiles("devmysql")
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class BatchListRepositoryImplTest {

	@Autowired
	BatchListRepository repository;

	private BatchList batchList;

	@Before
	public void setup() {
		batchList = newBatchList();
		repository.persist(batchList);
	}

	@Test
	public void persist() {
		assertNotNull(batchList.getId());
	}

	@Test
	public void findAll() {
		List<BatchList> foundList = repository.findAll();

		assertThat(batchList.getId(), is(foundList.get(0).getId()));
	}
}
