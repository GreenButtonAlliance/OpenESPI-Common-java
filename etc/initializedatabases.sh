# This script will initialize the databases
# it will first delete datacustodian and third party databases
# then it creates them
# then establishes all the tables
# then populates tables with initial data
#
# Copy the appropriate version of prepopulatesql_<host><port>.sql to prepopulatesql.sql for your installation
mysql --user=root --password=password < setupdatabases.sql
