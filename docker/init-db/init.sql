-- docker/init-db/init.sql 

-- Runs once when the PostgreSQL container is first created. 

-- Flyway handles all table DDL — this file only handles 

-- database-level setup that must exist before Flyway connects. 

 

-- Ensure the database exists (idempotent) 

SELECT 'CREATE DATABASE taskerdb' 

WHERE NOT EXISTS ( 

    SELECT FROM pg_database WHERE datname = 'taskerdb' 

)\gexec 

 

-- Ensure the app user exists with correct privileges 

DO $$ 

BEGIN 

    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'tasker') THEN 

        CREATE ROLE tasker WITH LOGIN PASSWORD 'tasker'; 

    END IF; 

END 

$$; 

 

GRANT ALL PRIVILEGES ON DATABASE taskerdb TO tasker; 

 

-- Connect to the app database for schema-level grants 

\c taskerdb 

 

-- Grant future table/sequence privileges so Flyway migrations 

-- run without needing the superuser role 

ALTER DEFAULT PRIVILEGES IN SCHEMA public 

    GRANT ALL ON TABLES    TO tasker; 

ALTER DEFAULT PRIVILEGES IN SCHEMA public 

    GRANT ALL ON SEQUENCES TO tasker; 