# This script will initialize the databases
# it will first delete datacustodian and third party databases
# then it creates them
# then establishes all the tables
# then populates tables with initial data
mysql -u root -p < setupdatabases.sql
