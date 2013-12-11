package org.energyos.espi.common.service;

import org.energyos.espi.common.models.atom.EntryType;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ImportService {
    void importData(InputStream stream) throws IOException, SAXException, ParserConfigurationException;

	List<EntryType> getEntries();
}
