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

import org.greenbuttonalliance.espi.common.domain.ApplicationInformation;
import org.greenbuttonalliance.espi.common.repositories.ApplicationInformationRepository;
import org.greenbuttonalliance.espi.common.support.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@ActiveProfiles("devmysql")
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		jakarta.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class ApplicationInformationRepositoryImplTest {

	@Autowired
	private ApplicationInformationRepository repository;
	private ApplicationInformation applicationInformation;

	@Before
	public void setUp() {
		applicationInformation = EspiFactory.newApplicationInformation();
		repository.persist(applicationInformation);
	}

	@Test
	public void persist() {
		assertNotNull(applicationInformation.getId());
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void persist_modelEnforcesUniqueClientId() {
		ApplicationInformation duplicateApplicationInformation = EspiFactory
				.newApplicationInformation();
		duplicateApplicationInformation
				.setDataCustodianId(applicationInformation.getDataCustodianId());
		duplicateApplicationInformation.setClientId(applicationInformation
				.getClientId());

		repository.persist(duplicateApplicationInformation);
	}

	@Test
	public void findById() {
		assertEquals(applicationInformation.getId(),
				repository.findById(applicationInformation.getId()).getId());
	}

	@Test
	public void findByClientId() {
		assertEquals(applicationInformation.getId(),
				repository.findByClientId(applicationInformation.getClientId())
						.getId());
	}

	@Test
	public void findByDataCustodianClientId() {
		assertEquals(
				applicationInformation.getId(),
				repository.findByDataCustodianClientId(
						applicationInformation.getDataCustodianId()).getId());
	}

	@Test
	public void findAll_returnsAllThirdParties() {
		assertTrue("Repository has no data", repository.findAll().size() > 0);
	}
}
