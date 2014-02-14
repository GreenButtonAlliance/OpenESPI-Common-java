package org.energyos.espi.common;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;

public class SeedDataGenerator {
    @Autowired
    private RetailCustomerService retailCustomerService;
    @Autowired
    private UsagePointService usagePointService;
    @Autowired
    private ImportService importService;

    public void init() throws Exception {
        RetailCustomer retailCustomer = retailCustomerService.findById(1L);
        usagePointService.associateByUUID(retailCustomer, UUID.fromString("7BC41774-7190-4864-841C-861AC76D46C2"));
        importService.importData(newUsagePointInputStream(UUID.fromString("7BC41774-7190-4864-841C-861AC76D46C2")), retailCustomer.getId());
        usagePointService.associateByUUID(retailCustomer, UUID.fromString("7BC41774-7190-4864-841C-861AC76D46C3"));
        importService.importData(newUsagePointInputStream(UUID.fromString("7BC41774-7190-4864-841C-861AC76D46C3")), retailCustomer.getId());
    }

    public static InputStream newUsagePointInputStream(UUID uuid) throws IOException {
        return new ByteArrayInputStream(newUsagePointXML(uuid, "Front Electric Meter").getBytes());
    }

    public static String newUsagePointXML(UUID uuid, String name) throws IOException {
        InputStream in = SeedDataGenerator.class.getClassLoader().getResourceAsStream("fixtures/test_usage_data.xml");

        @SuppressWarnings("resource")
		String xml = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
        xml = xml.replaceFirst("48C2A019-5598-4E16-B0F9-49E4FF27F5FB", uuid.toString());
        xml = xml.replaceFirst("Front Electric Meter", name);

        return xml;
    }
}



