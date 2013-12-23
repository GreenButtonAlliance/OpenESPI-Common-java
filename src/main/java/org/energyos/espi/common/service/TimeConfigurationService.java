package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.TimeConfigurationRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TimeConfigurationService {
	// TODO: likely deprecated
	TimeConfiguration findByUUID(UUID uuid);

	List<TimeConfiguration> findAllByRetailCustomer(RetailCustomer retailCustomer);

	String feedFor(List<TimeConfiguration> timeConfiguration);

	String entryFor(TimeConfiguration timeConfiguration);

	void associateByUUID(UsagePoint usagePoint, UUID uuid);

	TimeConfiguration importTimeConfiguration(InputStream stream);

	void deleteById(long timeConfigurationId);

	UsagePoint getUsagePoint();

	// persistence management services
	public void setRepository(TimeConfigurationRepository timeConfigurationRepository);

	public void persist(TimeConfiguration timeConfiguration);

	// accessor services
	public TimeConfiguration findById(Long timeConfigurationId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId, Long timeConfigurationId, ExportFilter params);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId, ExportFilter params);

	public void add(TimeConfiguration timeConfiguration);

	public void delete(TimeConfiguration timeConfiguration);

	// importResource services
	public TimeConfiguration importResource(InputStream stream);

}
