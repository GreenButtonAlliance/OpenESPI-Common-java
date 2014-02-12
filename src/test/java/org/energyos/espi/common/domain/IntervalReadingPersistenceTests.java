/*
 * Copyright 2013 EnergyOS.org
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

package org.energyos.espi.common.domain;

import static org.energyos.espi.common.support.TestUtils.assertAnnotationPresent;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.energyos.espi.common.atom.XMLTest;
import org.hibernate.annotations.LazyCollection;
import org.junit.Test;

public class IntervalReadingPersistenceTests extends XMLTest {

    @Test
    public void persistence() {
        assertAnnotationPresent(IntervalReading.class, Entity.class);
        assertAnnotationPresent(IntervalReading.class, Table.class);
    }

    @Test
    public void readingQualities() {
        assertAnnotationPresent(IntervalReading.class, "readingQualities", OneToMany.class);
        assertAnnotationPresent(IntervalReading.class, "readingQualities", LazyCollection.class);
    }

    @Test
    public void timePeriod() {
        assertAnnotationPresent(IntervalReading.class, "timePeriod", Embedded.class);
    }

    @Test
    public void intervalBlock() {
        assertAnnotationPresent(IntervalReading.class, "intervalBlock", ManyToOne.class);
        assertAnnotationPresent(IntervalReading.class, "intervalBlock", JoinColumn.class);
    }
}