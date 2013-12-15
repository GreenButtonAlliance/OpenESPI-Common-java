package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ElectricPowerQualitySummaryRepository;
import org.energyos.espi.common.service.ElectricPowerQualitySummaryService;
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
public class ElectricPowerQualitySummaryServiceImpl implements ElectricPowerQualitySummaryService {

    @Autowired
    protected ElectricPowerQualitySummaryRepository electricPowerQualitySummaryRepository;

    public void setRepository(ElectricPowerQualitySummaryRepository electricPowerQualitySummaryRepository) {
        this.electricPowerQualitySummaryRepository = electricPowerQualitySummaryRepository;
    }

    @Override
    public ElectricPowerQualitySummary findByUUID(UUID uuid) {
        return electricPowerQualitySummaryRepository.findByUUID(uuid);
    }

    public ElectricPowerQualitySummary findById(Long electricPowerQualitySummaryId) {
        return electricPowerQualitySummaryRepository.findById(electricPowerQualitySummaryId);
    }

    @Override
    public void persist(ElectricPowerQualitySummary electricPowerQualitySummary) {
    	electricPowerQualitySummaryRepository.persist(electricPowerQualitySummary);
    }

	@Override
	public List<ElectricPowerQualitySummary> findAllByUsagePoint(
			UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String feedFor(
			List<ElectricPowerQualitySummary> electricPowerQualitySummarys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(
			ElectricPowerQualitySummary electricPowerQualitySummary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ElectricPowerQualitySummary electricPowerQualitySummary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntryType find(Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId, Long usagePointId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ElectricPowerQualitySummary electricPowerQualitySummary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ElectricPowerQualitySummary importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
