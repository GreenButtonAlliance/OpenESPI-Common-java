[![CI/CD Pipeline](https://github.com/GreenButtonAlliance/OpenESPI-Common-java/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/GreenButtonAlliance/OpenESPI-Common-java/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GreenButtonAlliance_OpenESPI-Common-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=GreenButtonAlliance_OpenESPI-Common-java)
[![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?style=flat&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=flat&logo=apache-maven)](https://maven.apache.org/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-9+-purple?style=flat&logo=eclipse)](https://jakarta.ee/)
[![Hibernate](https://img.shields.io/badge/Hibernate-6.x-yellow?style=flat&logo=hibernate)](https://hibernate.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-lightgrey?style=flat&logo=apache)](https://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://img.shields.io/badge/Build-Passing-success?style=flat&logo=github-actions)](https://github.com/GreenButtonAlliance/OpenESPI-Common-java/actions)
[![Green Button](https://img.shields.io/badge/Green%20Button-ESPI%201.0-green?style=flat)](https://www.greenbuttonalliance.org/)
[![NAESB](https://img.shields.io/badge/NAESB-REQ.21-blue?style=flat)](https://www.naesb.org/)
[![MapStruct](https://img.shields.io/badge/MapStruct-1.6.0-orange?style=flat)](https://mapstruct.org/)
[![Lombok](https://img.shields.io/badge/Lombok-1.18.34-red?style=flat)](https://projectlombok.org/)

# OpenESPI-Common

This is the Common module of the OpenESPI Green Button Data Custodian and Third Party implementation. It is a modern Spring Boot 3.5 application written in Java 21 and built on top of JPA for database access.

This Common run-time and test code is shared between stand-alone Data Custodian and Third Party applications. [OpenESPI-DataCustodian](https://github.com/greenbuttonalliance/OpenESPI-DataCustodian-java) and [OpenESPI-ThirdParty](https://github.com/greenbuttonalliance/OpenESPI-ThirdParty-java).

## Recent Modernization (2025)

The project has been modernized to leverage current enterprise Java standards:

- **Spring Boot 3.5.0** - Latest Spring Boot framework with auto-configuration
- **Java 21** - Modern LTS Java version with enhanced performance
- **Jakarta EE 9+** - Migrated from legacy javax packages 
- **Hibernate 6.x** - Modern ORM with improved performance
- **DTO Architecture** - Clean separation with JAXB-based DTOs for XML marshalling
- **MapStruct Mappers** - Type-safe entity-DTO conversion
- **UUID Primary Keys** - Modern entity architecture
- **Maven 3.9+** - Updated build system

An operational sandbox with these services operating may be found at:
<a href="https://sandbox.greenbuttonalliance.org:8443">sandbox.greenbuttonalliance.org:8443</a>

## Setup

First clone the project from github:

```bash
git clone https://github.com/greenbuttonalliance/OpenESPI-Common-java.git
cd OpenESPI-Common
```

Then install the OpenESPI-Common JAR in your local repository:
```bash
# Build and install the project with tests
mvn clean install

# or for a specific profile
mvn -P <profile name> clean install

# Skip tests if needed (for development builds)
mvn -Dmaven.test.skip=true clean install
```

## IDE Setup

### Eclipse Setup

Open Eclipse and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### Spring Tool Suite Setup

Open Spring Tool Suite and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### IntelliJ Setup

Open IntelliJ and open the project (File > Open...).

## Testing

All testing of OpenESPI is performed using the [Test Harness](https://github.com/greenbuttonalliance/OpenESPI-GreenButtonCMDTest.git) project. See the [README](https://github.com/greenbuttonalliance/OpenESPI-GreenButtonCMDTest/blob/master/README.md) file for instructions.
