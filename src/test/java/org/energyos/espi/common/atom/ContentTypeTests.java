package org.energyos.espi.common.atom;

import com.google.common.collect.Lists;
import org.energyos.espi.common.domain.*;
import org.energyos.espi.common.models.atom.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.energyos.espi.common.test.EspiFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContentTypeTests {

    private ContentType content;

    @Before
    public void setUp() throws Exception {
        content = new ContentType();
    }

    @Test
    public void getResource_returnsUsagePoint() throws Exception {
        content.setUsagePoint(new UsagePoint());

        assertThat(content.getResource(), instanceOf(UsagePoint.class));
    }

    @Test
    public void getResource_returnsMeterReading() throws Exception {
        content.setMeterReading(new MeterReading());

        assertThat(content.getResource(), instanceOf(MeterReading.class));
    }

    @Test
    public void getResource_returnsTimeConfiguration() {
        content.setLocalTimeParameters(new TimeConfiguration());

        assertThat(content.getResource(), instanceOf((TimeConfiguration.class)));
    }

    @Test
    public void getResource_returnsElectricPowerUsageSummary() {
        content.setElectricPowerUsageSummary(new ElectricPowerUsageSummary());

        assertThat(content.getResource(), instanceOf((ElectricPowerUsageSummary.class)));
    }

    @Test
    public void getResource_returnsElectricPowerQualitySummary() {
        content.setElectricPowerQualitySummary(new ElectricPowerQualitySummary());

        assertThat(content.getResource(), instanceOf((ElectricPowerQualitySummary.class)));
    }

    @Test
    public void getResource_returnsReadingType() {
        content.setReadingType(new ReadingType());

        assertThat(content.getResource(), instanceOf((ReadingType.class)));
    }

    @Test
    public void setResource_setUsagePoint() throws Exception {
        UsagePoint usagePoint = newSimpleUsagePoint();
        content.setResource(usagePoint);

        assertThat(content.getUsagePoint(), is(equalTo(usagePoint)));
    }

    @Test
    public void setResource_setMeterReading() throws Exception {
        MeterReading resource = newMeterReading();
        content.setResource(resource);

        assertThat(content.getMeterReading(), is(equalTo(resource)));
    }

    @Test
    public void setResource_setLocalTimeParameters() throws Exception {
        TimeConfiguration resource = newLocalTimeParameters();
        content.setResource(resource);

        assertThat(content.getLocalTimeParameters(), is(equalTo(resource)));
    }

    @Test
    public void setResource_setIntervalBlock() throws Exception {
        IntervalBlock resource = newIntervalBlock();
        content.setResource(resource);

        assertThat(content.getIntervalBlocks().get(0), is(equalTo(resource)));
    }

    @Test
    public void setResource_setElectricPowerUsageSummary() throws Exception {
        ElectricPowerUsageSummary resource = newElectricPowerUsageSummary();
        content.setResource(resource);

        assertThat(content.getElectricPowerUsageSummary(), is(equalTo(resource)));
    }

    @Test
    public void setResource_setElectricPowerQualitySummary() throws Exception {
        ElectricPowerQualitySummary resource = newElectricPowerQualitySummary();
        content.setResource(resource);

        assertThat(content.getElectricPowerQualitySummary(), is(equalTo(resource)));
    }

    @Test
    public void setResource_setReadingType() throws Exception {
        ReadingType resource = newReadingType();
        content.setResource(resource);

        assertThat(content.getReadingType(), is(equalTo(resource)));
    }

    @Test
    public void setResources_givenIntervalBlocks() {
        IntervalBlock intervalBlock = newIntervalBlock();
        content.setResources(Lists.<IdentifiedObject>newArrayList(intervalBlock));

        assertThat(content.getIntervalBlocks().get(0), is(equalTo(intervalBlock)));
    }

    @Test
    public void setResources_givenASingleResource() {
        UsagePoint usagePoint = newSimpleUsagePoint();
        List<IdentifiedObject> resources = Lists.<IdentifiedObject>newArrayList(usagePoint);
        content.setResources(resources);

        assertThat(content.getUsagePoint(), is(equalTo(usagePoint)));
    }

    @Test
    public void setResources_givenNull() {
        content.setResources(null);
    }

    @Test
    public void setResource_givenNull() {
        content.setResource(null);
    }
}
