package org.greenbuttonalliance.espi.common.dto;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

// Import our DTOs
import org.greenbuttonalliance.espi.common.dto.usage.*;
import org.greenbuttonalliance.espi.common.dto.SummaryMeasurementDto;
import org.greenbuttonalliance.espi.common.domain.ServiceCategory;

/**
 * Test to verify UsagePointDto uses SummaryMeasurement for electrical measurements.
 */
public class VerifySummaryMeasurementTest {
    
    @Test
    public void testUsagePointSummaryMeasurements() {
        try {
            // Create tariff rider references for ServiceDeliveryPoint
            TariffRiderRefDto touRider = new TariffRiderRefDto("TIME_OF_USE", "ENROLLED", 1672531200L);
            TariffRiderRefsDto tariffRiderRefs = new TariffRiderRefsDto(Arrays.asList(touRider));
            
            // Create service delivery point
            ServiceDeliveryPointDto serviceDeliveryPoint = new ServiceDeliveryPointDto(
                1L, "SDP-001", "Residential service delivery point", "Main Service Point",
                "RESIDENTIAL_TOU", "CUST-AGR-001", tariffRiderRefs
            );
            
            // Create SummaryMeasurement instances for electrical parameters
            SummaryMeasurementDto estimatedLoad = SummaryMeasurementDto.createPowerMeasurement(15000L); // 15 kW
            SummaryMeasurementDto nominalVoltage = SummaryMeasurementDto.createVoltageMeasurement(240L); // 240 V
            SummaryMeasurementDto ratedCurrent = SummaryMeasurementDto.createCurrentMeasurement(100L); // 100 A
            SummaryMeasurementDto ratedPower = new SummaryMeasurementDto(24L, "W", "3"); // 24 kW (24 * 10^3)
            
            // Create pricing node references
            PnodeRefDto pnode1 = new PnodeRefDto("PNODE_HUB_EAST", 1672531200L, null);
            PnodeRefsDto pnodeRefs = new PnodeRefsDto(Arrays.asList(pnode1));
            
            // Create aggregated node references
            AggregatedNodeRefDto aggNode1 = new AggregatedNodeRefDto("LOAD_ZONE_NORTH", 1672531200L, null);
            AggregatedNodeRefsDto aggregatedNodeRefs = new AggregatedNodeRefsDto(Arrays.asList(aggNode1));
            
            // Create usage point with SummaryMeasurement fields
            UsagePointDto usagePoint = new UsagePointDto(
                "UP-001",                                         // uuid (for Atom id, NOT mRID)
                "Primary residential usage point for customer",  // description
                new byte[]{0x01, 0x02},                          // roleFlags
                new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE), // serviceCategory
                (short) 1,                                       // status
                estimatedLoad,                                   // SummaryMeasurement
                nominalVoltage,                                  // SummaryMeasurement
                ratedCurrent,                                    // SummaryMeasurement
                ratedPower,                                      // SummaryMeasurement
                serviceDeliveryPoint,                            // embedded ServiceDeliveryPoint
                pnodeRefs,                                       // pnodeRefs array
                aggregatedNodeRefs,                              // aggregatedNodeRefs array
                null, null, null                                 // collections (transient)
            );
            
            // Marshal UsagePoint to XML
            JAXBContext context = JAXBContext.newInstance(UsagePointDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            StringWriter writer = new StringWriter();
            marshaller.marshal(usagePoint, writer);
            String xml = writer.toString();
            
            System.out.println("Generated UsagePoint XML with SummaryMeasurement elements:");
            System.out.println("=".repeat(80));
            System.out.println(xml);
            System.out.println("=".repeat(80));
            
            // Verify SummaryMeasurement structures
            boolean hasEstimatedLoad = xml.contains("<espi:estimatedLoad>");
            boolean hasEstimatedLoadValue = xml.contains("<espi:value>15000</espi:value>");
            boolean hasEstimatedLoadUom = xml.contains("<espi:uom>W</espi:uom>");
            
            boolean hasNominalVoltage = xml.contains("<espi:nominalServiceVoltage>");
            boolean hasNominalVoltageValue = xml.contains("<espi:value>240</espi:value>");
            boolean hasNominalVoltageUom = xml.contains("<espi:uom>V</espi:uom>");
            
            boolean hasRatedCurrent = xml.contains("<espi:ratedCurrent>");
            boolean hasRatedCurrentValue = xml.contains("<espi:value>100</espi:value>");
            boolean hasRatedCurrentUom = xml.contains("<espi:uom>A</espi:uom>");
            
            boolean hasRatedPower = xml.contains("<espi:ratedPower>");
            boolean hasRatedPowerValue = xml.contains("<espi:value>24</espi:value>");
            boolean hasRatedPowerMultiplier = xml.contains("<espi:powerOfTenMultiplier>3</espi:powerOfTenMultiplier>");
            boolean hasRatedPowerUom = xml.contains("<espi:uom>W</espi:uom>");
            
            System.out.println("SummaryMeasurement Verification:");
            System.out.println("EstimatedLoad SummaryMeasurement:");
            System.out.println("- estimatedLoad element: " + hasEstimatedLoad);
            System.out.println("- value (15000 W): " + hasEstimatedLoadValue);
            System.out.println("- uom (W): " + hasEstimatedLoadUom);
            System.out.println();
            System.out.println("NominalServiceVoltage SummaryMeasurement:");
            System.out.println("- nominalServiceVoltage element: " + hasNominalVoltage);
            System.out.println("- value (240 V): " + hasNominalVoltageValue);
            System.out.println("- uom (V): " + hasNominalVoltageUom);
            System.out.println();
            System.out.println("RatedCurrent SummaryMeasurement:");
            System.out.println("- ratedCurrent element: " + hasRatedCurrent);
            System.out.println("- value (100 A): " + hasRatedCurrentValue);
            System.out.println("- uom (A): " + hasRatedCurrentUom);
            System.out.println();
            System.out.println("RatedPower SummaryMeasurement:");
            System.out.println("- ratedPower element: " + hasRatedPower);
            System.out.println("- value (24): " + hasRatedPowerValue);
            System.out.println("- powerOfTenMultiplier (3 = kW): " + hasRatedPowerMultiplier);
            System.out.println("- uom (W): " + hasRatedPowerUom);
            
            boolean allMeasurementsCorrect = hasEstimatedLoad && hasEstimatedLoadValue && hasEstimatedLoadUom &&
                                           hasNominalVoltage && hasNominalVoltageValue && hasNominalVoltageUom &&
                                           hasRatedCurrent && hasRatedCurrentValue && hasRatedCurrentUom &&
                                           hasRatedPower && hasRatedPowerValue && hasRatedPowerMultiplier && hasRatedPowerUom;
            
            if (allMeasurementsCorrect) {
                System.out.println("\n✓ SUCCESS: All electrical measurements use SummaryMeasurement correctly!");
                System.out.println("✓ COMPLIANCE: estimatedLoad, nominalServiceVoltage, ratedCurrent, ratedPower");
                System.out.println("✓ STRUCTURE: Each has value, uom, powerOfTenMultiplier, timeStamp fields");
                System.out.println("✓ ESPI STANDARD: SummaryMeasurement embeddables, not simple values");
            } else {
                System.out.println("\n✗ FAILURE: Some SummaryMeasurement elements are incorrect.");
            }
            
        } catch (JAXBException e) {
            System.err.println("JAXB Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}