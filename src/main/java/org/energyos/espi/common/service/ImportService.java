package org.energyos.espi.common.service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface ImportService {
    void importData(InputStream stream) throws IOException, SAXException, ParserConfigurationException;
}
