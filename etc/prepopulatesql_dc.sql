SET FOREIGN_KEY_CHECKS=0;

/* prepopulate tables DC */
USE `datacustodian`;
source prepopulatesql_users_dc.sql
source prepopulatesql_applicationinformation_dc.sql
source prepopulatesql_tokenstore.sql

SET FOREIGN_KEY_CHECKS=1;

