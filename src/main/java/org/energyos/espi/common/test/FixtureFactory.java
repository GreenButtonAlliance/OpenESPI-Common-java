package org.energyos.espi.common.test;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FixtureFactory {
    private FixtureFactory() {}

    public static InputStream newUsagePointInputStream(UUID uuid) throws IOException {
        return new ByteArrayInputStream(newUsagePointXML(uuid).getBytes());
    }

    public static String newUsagePointXML(UUID uuid) throws IOException {
        ClassPathResource sourceFile = new ClassPathResource("/fixtures/test_usage_data.xml");
        String xml = FileUtils.readFileToString(sourceFile.getFile());
        xml = xml.replaceFirst("48C2A019-5598-4E16-B0F9-49E4FF27F5FB", uuid.toString());
        return xml;
    }

    public static String loadFixture(String path) throws IOException {
        ClassPathResource sourceFile = new ClassPathResource(path);
        return FileUtils.readFileToString(sourceFile.getFile());
    }
}
