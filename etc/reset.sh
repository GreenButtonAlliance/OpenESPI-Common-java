# This script will repopulate the databases
# it will first remove all entries in tables for datacustodian and third party databases
# then populates tables with initial data
#
# Copy the appropriate version of prepopulatesql_<dc or tp>_<host><port>.sql to prepopulatesql.sql for your installation

# clean tables 
mysql --user=root --password=password < cleantables_dc.sql
mysql --user=root --password=password < cleantables_tp.sql

# prepopulate tables 
mysql --user=root --password=password < prepopulatesql_dc.sql
mysql --user=root --password=password < prepopulatesql_tp.sql
