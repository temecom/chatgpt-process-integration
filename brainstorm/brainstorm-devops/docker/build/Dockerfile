FROM postgres:latest
COPY sql/V1.0.0__create_tables.sql /docker-entrypoint-initdb.d/
COPY sql/V1.0.1__insert_sample_data.sql /docker-entrypoint-initdb.d/

# Generate and include sample data (add your specific commands here)
