package org.energyos.espi.common.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.energyos.espi.common.models.atom.EntryType;
import org.xml.sax.SAXException;

public interface ImportService {
	void importData(InputStream stream) throws IOException, SAXException,
			ParserConfigurationException;

	List<EntryType> getEntries();
}
