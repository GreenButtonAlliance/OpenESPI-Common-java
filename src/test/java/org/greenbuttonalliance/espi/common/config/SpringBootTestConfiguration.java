/*
 *
 *    Copyright (c) 2018-2025 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.espi.common.config;

import org.greenbuttonalliance.espi.common.service.EspiIdGeneratorService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import jakarta.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Boot 3.5 test configuration for OpenESPI Common module.
 * Replaces legacy XML-based test context configuration.
 */
@TestConfiguration
@Profile("test")
@ComponentScan(basePackages = {
    "org.greenbuttonalliance.espi.common.service",
    "org.greenbuttonalliance.espi.common.utils",
    "org.greenbuttonalliance.espi.common.repositories.jpa"
})
public class SpringBootTestConfiguration {

    /**
     * ESPI ID generator service for deterministic UUID5 generation.
     */
    @Bean
    @Primary
    public EspiIdGeneratorService espiIdGeneratorService() {
        return new EspiIdGeneratorService();
    }

    /**
     * JAXB marshaller for domain objects.
     */
    @Bean
    public Jaxb2Marshaller domainMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(
            "org.greenbuttonalliance.espi.common.domain",
            "org.greenbuttonalliance.espi.common.models.atom"
        );
        
        Map<String, Object> properties = new HashMap<>();
        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setMarshallerProperties(properties);
        
        return marshaller;
    }

    /**
     * JAXB marshaller for XML fragments.
     */
    @Bean
    public Jaxb2Marshaller fragmentMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(
            "org.greenbuttonalliance.espi.common.models.atom",
            "org.greenbuttonalliance.espi.common.domain"
        );
        
        Map<String, Object> properties = new HashMap<>();
        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        properties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setMarshallerProperties(properties);
        
        return marshaller;
    }

    /**
     * Bean validation factory.
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * REST template for testing.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}