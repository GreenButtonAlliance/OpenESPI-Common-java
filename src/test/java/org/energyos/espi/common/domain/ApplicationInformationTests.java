package org.energyos.espi.common.domain;

import org.energyos.espi.common.test.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.energyos.espi.common.support.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.common.support.TestUtils.assertSizeValidation;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ApplicationInformationTests {
    
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();

        Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(applicationInformation);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ApplicationInformation applicationInformation = new ApplicationInformation();

        Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(applicationInformation);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void dataCustodianThirdPartyId() {
        assertAnnotationPresent(ApplicationInformation.class, "dataCustodianThirdPartyId", NotEmpty.class);
        assertSizeValidation(ApplicationInformation.class, "dataCustodianThirdPartyId", 2, 64);
    }

    @Test
    public void thirdPartyApplicationName() {
        assertAnnotationPresent(ApplicationInformation.class, "thirdPartyApplicationName", NotEmpty.class);
        assertSizeValidation(ApplicationInformation.class, "thirdPartyApplicationName", 2, 64);
    }

    @Test
    public void getClientId() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getClientId(), equalTo(applicationInformation.getDataCustodianThirdPartyId()));
    }

    @Test
    public void getResourceIds() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getResourceIds(), is(nullValue()));
    }

    @Test
    public void isSecretRequired() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.isSecretRequired(), is(true));
    }

    @Test
    public void getClientSecret() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getClientSecret(), equalTo(applicationInformation.getDataCustodianThirdPartySecret()));
    }

    @Test
    public void isScoped() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.isScoped(), is(true));
    }

    @Test
    public void getScope() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getScope(), hasItem("FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"));
        assertThat(applicationInformation.getScope(), hasItem("FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"));
    }

    @Test
    public void getAuthorizedGrantTypes() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getAuthorizedGrantTypes(), hasItem("authorization_code"));
    }

    @Test
    public void getRegisteredRedirectUri() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getRegisteredRedirectUri(), hasItem(applicationInformation.getThirdPartyDefaultOAuthCallback()));
    }

    @Test
    public void getAuthorities() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getAuthorities(), hasItem(new SimpleGrantedAuthority("ROLE_CLIENT")));
    }

    @Test
    public void getAccessTokenValiditySeconds() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getAccessTokenValiditySeconds(), equalTo(Integer.valueOf(60*60*24*60)));
    }

    @Test
    public void getRefreshTokenValiditySeconds() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getRefreshTokenValiditySeconds(), equalTo(Integer.valueOf(60*60*24)));
    }

    @Test
    public void getAdditionalInformation() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getAdditionalInformation(), is(nullValue()));
    }
}
