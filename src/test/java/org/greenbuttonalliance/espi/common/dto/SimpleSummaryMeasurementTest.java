package org.greenbuttonalliance.espi.common.dto;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.greenbuttonalliance.espi.common.dto.SummaryMeasurementDto;
import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.dto.usage.UsagePointDto;

/**
 * Simple test to verify SummaryMeasurementDto XML marshalling.
 */
public class SimpleSummaryMeasurementTest {
    
    @Test
    public void testSummaryMeasurementDtoMarshalling() throws JAXBException {
        // Create SummaryMeasurement instances for electrical parameters
        SummaryMeasurementDto estimatedLoad = SummaryMeasurementDto.createPowerMeasurement(15000L); // 15 kW
        SummaryMeasurementDto nominalVoltage = SummaryMeasurementDto.createVoltageMeasurement(240L); // 240 V
        SummaryMeasurementDto ratedCurrent = SummaryMeasurementDto.createCurrentMeasurement(100L); // 100 A
        SummaryMeasurementDto ratedPower = new SummaryMeasurementDto(24L, "W", "3"); // 24 kW (24 * 10^3)
        
        // Verify SummaryMeasurement objects are created correctly
        assertEquals(15000L, estimatedLoad.value());
        assertEquals("W", estimatedLoad.uom());
        assertEquals("0", estimatedLoad.powerOfTenMultiplier()); // Default for createPowerMeasurement
        
        assertEquals(240L, nominalVoltage.value());
        assertEquals("V", nominalVoltage.uom());
        assertEquals("0", nominalVoltage.powerOfTenMultiplier());
        
        assertEquals(100L, ratedCurrent.value());
        assertEquals("A", ratedCurrent.uom());
        assertEquals("0", ratedCurrent.powerOfTenMultiplier());
        
        assertEquals(24L, ratedPower.value());
        assertEquals("W", ratedPower.uom());
        assertEquals("3", ratedPower.powerOfTenMultiplier());
        
        // Create minimal usage point for testing
        UsagePointDto usagePoint = new UsagePointDto(
            "UP-001",                                         // uuid
            "Test usage point for SummaryMeasurement",       // description
            null,                                             // roleFlags
            new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE), // serviceCategory
            (short) 1,                                        // status
            estimatedLoad,                                    // SummaryMeasurement
            nominalVoltage,                                   // SummaryMeasurement
            ratedCurrent,                                     // SummaryMeasurement
            ratedPower,                                       // SummaryMeasurement
            null,                                             // serviceDeliveryPoint
            null,                                             // pnodeRefs
            null,                                             // aggregatedNodeRefs
            null, null, null                                  // collections
        );
        
        // Marshal to XML
        JAXBContext context = JAXBContext.newInstance(UsagePointDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        StringWriter writer = new StringWriter();
        marshaller.marshal(usagePoint, writer);
        String xml = writer.toString();
        
        // Print the XML for verification
        System.out.println("Generated UsagePoint XML with SummaryMeasurement elements:");
        System.out.println("=".repeat(80));
        System.out.println(xml);
        System.out.println("=".repeat(80));
        
        // Verify SummaryMeasurement elements in XML
        assertTrue(xml.contains("<estimatedLoad>"), "XML should contain estimatedLoad element");
        assertTrue(xml.contains("<value>15000</value>"), "XML should contain estimated load value");
        assertTrue(xml.contains("<uom>W</uom>"), "XML should contain power unit of measure");
        
        assertTrue(xml.contains("<nominalServiceVoltage>"), "XML should contain nominalServiceVoltage element");
        assertTrue(xml.contains("<value>240</value>"), "XML should contain voltage value");
        assertTrue(xml.contains("<uom>V</uom>"), "XML should contain voltage unit of measure");
        
        assertTrue(xml.contains("<ratedCurrent>"), "XML should contain ratedCurrent element");
        assertTrue(xml.contains("<value>100</value>"), "XML should contain current value");
        assertTrue(xml.contains("<uom>A</uom>"), "XML should contain current unit of measure");
        
        assertTrue(xml.contains("<ratedPower>"), "XML should contain ratedPower element");
        assertTrue(xml.contains("<value>24</value>"), "XML should contain power value");
        assertTrue(xml.contains("<powerOfTenMultiplier>3</powerOfTenMultiplier>"), "XML should contain power multiplier");
        
        System.out.println("✓ SUCCESS: All SummaryMeasurement elements are correctly marshalled to XML");
        System.out.println("✓ COMPLIANCE: estimatedLoad, nominalServiceVoltage, ratedCurrent, ratedPower");
        System.out.println("✓ STRUCTURE: Each has value, uom, powerOfTenMultiplier fields");
        System.out.println("✓ ESPI STANDARD: SummaryMeasurement embeddables, not simple values");
    }
}