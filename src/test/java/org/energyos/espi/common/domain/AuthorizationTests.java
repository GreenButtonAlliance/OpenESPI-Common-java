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

import org.energyos.espi.common.test.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.energyos.espi.common.support.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.common.support.TestUtils.assertColumnAnnotation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorizationTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();

        Authorization authorization = EspiFactory.newAuthorization(EspiFactory.newSubscription(retailCustomer));

        Set<ConstraintViolation<Authorization>> violations = validator.validate(authorization);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Authorization authorization = new Authorization();

        Set<ConstraintViolation<Authorization>> violations = validator.validate(authorization);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void extendsIdentifiableObject() {
        assertTrue(Authorization.class.getSuperclass() == IdentifiedObject.class);
    }

    @Test
    public void persistence() {
        assertAnnotationPresent(Authorization.class, Entity.class);
        assertAnnotationPresent(Authorization.class, Table.class);
    }

    @Test
    public void accessToken() {
        assertColumnAnnotation(Authorization.class, "accessToken", "access_token");
        assertAnnotationPresent(Authorization.class, "accessToken", NotEmpty.class);
    }

    @Test
    public void resource() {
        assertAnnotationPresent(Authorization.class, "resource", NotEmpty.class);
    }
}