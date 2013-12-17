package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ElectricPowerUsageSummaryRepository;
import org.energyos.espi.common.service.ElectricPowerUsageSummaryService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ElectricPowerUsageSummaryServiceImpl implements ElectricPowerUsageSummaryService {

    @Autowired
    protected ElectricPowerUsageSummaryRepository electricPowerUsageSummaryRepository;

    public void setRepository(ElectricPowerUsageSummaryRepository electricPowerUsageSummaryRepository) {
        this.electricPowerUsageSummaryRepository = electricPowerUsageSummaryRepository;
    }

    @Override
    public ElectricPowerUsageSummary findByUUID(UUID uuid) {
        return electricPowerUsageSummaryRepository.findByUUID(uuid);
    }

    public ElectricPowerUsageSummary findById(Long electricPowerUsageSummaryId) {
        return electricPowerUsageSummaryRepository.findById(electricPowerUsageSummaryId);
    }

    @Override
    public void persist(ElectricPowerUsageSummary electricPowerUsageSummary) {
    	electricPowerUsageSummaryRepository.persist(electricPowerUsageSummary);
    }

	@Override
	public String feedFor(
			List<ElectricPowerUsageSummary> electricPowerUsageSummarys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(ElectricPowerUsageSummary electricPowerUsageSummary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void delete(ElectricPowerUsageSummary electricPowerUsageSummary) {
		// TODO Auto-generated method stub	
	}

	@Override
	public List<ElectricPowerUsageSummary> findAllByUsagePoint(
			UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long retailCustomerId, Long usagePointId,
			Long electricPowerUsageSummaryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId, Long usagePointId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ElectricPowerUsageSummary electricPowerUsageSummary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ElectricPowerUsageSummary importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
