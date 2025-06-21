/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.legacy.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.legacy.Linkable;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides a resource type independent access to ESPI Resources
 * 
 * @author jat1
 *
 */
public interface ResourceService {
	void persist(IdentifiedObject resource);

	void flush();

	void setConfigurations(Map<String, String> params);

	void setConfiguration(String key, String value);

	Map<String, String> getConfigurations();

	String getConfiguration(String key);

	List<IdentifiedObject> findByAllParentsHref(String relatedHref,
			Linkable linkable);

	List<IdentifiedObject> findAllRelated(Linkable resource);

	<T> T findByUUID(UUID uuid, Class<T> clazz);

	<T extends IdentifiedObject> T testById(Long id, Class<T> clazz);

	/**
	 * @param id
	 * @param clazz
	 * @return
	 * 
	 *         Find a Resource based upon it's locally unique id and it's class
	 */
	<T extends IdentifiedObject> T findById(Long id, Class<T> clazz);

	/**
	 * @param clazz
	 * @return List<Long>
	 * 
	 *         Returns a list the IDs of all of the locally unique ESPI
	 *         resources of type clazz
	 * 
	 */
	<T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz);

	/**
	 * @param usagePointId
	 * @param clazz
	 * @return List<Long>
	 * 
	 *         Returns a list of IDs for all of the resources of type clazz that
	 *         are associated with the given usagePointId
	 */
	<T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(
			Long usagePointId, Class<T> clazz);

	/**
	 * One of the XPath navigators. Class1/mm/Class2/nn/Class3/pp where the
	 * input parameters provide the obvious mappings to resources.
	 * 
	 * @param clazz
	 * @return
	 */
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Class<T> clazz);

	/**
	 * One of the XPath navigators. Class1/mm/Class2/nn/Class3/pp where the
	 * input parameters provide the obvious mappings to resources.
	 * 
	 * @param id1
	 *            (is either a retail customerId or a subscriptionId or ... any
	 *            resourceId
	 * @param clazz
	 * @return
	 */
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Class<T> clazz);

	/**
	 * One of the XPath navigators. Class1/mm/Class2/nn/Class3/pp where the
	 * input parameters provide the obvious mappings to resources.
	 * 
	 * @param id1
	 * @param id2
	 * @param clazz
	 * @return a List of Long's == resourceIds of the resultant search
	 */
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Class<T> clazz);

	/**
	 * One of the XPath navigators. Class1/mm/Class2/nn/Class3/pp where the
	 * input parameters provide the obvious mappings to resources.
	 * 
	 * @param id1
	 * @param id2
	 * @param id3
	 * @param clazz
	 * @return a List of Long's == resourceIds of the resultant search
	 */
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Long id3, Class<T> clazz);

	/**
	 * @param id1
	 * @param clazz
	 * @return a List of Long's == resourceIds of the resultant search
	 */
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Class<T> clazz);

	/**
	 * @param id1
	 * @param id2
	 * @param clazz
	 * @return a List of Long's == resourceIds of the resultant search
	 */
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Class<T> clazz);

	/**
	 * @param id1
	 * @param id2
	 * @param id3
	 * @param clazz
	 * @return a List of Long's == resourceIds of the resultant search
	 */
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Class<T> clazz);

	/**
	 * @param id1
	 * @param id2
	 * @param id3
	 * @param id4
	 * @param clazz
	 * @return a List of Long's == resourceIds of the resultant search
	 */
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz);

	/**
	 * @param clazz
	 * @return an {@link EntryTypeIterator} that may be used to export the
	 *         associated resource tree
	 *
	 */
	<T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(
			Class<T> clazz);

	/**
	 * @param ids
	 * @param clazz
	 * @return
	 */
	<T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(
			List<Long> ids, Class<T> clazz);

	/**
	 * @param id1
	 * @param clazz
	 * @return
	 */
	<T extends IdentifiedObject> EntryType findEntryType(long id1,
			Class<T> clazz);

	// currently used only finding the Authorization associated with a given
	// resource URI
	/**
	 * @param uri
	 * @param clazz
	 * @return
	 */
	<T extends IdentifiedObject> T findByResourceUri(String uri, Class<T> clazz);

	// a collection of interfaces that delete resources (by both XPATH and ROOT
	// form)
	//
	/**
	 * @param id
	 * @param clazz
	 */
	<T extends IdentifiedObject> void deleteById(Long id, Class<T> clazz);

	/**
	 * @param id1
	 * @param id2
	 * @param clazz
	 */
	/**
	 * @param id1
	 * @param id2
	 * @param clazz
	 */
	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2,
			Class<T> clazz);

	/**
	 * @param id1
	 * @param id2
	 * @param id3
	 * @param clazz
	 */
	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2,
			Long id3, Class<T> clazz);

	/**
	 * @param id1
	 * @param id2
	 * @param id3
	 * @param id4
	 * @param clazz
	 */
	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz);

	/**
	 * @param existingResource
	 * @return
	 */
	<T extends IdentifiedObject> T merge(IdentifiedObject existingResource);

}
