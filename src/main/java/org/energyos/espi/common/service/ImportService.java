/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;

import org.energyos.espi.common.models.atom.EntryType;
import org.xml.sax.SAXException;

public interface ImportService {
	void importData(InputStream stream, Long retailCustomerId) throws IOException, SAXException,
			ParserConfigurationException;

	List<EntryType> getEntries();
	
	XMLGregorianCalendar getMinUpdated();
	
	XMLGregorianCalendar getMaxUpdated();
	
}
