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

import org.greenbuttonalliance.espi.common.support.EspiFactory;
import org.junit.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class SubscriptionTests {

	@Test
	public void isValid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		Subscription subscription = EspiFactory.newSubscription();

		Set<ConstraintViolation<Subscription>> violations = validator
				.validate(subscription);

		assertThat(violations, is(empty()));
	}

	@Test
	public void isInvalid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		Subscription subscription = new Subscription();

		Set<ConstraintViolation<Subscription>> violations = validator
				.validate(subscription);

		assertFalse(violations.isEmpty());
	}

	@Test
	public void extendsIdentifiableObject() {
		assertSame(Subscription.class.getSuperclass(),
				IdentifiedObject.class);
	}

//	@Test
//	public void persistence() {
//		TestUtils.assertAnnotationPresent(Subscription.class, Entity.class);
//		TestUtils.assertAnnotationPresent(Subscription.class, Table.class);
//	}

//	@Test
//	public void retailCustomer() {
//		TestUtils.assertAnnotationPresent(Subscription.class, "retailCustomer",
//				ManyToOne.class);
//		TestUtils.assertAnnotationPresent(Subscription.class, "retailCustomer",
//				JoinColumn.class);
//		TestUtils.assertAnnotationPresent(Subscription.class, "retailCustomer",
//				NotNull.class);
//	}
}