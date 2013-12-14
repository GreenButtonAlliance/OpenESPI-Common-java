package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.MeterReadingRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MeterReadingService {
	// TODO: likely deprecated

	MeterReading findByUUID(UUID uuid);

	List<MeterReading> findAllByUsagePoint(UsagePoint usagePoint);

	String feedFor(List<MeterReading> meterReadings);

	String entryFor(MeterReading meterReading);

	void associateByUUID(UsagePoint usagePoint, UUID uuid);

	// persistence management services
	public void setRepository(MeterReadingRepository repository);

	public void persist(MeterReading meterReading);

	// accessor services
	public MeterReading findById(Long retailCustomerId, Long usagePointId, Long meterReadingId);
	
	public EntryTypeIterator find(Long retailCustomerId, Long usagePointId);

	public EntryType find(Long retailCustomerId, Long usagePointId,
			Long meterReadingId);

	public void delete(MeterReading meterReading);

	// import-exportResource services
	public MeterReading importResource(InputStream stream);

}
