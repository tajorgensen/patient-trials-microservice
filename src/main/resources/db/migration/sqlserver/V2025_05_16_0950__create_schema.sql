IF NOT EXISTS(SELECT *
              FROM sys.schemas
              WHERE name = N'dbo')
    EXEC ('CREATE SCHEMA [dbo]');
GO