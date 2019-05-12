#!/usr/bin/env bash
# This script will initialize the databases
# it will first delete datacustodian and thirdparty databases
# then it creates them
# then establishes all the tables
# then populates tables with initial data
#
# Copy the appropriate version of prepopulatesql_<dc or tp>_<host><port>.sql to prepopulatesql.sql for your installation

# drop/create databases 
mysql --user=root --password=password < cleandatabases_dc.sql
mysql --user=root --password=password < cleandatabases_tp.sql

# create tables 
mysql --user=root --password=password < thirdpartymysql.sql
mysql --user=root --password=password < datacustodianmysql.sql
mysql --user=root --password=password < tokenstore.sql

# prepopulate tables
mysql --user=root --password=password < prepopulatesql_users_dc.sql
mysql --user=root --password=password < prepopulatesql_users_tp.sql
mysql --user=root --password=password < prepopulatesql_dc.sql
mysql --user=root --password=password < prepopulatesql_tp.sql
