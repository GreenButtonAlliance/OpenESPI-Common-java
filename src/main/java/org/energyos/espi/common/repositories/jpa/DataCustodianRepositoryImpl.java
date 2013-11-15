package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.DataCustodian;
import org.energyos.espi.common.repositories.DataCustodianRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DataCustodianRepositoryImpl implements DataCustodianRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public DataCustodian findById(Long dataCustodianId) {
        return (DataCustodian)em.createNamedQuery(DataCustodian.QUERY_FIND_BY_ID)
                .setParameter("id", dataCustodianId).getSingleResult();
    }

    @Override
    public List<DataCustodian> findAll() {
        return em.createNamedQuery(DataCustodian.QUERY_FIND_ALL).getResultList();
    }

    @Override
    public void persist(DataCustodian dataCustodian) {
       em.persist(dataCustodian);
    }

    @Override
    public DataCustodian findByClientId(String clientId) {
        return (DataCustodian)em.createNamedQuery(DataCustodian.QUERY_FIND_BY_CLIENT_ID)
                .setParameter("clientId", clientId).getSingleResult();
    }
}
