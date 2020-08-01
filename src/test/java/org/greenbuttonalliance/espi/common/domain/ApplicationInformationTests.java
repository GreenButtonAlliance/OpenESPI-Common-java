/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.support.EspiFactory;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ApplicationInformationTests {

	@Test
	public void isValid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		ApplicationInformation applicationInformation = EspiFactory
				.newApplicationInformation();

		Set<ConstraintViolation<ApplicationInformation>> violations = validator
				.validate(applicationInformation);

		assertThat(violations.isEmpty(), is(true));
	}

	@Test
	public void isInvalid() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		ApplicationInformation applicationInformation = new ApplicationInformation();

		Set<ConstraintViolation<ApplicationInformation>> violations = validator
				.validate(applicationInformation);

		assertThat(violations.isEmpty(), is(false));
	}

	//TODO: Rewrite with valid JUnit 5 based test
//	@Test
//	public void clientId() {
//		assertAnnotationPresent(ApplicationInformation.class, "clientId",
//				NotEmpty.class);
//		assertSizeValidation(ApplicationInformation.class, "clientId", 2, 64);
//	}

	//TODO: Rewrite with valid JUnit 5 based test
//	@Test
//	public void thirdPartyApplicationName() {
//		assertAnnotationPresent(ApplicationInformation.class,
//				"thirdPartyApplicationName", NotEmpty.class);
//		assertSizeValidation(ApplicationInformation.class,
//				"thirdPartyApplicationName", 2, 64);
//	}

	@Test
	public void getClientId() {
		ApplicationInformation applicationInformation = EspiFactory
				.newApplicationInformation();
		assertThat(applicationInformation.getClientId(),
				containsString("ClientId"));
	}

	// TODO: Investigate if getResourceIds() ever existed
//	@Test
//	public void getResourceIds() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.getResourceIds(),
//				is(nullValue()));
//	}

	// TODO: Investigate if isSecretRequired() ever existed
//	@Test
//	public void isSecretRequired() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.isSecretRequired(),
//				is(true));
//	}

	@Test
	public void getClientSecret() {
		ApplicationInformation applicationInformation = EspiFactory
				.newApplicationInformation();
		assertThat(applicationInformation.getClientSecret(),
				containsString("Secret"));
	}

	// TODO: Investigate if isScoped() ever existed
//	@Test
//	public void isScoped() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.isScoped(),
//				is(true));
//	}

	@Test
	public void getScope() {
		ApplicationInformation applicationInformation = EspiFactory
				.newApplicationInformation();
		assertThat(
				applicationInformation.getScope(),
				hasItem("FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"));
		assertThat(
				applicationInformation.getScope(),
				hasItem("FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"));
	}

	// TODO: Investigate if getAuthorizedGrantTypes() ever existed
//	@Test
//	public void getAuthorizedGrantTypes() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.getAuthorizedGrantTypes(),
//				hasItem("authorization_code"));
//	}

	// TODO: Investigate if getRegisteredRedirectUri() ever existed
//	@Test
//	public void getRegisteredRedirectUri() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.getRegisteredRedirectUri(),
//				hasItem(applicationInformation.getRedirectUri()));
//	}

	// TODO: Investigate if getAuthorities() ever existed
//	@Test
//	public void getAuthorities() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.getAuthorities(),
//				hasItem(new SimpleGrantedAuthority("ROLE_CLIENT")));
//	}

	// TODO: Investigate if getAccessTokenValiditySeconds() ever existed
//	@Test
//	public void getAccessTokenValiditySeconds() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.getAccessTokenValiditySeconds(),
//				equalTo(60 * 60 * 24 * 60));
//	}

	// TODO: Investigate if getRefreshTokenValiditySeconds() ever existed
//	@Test
//	public void getRefreshTokenValiditySeconds() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.getRefreshTokenValiditySeconds(),
//				equalTo(60 * 60 * 24));
//	}

	// TODO: Investigate if getAdditionalInformation() ever existed
//	@Test
//	public void getAdditionalInformation() {
//		ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//		assertThat(applicationInformation.getAdditionalInformation(),
//				is(nullValue()));
//	}
}
