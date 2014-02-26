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

import javax.persistence.Entity;

import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.service.ExportService;
import org.energyos.espi.common.service.ImportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class TimeConfigurationTests extends XMLTest {

    @Autowired
    ImportService importService;
    
    @Autowired
    ExportService exportService;
    
    TimeConfiguration timeConfiguration;

    @Before
    public void before() {
    
        timeConfiguration = new TimeConfiguration();
    }

    @Test
    public void persistable() {
        assertAnnotationPresent(TimeConfiguration.class, Entity.class);
    }

}