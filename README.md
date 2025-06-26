# Description

A Spring Boot application for interacting with a database for Biopharma Clinical Trials.

## Features

- Update this

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- JPA/Hibernate
- PostgreSQL (compatible with Vercel Postgres)
- Flyway for database migrations
- Maven for build management
- Swagger/OpenAPI for API documentation

## Setup Development Environment

### Prerequisites

- Java 17 or higher
- Maven
- Docker (for local PostgreSQL)

### Local Development with PostgreSQL

1. Run PostgreSQL container:

```bash
docker run --name postgres-dev \
  -e POSTGRES_DB=PATIENT_TRIALS \
  -e POSTGRES_USER=dev_user \
  -e POSTGRES_PASSWORD=P@ssw0rd1234 \
  -p 5432:5432 \
  -d postgres:15
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

## Vercel Deployment

### Setup Vercel Postgres

1. Create a new project on Vercel
2. Add a PostgreSQL database through the Vercel dashboard
3. Copy the `DATABASE_URL` from your Vercel Postgres settings

### Deploy to Vercel

1. Connect your GitHub repository to Vercel
2. Set the following environment variables in Vercel:
    - `DATABASE_URL`: Your Vercel Postgres connection string
    - `SPRING_PROFILES_ACTIVE`: `prod`

3. Deploy using the Vercel dashboard or CLI:

```bash
vercel --prod
```

The application will automatically:
- Use the production profile
- Connect to Vercel Postgres
- Run Flyway migrations on startup
- Be available at your Vercel domain

### Environment Variables for Vercel

Required environment variables:
- `DATABASE_URL`: PostgreSQL connection string from Vercel
- `SPRING_PROFILES_ACTIVE`: Set to `prod`

Optional environment variables:
- `JAVA_TOOL_OPTIONS`: `-Xmx512m` (to limit memory usage)

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

### PostgreSQL-Specific Features

- Uses `BIGSERIAL` for auto-incrementing primary keys
- PostgreSQL functions instead of stored procedures
- PostgreSQL triggers for business logic
- Compatible with Vercel Postgres

## Migration from SQL Server

Key changes made for PostgreSQL compatibility:

1. **Database Driver**: Changed from `mssql-jdbc` to `postgresql`
2. **Hibernate Dialect**: Updated to `PostgreSQLDialect`
3. **Identity Strategy**: Changed from `IDENTITY` to `BIGSERIAL`
4. **Date/Time Functions**: Updated to use PostgreSQL syntax
5. **Stored Procedures**: Converted to PostgreSQL functions
6. **Triggers**: Adapted to PostgreSQL trigger syntax

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

## Docker Support (Alternative to Vercel)

If you prefer Docker deployment:

```bash
# Build the application
mvn clean package

# Run with Docker Compose (requires docker-compose.yml)
docker-compose up
```

## Troubleshooting

### Common Issues

1. **Connection Issues**: Ensure your `DATABASE_URL` is correctly formatted
2. **Migration Failures**: Check that the database user has sufficient permissions
3. **Memory Issues on Vercel**: Set `JAVA_TOOL_OPTIONS=-Xmx512m`

### Local Development

For local development, ensure PostgreSQL is running and accessible at `localhost:5432`