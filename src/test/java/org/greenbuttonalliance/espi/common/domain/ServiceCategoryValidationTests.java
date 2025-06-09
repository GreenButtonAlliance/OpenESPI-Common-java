/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.greenbuttonalliance.espi.common.support.EspiFactory.newServiceCategory;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ServiceCategoryValidationTests {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setupXMLUnit();
	}

	@Test
	public void isValid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		ServiceCategory serviceCategory = newServiceCategory();

		Set<ConstraintViolation<ServiceCategory>> violations = validator
				.validate(serviceCategory);

		assertThat(violations, is(empty()));
	}

	@Test
	public void isInvalid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		ServiceCategory serviceCategory = new ServiceCategory();

		Set<ConstraintViolation<ServiceCategory>> violations = validator
				.validate(serviceCategory);

		assertThat(violations, is(not(empty())));
	}
}
