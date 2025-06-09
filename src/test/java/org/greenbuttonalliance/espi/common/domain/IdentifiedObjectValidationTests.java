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

import org.junit.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class IdentifiedObjectValidationTests {
	@Test
	public void isValid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		IdentifiedObject identifiedObject = new IdentifiedObject();
		identifiedObject.setUUID(UUID.randomUUID());

		Set<ConstraintViolation<IdentifiedObject>> violations = validator
				.validate(identifiedObject);

		assertTrue(violations.isEmpty());
	}

	@Test
	public void isInvalid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		IdentifiedObject IdentifiedObject = new IdentifiedObject();

		Set<ConstraintViolation<IdentifiedObject>> violations = validator
				.validate(IdentifiedObject);

		assertFalse(violations.isEmpty());
	}

//	@Test
//	public void uuid() {
//		TestUtils.assertAnnotationPresent(IdentifiedObject.class, "uuid",
//				NotNull.class);
//	}
}
