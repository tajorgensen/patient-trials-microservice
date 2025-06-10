# Description

A Spring Boot application for interacting with a database for Biopharma Clinical Trials.

## Features

- Update this

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- JPA/Hibernate
- SQL Server
- Flyway for database migrations
- Maven for build management
- Swagger/OpenAPI for API documentation

## Setup Development Environment

### Prerequisites

- Java 17 or higher
- Maven
- Docker

### Database Setup with Docker

1. Pull the SQL Server Docker image:

```bash
docker pull mcr.microsoft.com/mssql/server:2022-latest
```

2. Run SQL Server container:

```bash
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=P@ssw0rd1234" -p 1433:1433 --name sql_server -d mcr.microsoft.com/mssql/server:2022-latest
```

3. Connect to SQL Server using SQL Server command-line tools or Azure Data Studio:

```bash
docker run -it --network=host mcr.microsoft.com/mssql-tools
sqlcmd -S localhost -U sa -P P@ssw0rd1234
```

4. Create the database and development user:

```sql
-- Create practice plan database
CREATE
    DATABASE PATIENT_TRIALS;
GO

-- Use the new database
USE PATIENT_TRIALS;
GO

-- Create dev_user login
CREATE
    LOGIN dev_user WITH PASSWORD = 'P@ssw0rd1234';
GO

-- Create user in database from login
CREATE
    USER dev_user FOR LOGIN dev_user;
GO

-- Add db_owner role to user
ALTER
    ROLE db_owner ADD MEMBER dev_user;
GO

-- Create flyway history schema
CREATE SCHEMA flyway_history_schema;
GO

-- Grant necessary permissions
GRANT ALTER
    ON SCHEMA::flyway_history_schema TO dev_user;
GRANT
    CREATE TABLE TO dev_user;
GRANT
    CONTROL
    ON SCHEMA::dbo TO dev_user;
GO

EXIT
```

### Building and Running the Application

1. Clone the repository:

```bash
git clone https://github.com/tajorgensen/patient-trials-microservice.git
cd patient-trials-microservice
```

2. Build with Maven:

```bash
mvn clean install
```

3. Run the application with dev profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

4. The application will be available at `http://localhost:8080`

5. API documentation is available at `http://localhost:8080/swagger-ui.html`

## API Overview

The application provides RESTful APIs for:

- **Patients**: Create and manage patients
- **Trials**: Create and manage trials
- **Drugs**: Create and manage drugs
- **Patient Trials**: Manage the patient to trial relationship
- **Adverse Events**: Create and manage adverse events that a patient experiences as part of a trial

## Database Structure

The database consists of the following main entities:

- Patients
- Drugs
- Trials
- Patient Trials
- Patient Trial Drugs
- Adverse Events

## Docker Troubleshooting

If you encounter issues with the SQL Server Docker container:

1. Check container status:

```bash
docker ps -a
```

2. View container logs:

```bash
docker logs sql_server
```

3. If the container stops unexpectedly, ensure your Docker has enough memory allocated (at least 2GB for SQL Server)

4. Restart the container:

```bash
docker start sql_server
```

5. If you need to remove and recreate the container:

```bash
docker rm sql_server
# Then rerun the docker run command from the setup instructions
```

## Testing

Testing has been broken into 4 tiers to align with the testing triangle (Unit, Integration, System, and End to End).

In order to test those tiers individually you can simply run the following commands

```bash
## Unit Tests
mvn test

## Integration Tests
mvn test -Dgroups="integration"

## System Tests
mvn test -Dgroups="system"

## End To End Tests
mvn test -Dgroups="endToEnd"

## Multiple together can be done with comma separation
mvn test -Dgroups="integration, system"

## Run all tests after you have started up the application locally
mvn test -DexcludedGroups=""
```