package org.energyos.espi.common.utils;


import com.google.common.collect.Lists;
import org.energyos.espi.common.BaseTest;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ResourceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.energyos.espi.common.test.EspiFactory.newSimpleUsagePoint;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class EntryTypeIteratorTest extends BaseTest {

    @Mock
    private EntryBuilder entryBuilder;

    @Mock
    private ResourceService resourceService;

    private EntryTypeIterator iterator;

    @Before
    public void before() throws Exception {
        iterator = new EntryTypeIterator(resourceService, Lists.newArrayList(1L), entryBuilder);
    }

    @Test
    public void hasNext_givenElements() {
        assertThat(iterator.hasNext(), is(true));
    }

    @Test
    public void hasNext_givenNoElements() {
        iterator = new EntryTypeIterator(resourceService, new ArrayList<Long>(), entryBuilder);
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void next_movesTheIterator() {
        UsagePoint usagePoint = new UsagePoint();
        when(resourceService.findById(1L, UsagePoint.class)).thenReturn(usagePoint);

        iterator.next();

        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void next_givenUsagePointWithNoChildren() {
        UsagePoint usagePoint = newSimpleUsagePoint();
        EntryType entry = new EntryType();
        when(resourceService.findById(1L, UsagePoint.class)).thenReturn(usagePoint);
        when(entryBuilder.build(usagePoint)).thenReturn(entry);

        assertThat(iterator.next(), is(equalTo(entry)));
        assertThat(iterator.hasNext(), is(false));
    }
//
//    @Test
//    public void next_givenUsagePointWithTimeConfiguration() throws Exception {
//        TimeConfiguration localTimeParameters = newLocalTimeParameters();
//        UsagePoint usagePoint = newSimpleUsagePoint();
//        usagePoint.setLocalTimeParameters(localTimeParameters);
//
//        when(resourceService.findById(1L, UsagePoint.class)).thenReturn(usagePoint);
//        when(resourceService.findAllIds(TimeConfiguration.class)).thenReturn(Lists.newArrayList(localTimeParameters.getId()));
//        when(resourceService.findById(localTimeParameters.getId(), localTimeParameters.getClass())).thenReturn(localTimeParameters);
//
//        EntryType usagePointEntry = new EntryType();
//        EntryType timeConfigEntry = new EntryType();
//        when(entryBuilder.build(usagePoint)).thenReturn(usagePointEntry);
//        when(entryBuilder.build(localTimeParameters)).thenReturn(timeConfigEntry);
//
//        assertThat(iterator.next(), is(equalTo(usagePointEntry)));
//        assertThat(iterator.next(), is(equalTo(timeConfigEntry)));
//    }
}
