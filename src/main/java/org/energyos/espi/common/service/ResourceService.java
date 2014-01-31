package org.energyos.espi.common.service;

import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;

public interface ResourceService {
	void persist(IdentifiedObject resource);

	List<IdentifiedObject> findByAllParentsHref(String relatedHref,
			Linkable linkable);

	List<IdentifiedObject> findAllRelated(Linkable resource);

	<T> T findByUUID(UUID uuid, Class<T> clazz);

	<T extends IdentifiedObject> T testById(Long id, Class<T> clazz);
	
	<T extends IdentifiedObject> T findById(Long id, Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(Long id,
			Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Long id2, Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Long id2, Long id3, Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Class<T> clazz);
	
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2, Class<T> clazz);
	
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2, Long id3, Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2, Long id3, Long id4, Class<T> clazz);

}
