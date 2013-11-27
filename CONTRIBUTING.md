#Contributing to OpenESPI-Greenbutton-java


## Have a Question or Problem?

If you have questions about how to use the project, please direct these to the [mailing list][groups].

## Found an Issue?
If you find a bug in the source code or a mistake in the documentation, you can help us by
submitting an issue to our [GitHub Repository][github]. You may also submit a Pull Request with a fix.

### Development Guidelines
The project uses a mixture of Cucumber and JUnit tests. When implementing a new feature or bugfix, please add appropriate test coverage before sending along a Pull Request.

#### Code Structure
When implementing a new feature or bugfix, you want to ensure the you have appropriate test coverage. Here is an example of the code structure for a single feature (UsagePoint REST API).

* **Write a Cucumber feature test**
    * [Feature](https://github.com/energyos/OpenESPI-DataCustodian-java/blob/master/src/test/resources/features/API.feature)
* **Implement the Controller**
    * [Implementation](https://github.com/energyos/OpenESPI-DataCustodian-java/blob/master/src/main/java/org/energyos/espi/datacustodian/web/api/UsagePointRESTController.java)
    * [Integration Test](https://github.com/energyos/OpenESPI-DataCustodian-java/blob/master/src/test/java/org/energyos/espi/datacustodian/integration/api/UsagePointRESTTests.java)
	* [Unit Test](https://github.com/energyos/OpenESPI-DataCustodian-java/blob/master/src/test/java/org/energyos/espi/datacustodian/web/api/UsagePointRESTControllerTests.java)
* **Implement the Service Layer**
	* [Implementation](https://github.com/energyos/OpenESPI-Common-java/blob/master/src/main/java/org/energyos/espi/common/service/impl/UsagePointServiceImpl.java)
	* [Integration Test](https://github.com/energyos/OpenESPI-Common-java/blob/master/src/test/java/org/energyos/espi/common/integration/service/UsagePointServiceTests.java)
	* [Unit Test](https://github.com/energyos/OpenESPI-DataCustodian-java/blob/master/src/test/java/org/energyos/espi/datacustodian/service/impl/UsagePointServiceImplTests.java)
* **Implement the Repository Layer**
	* [Implementation](https://github.com/energyos/OpenESPI-Common-java/blob/master/src/main/java/org/energyos/espi/common/repositories/jpa/UsagePointRepositoryImpl.java)	 
	* [Integration Test](https://github.com/energyos/OpenESPI-Common-java/blob/master/src/test/java/org/energyos/espi/common/repositories/jpa/UsagePointRepositoryImplTests.java)
* **Implement the Domain Object**
	* [Implementation](https://github.com/energyos/OpenESPI-Common-java/blob/master/src/main/java/org/energyos/espi/common/domain/UsagePoint.java)
	* [Unit Test](https://github.com/energyos/OpenESPI-Common-java/blob/master/src/test/java/org/energyos/espi/common/domain/UsagePointTests.java)
	
*Note that each of the classes linked above reference many other classes that are individually unit and integration tested.*


### Submitting a Pull Request
Before you submit your pull request consider the following guidelines:

* Search [the master module](https://github.com/energyos/OpenESPI-Greenbutton-java/pulls), the [Common module](https://github.com/energyos/OpenESPI-Common-java/pulls), the [DataCustodian module](https://github.com/energyos/OpenESPI-DataCustodian-java/pulls) and the [ThirdParty module](https://github.com/energyos/OpenESPI-ThirdParty-java/pulls) for an open or closed Pull Request
  that relates to your submission. You don't want to duplicate effort.
* Create a fork of the project you'd like to contribute to.
* Make your changes in a new git branch

     ```shell
     git checkout -b my-fix-branch master
     ```

* Create your patch, including appropriate test cases.
* Commit your changes and create a descriptive commit message:

     ```shell
     git commit -a
     ```

* Build your changes locally to ensure all the tests pass

    ```shell
    mvn clean install
    ```

* Push your branch to Github:

    ```shell
    git push origin my-fix-branch
    ```

* In Github, send a pull request.
* If we suggest changes then you can modify your branch, rebase and force a new push to your GitHub repository to update the Pull Request:

    ```shell
    git rebase master -i
    git push -f
    ```

That's it! Thank you for your contribution!

[github]: https://github.com/energyos/OpenESPI-Greenbutton-java
[list]: https://groups.google.com/forum/?fromgroups#!forum/angular
[groups]: https://groups.google.com/forum/?fromgroups#!forum/angular
[greenbutton-dev]: mailto:greenbutton-dev@googlegroups.com
[commit-message-format]: https://docs.google.com/document/d/1QrDFcIiPjSLDn3EL15IJygNPiHORgU1_OOAqWjiDU5Y/edit#
[pivotal-tracker]: https://www.pivotaltracker.com/projects/884994