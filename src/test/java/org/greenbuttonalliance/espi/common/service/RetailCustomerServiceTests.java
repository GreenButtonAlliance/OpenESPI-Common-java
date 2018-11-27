/*
 * Copyright (c) 2013, 2014, 2015, 2016, 2017, 2018 EnergyOS.org
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
 *
 * ********************************************************************************
 *
 *  Copyright (c) 2018 Green Button Alliance, Inc.
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
 */

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.repositories.RetailCustomerRepository;
import org.greenbuttonalliance.espi.common.service.impl.RetailCustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RetailCustomerServiceTests {

	private RetailCustomerRepository repository;
	private RetailCustomerServiceImpl service;

	@Before
	public void setup() {
		repository = mock(RetailCustomerRepository.class);
		service = new RetailCustomerServiceImpl();
		service.setRetailCustomerRepository(repository);
	}

	@Test
	public void findAll_returnsAllRetailCustomers() {
		List<RetailCustomer> allRetailCustomers = new ArrayList<RetailCustomer>();

		when(repository.findAll()).thenReturn(allRetailCustomers);

		assertEquals(allRetailCustomers, service.findAll());
	}

	@Test
	public void findById_returnsRetailCustomers() {
		RetailCustomer customer = new RetailCustomer();
		customer.setId(13L);

		when(repository.findById(customer.getId())).thenReturn(customer);

		assertEquals(customer, service.findById(customer.getId()));
	}

	@Test
	public void persist_persistsRetailCustomer() {
		RetailCustomer customer = new RetailCustomer();

		service.persist(customer);

		verify(repository).persist(customer);
	}
}
