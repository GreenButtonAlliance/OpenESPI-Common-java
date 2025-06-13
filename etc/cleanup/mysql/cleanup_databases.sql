/*
 * OpenESPI Database Cleanup - MySQL
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script safely cleans up OpenESPI databases and users.
 * Use with caution - this will remove all data!
 */

-- Drop databases (this will remove all data!)
DROP DATABASE IF EXISTS openespi_usage;
DROP DATABASE IF EXISTS openespi_customer;
DROP DATABASE IF EXISTS openespi_tokenstore;

-- Legacy database cleanup (for backward compatibility)
DROP DATABASE IF EXISTS datacustodian;
DROP DATABASE IF EXISTS thirdparty;
DROP DATABASE IF EXISTS tokenstore;

-- Drop users
DROP USER IF EXISTS 'openespi_dev'@'localhost';
DROP USER IF EXISTS 'openespi_prod'@'%';
DROP USER IF EXISTS 'openespi_readonly'@'%';
DROP USER IF EXISTS 'openespi_migrate'@'localhost';

-- Flush privileges
FLUSH PRIVILEGES;

-- Show remaining databases and users
SHOW DATABASES;
SELECT User, Host FROM mysql.user WHERE User LIKE 'openespi_%';