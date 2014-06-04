SET FOREIGN_KEY_CHECKS=0;

/* prepopulate tables TP */
USE `thirdparty`;
source prepopulatesql_users_tp.sql
source prepopulatesql_applicationinformation_tp.sql

SET FOREIGN_KEY_CHECKS=1;

