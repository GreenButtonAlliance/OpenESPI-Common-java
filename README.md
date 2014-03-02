# OpenESPI-Common

Common run-time and test code shared between [OpenESPI-DataCustodian](https://github.com/energyos/OpenESPI-DataCustodian-java) and [OpenESPI-ThirdParty](https://github.com/energyos/OpenESPI-ThirdParty-java).

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

### Unit Tests

To run all Unit tests:

```bash
mvn test
```

Run a single test class:

```bash
mvn -Dtest=<TestClassName> test
mvn -Dtest=HomeControllerTests test
```

Run a single test in a single class:

```bash
mvn -Dtest=<TestClassName>#<testMethodName> test
mvn -Dtest=HomeControllerTests#index_shouldDisplayHomePage test
```
