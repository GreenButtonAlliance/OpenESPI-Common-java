package org.energyos.espi.common.domain;

import static org.energyos.espi.common.support.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.common.support.TestUtils.assertSizeValidation;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.energyos.espi.common.test.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    public void clientId() {
        assertAnnotationPresent(ApplicationInformation.class, "clientId", NotEmpty.class);
        assertSizeValidation(ApplicationInformation.class, "clientId", 2, 64);
    }

    @Test
    public void thirdPartyApplicationName() {
        assertAnnotationPresent(ApplicationInformation.class, "thirdPartyApplicationName", NotEmpty.class);
        assertSizeValidation(ApplicationInformation.class, "thirdPartyApplicationName", 2, 64);
    }

    @Test
    public void getClientId() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getClientId(), containsString("ClientId"));
    }

//    @Test
//    public void getResourceIds() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.getResourceIds(), is(nullValue()));
//    }

//    @Test
//    public void isSecretRequired() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.isSecretRequired(), is(true));
//    }

    @Test
    public void getClientSecret() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getClientSecret(), containsString("Secret"));
    }

//    @Test
//    public void isScoped() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.isScoped(), is(true));
//    }

    @Test
    public void getScope() {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        assertThat(applicationInformation.getScope(), hasItem("FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"));
        assertThat(applicationInformation.getScope(), hasItem("FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"));
    }

//    @Test
//    public void getAuthorizedGrantTypes() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.getAuthorizedGrantTypes(), hasItem("authorization_code"));
//    }

    
    // ToDo: Verify this test
//    @Test
//    public void getRegisteredRedirectUri() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.getRegisteredRedirectUri(), hasItem(applicationInformation.getRedirectUri()));
//    }

//    @Test
//    public void getAuthorities() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.getAuthorities(), hasItem(new SimpleGrantedAuthority("ROLE_CLIENT")));
//    }

//    @Test
//    public void getAccessTokenValiditySeconds() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.getAccessTokenValiditySeconds(), equalTo(Integer.valueOf(60*60*24*60)));
//    }

//    @Test
//    public void getRefreshTokenValiditySeconds() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.getRefreshTokenValiditySeconds(), equalTo(Integer.valueOf(60*60*24)));
//    }

//    @Test
//    public void getAdditionalInformation() {
//        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
//        assertThat(applicationInformation.getAdditionalInformation(), is(nullValue()));
//    }
}
