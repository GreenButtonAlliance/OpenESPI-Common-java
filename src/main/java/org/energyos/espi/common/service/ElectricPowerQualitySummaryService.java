package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ElectricPowerQualitySummaryRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;

public interface ElectricPowerQualitySummaryService {
	// TODO: likely deprecated
	public ElectricPowerQualitySummary findById(
			Long electricPowerQualitySummaryId);

	public ElectricPowerQualitySummary findByUUID(UUID uuid);

	public List<ElectricPowerQualitySummary> findAllByUsagePoint(
			UsagePoint usagePoint);

	public String feedFor(
			List<ElectricPowerQualitySummary> electricPowerQualitySummarys);

	public String entryFor(
			ElectricPowerQualitySummary electricPowerQualitySummary);

	public void associateByUUID(UsagePoint usagePoint, UUID uuid);

	// persistence management services
	public void setRepository(ElectricPowerQualitySummaryRepository repository);

	public void persist(ElectricPowerQualitySummary electricPowerQualitySummary);

	// accessor services
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId);

	public void add(ElectricPowerQualitySummary electricPowerQualitySummary);

	public void delete(ElectricPowerQualitySummary electricPowerQualitySummary);

	// import-exportResource services
	public ElectricPowerQualitySummary importResource(InputStream stream);

}
