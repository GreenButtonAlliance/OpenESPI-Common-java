package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ReadingTypeRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.UUID;

public interface ReadingTypeService {
	// TODO: likely deprecated

	ReadingType findByUUID(UUID uuid);

	String feedFor(ReadingType readingType);

	String entryFor(ReadingType readingType);

	void associateByUUID(MeterReading meterReading, UUID uuid);

	void deleteById(long readingTypeId);

	// persistence management services
	public void setReadingTypeRepository(ReadingTypeRepository repository);

	public void persist(ReadingType readingType);

	// accessor services
	public ReadingType findById(Long readingTypeId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long readingTypeId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId);

	public void add(ReadingType readingType);

	public void delete(ReadingType readingType);

	// import-exportResource services
	public ReadingType importResource(InputStream stream);

}
