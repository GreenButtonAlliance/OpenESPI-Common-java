This directory contains schemas and database scripts for use with OpenESPI implementations.

The XMLSchemas that govern the content of ESPI data are:

	atom.xsd -- elements of the atom publishing protocol
	
	espiderived.xsd -- the description of the contents of resources
	
	
There are bash scripts for manipulating the database if the database is mysql:

	initializedatabases.sh -- will create the tables of a datacustodian and thirdparty (drops tables if they exist)
	reset.sh -- will clean and repopulate tables. This script will work successfully even while the DataCustodian and/or ThirdParty are running
	
	both scripts use a prepopulatesql.sql database script that is not source controlled and allows the databases to be prepopulated with useful initial data
