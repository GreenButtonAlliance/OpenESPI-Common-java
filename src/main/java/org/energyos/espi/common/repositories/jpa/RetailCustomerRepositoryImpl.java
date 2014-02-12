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

package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (rollbackFor= {JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })


public class RetailCustomerRepositoryImpl implements RetailCustomerRepository {

    @PersistenceContext
    protected EntityManager em;

    public void setEntityManager(EntityManager em)
      {
      this.em = em;
      }

    @SuppressWarnings("unchecked")
    public List<RetailCustomer> findAll() {
        return (List<RetailCustomer>)this.em.createNamedQuery(RetailCustomer.QUERY_FIND_ALL).getResultList();
    }

    @Transactional (rollbackFor= {JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public void persist(RetailCustomer customer) {
        this.em.persist(customer);
    }

    @Override
    public RetailCustomer findById(Long id) {
        return this.em.find(RetailCustomer.class, id);
    }

    @Override
    public UserDetails findByUsername(String username) {
        return (UserDetails)this.em.createNamedQuery(RetailCustomer.QUERY_FIND_BY_USERNAME)
                .setParameter("username", username).getSingleResult();
    }

	@Override
	public RetailCustomer findById(String id) {
        return em.find(RetailCustomer.class,id);
	}

        @Transactional (rollbackFor= {JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	@Override
	public void deleteById(Long id) {
    		RetailCustomer rc = findById(id);
    	    em.remove(em.contains(rc) ? rc : em.merge(rc));
	}
}
