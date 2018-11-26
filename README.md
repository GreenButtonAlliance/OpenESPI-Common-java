[![CircleCI](https://circleci.com/gh/greenbuttonalliance/OpenESPI-Common-java.svg?style=svg)](https://circleci.com/gh/greenbuttonalliance/OpenESPI-Common-java)
# OpenESPI-Common
This is the Common module of the OpenESPI Green Button Data Custodian and Third Party implementation. It is a Spring application written in java and built on top of jpa for database access.

This Common run-time and test code is shared between stand-alone Data Custodian and Third Party applications. [OpenESPI-DataCustodian](https://github.com/energyos/OpenESPI-DataCustodian-java) and [OpenESPI-ThirdParty](https://github.com/energyos/OpenESPI-ThirdParty-java).

An operational sandbox with these services operating may be found at:
<a href="https://services.greenbuttondata.org">services.greenbuttondata.org</a>

## Setup

First clone the project from github:

```bash
git clone https://github.com/energyos/OpenESPI-Common-java.git
cd OpenESPI-Common
```

Then install the OpenESPI-Common JAR in your local repository:
```bash
mvn clean install

# or for a specific profile
mvn -P <profile name> -Dmaven.test.skip=true clean install
```

## IDE Setup

### Eclipse Setup

Open Eclipse and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### Spring Tool Suite Setup

Open Spring Tool Suite and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### IntelliJ Setup

Open IntelliJ and open the project (File > Open...).

## Testing

All testing of OpenESPI is performed using the [Test Harness](https://github.com/energyos/OpenESPI-GreenButtonCMDTest.git) project. See the [README](https://github.com/energyos/OpenESPI-GreenButtonCMDTest/blob/master/README.md) file for instructions.
