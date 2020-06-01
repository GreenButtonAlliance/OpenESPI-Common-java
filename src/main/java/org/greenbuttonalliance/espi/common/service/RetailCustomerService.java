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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface RetailCustomerService extends UserDetailsService {

	List<RetailCustomer> findAll();

	RetailCustomer findByHashedId(Long retailCustomerId);

	void persist(RetailCustomer customer);

	RetailCustomer findById(Long retailCustomerId);

	RetailCustomer findById(String retailCustomerId);

	void add(RetailCustomer retailCustomer);

	void delete(RetailCustomer retailCustomer);

	RetailCustomer importResource(InputStream stream);

	Subscription associateByUUID(Long retailCustomerId, UUID uuId);

}
