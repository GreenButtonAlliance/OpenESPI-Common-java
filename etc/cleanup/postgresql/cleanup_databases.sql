/*
 * OpenESPI Database Cleanup - PostgreSQL
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script safely cleans up OpenESPI databases and users.
 * Use with caution - this will remove all data!
 */

-- Terminate active connections to the databases
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname IN ('openespi_usage', 'openespi_customer', 'openespi_tokenstore')
  AND pid <> pg_backend_pid();

-- Drop databases (this will remove all data!)
DROP DATABASE IF EXISTS openespi_usage;
DROP DATABASE IF EXISTS openespi_customer;
DROP DATABASE IF EXISTS openespi_tokenstore;

-- Drop users
DROP USER IF EXISTS openespi_dev;
DROP USER IF EXISTS openespi_prod;
DROP USER IF EXISTS openespi_readonly;
DROP USER IF EXISTS openespi_migrate;

-- Show remaining databases and users
\l
\du openespi_*