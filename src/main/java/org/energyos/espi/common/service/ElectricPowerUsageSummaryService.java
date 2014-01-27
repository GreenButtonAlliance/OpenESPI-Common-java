package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ElectricPowerUsageSummaryRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;

public interface ElectricPowerUsageSummaryService {
	// TODO: likely deprecated
	public ElectricPowerUsageSummary findByUUID(UUID uuid);

	public List<ElectricPowerUsageSummary> findAllByUsagePoint(
			UsagePoint usagePoint);

	public String feedFor(
			List<ElectricPowerUsageSummary> electricPowerUsageSummarys);

	public String entryFor(ElectricPowerUsageSummary electricPowerUsageSummary);

	public void associateByUUID(UsagePoint usagePoint, UUID uuid);

	// persistence management services
	public void setRepository(ElectricPowerUsageSummaryRepository repository);

	public void persist(ElectricPowerUsageSummary electricPowerUsageSummary);

	// accessor services
	public ElectricPowerUsageSummary findById(Long electricPowerUsageSummaryId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long electricPowerUsageSummaryId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId);

	public void add(ElectricPowerUsageSummary electricPowerUsageSummary);

	public void delete(ElectricPowerUsageSummary electricPowerUsageSummary);

	// import-exportResource services
	public ElectricPowerUsageSummary importResource(InputStream stream);

}
