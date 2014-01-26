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

package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RetailCustomerServiceImpl implements RetailCustomerService {

    @Autowired
    private RetailCustomerRepository retailCustomerRepository;
    
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private ImportService importService;
    
    public void setResourceService(ResourceService resourceService){
    	this.resourceService = resourceService;
    }

    public void setImportService(ImportService importService){
    	this.importService = importService;
    }

    public void setRepository(RetailCustomerRepository retailCustomerRepository) {
        this.retailCustomerRepository = retailCustomerRepository;
    }

    @Override
    public List<RetailCustomer> findAll() {
        return retailCustomerRepository.findAll();
    }

    @Override
    public void persist(RetailCustomer customer) {
    	retailCustomerRepository.persist(customer);
    }

    @Override
    public RetailCustomer findById(Long id) {
        return retailCustomerRepository.findById(id);
    }
    
	@Override
	public RetailCustomer findById(String retailCustomerId) {
		return retailCustomerRepository.findById(retailCustomerId);
	}
	
    @Override
    public RetailCustomer findByHashedId(Long retailCustomerId) {
        return findById(retailCustomerId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return retailCustomerRepository.findByUsername(username);
        } catch (EmptyResultDataAccessException x) {
            throw new UsernameNotFoundException("Unable to find user");
        }
    }
    
	@Override
	public void add(RetailCustomer retailCustomer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(RetailCustomer retailCustomer) {
		retailCustomerRepository.deleteById(retailCustomer.getId());
		
	}

	@Override
	public RetailCustomer importResource(InputStream stream) {
		RetailCustomer retailCustomer = null;
		try {
			importService.importData(stream);
			EntryType entry = importService.getEntries().get(0);
			// TODO - add RetailCustomer to the entrytype structure
//			RetailCustomer retailCustomer = entry.getContent().getRetailCustomer();
//			persist(retailCustomer);
		} catch (Exception e) {

		}
		return retailCustomer;
	}

	@Override
	public void associateByUUID(UUID uuid) {
		// TODO Auto-generated method stub
		
	}


}
