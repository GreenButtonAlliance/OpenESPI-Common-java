/*
 * Copyright 2013, 2014 EnergyOS.org
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

package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RetailCustomerService extends UserDetailsService {
	// TODO: likely deprecated
	List<RetailCustomer> findAll();

	RetailCustomer findByHashedId(Long retailCustomerId);

	// persistence management services
	public void setRepository(RetailCustomerRepository retailCustomerRepository);

	void persist(RetailCustomer customer);

	// accessor services
	//
	RetailCustomer findById(Long retailCustomerId);
	
	RetailCustomer findById(String retailCustomerId);

	public void add(RetailCustomer retailCustomer);

	public void delete(RetailCustomer retailCustomer);

	// import-exportResource services
	public RetailCustomer importResource(InputStream stream);

	Subscription associateByUUID(Long retailCustomerId, UUID uuId);

}
