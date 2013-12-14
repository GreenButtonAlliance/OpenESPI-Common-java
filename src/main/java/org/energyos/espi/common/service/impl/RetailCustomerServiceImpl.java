/*
 * Copyright 2013 EnergyOS.org
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

package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class RetailCustomerServiceImpl implements RetailCustomerService {

    @Autowired
    private RetailCustomerRepository repository;

    public void setRepository(RetailCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RetailCustomer> findAll() {
        return repository.findAll();
    }

    @Override
    public void persist(RetailCustomer customer) {
        repository.persist(customer);
    }

    @Override
    public RetailCustomer findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public RetailCustomer findByHashedId(Long retailCustomerId) {
        return findById(retailCustomerId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repository.findByUsername(username);
        } catch (EmptyResultDataAccessException x) {
            throw new UsernameNotFoundException("Unable to find user");
        }
    }

	@Override
	public EntryType find(Long retailCustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ElectricPowerUsageSummary electricPowerUsageSummary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ElectricPowerUsageSummary electricPowerUsageSummary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RetailCustomer importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}
}
