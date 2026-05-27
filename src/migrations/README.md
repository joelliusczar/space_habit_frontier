Add .sql file to src/migrations/src/main/resources/db/migration
It should have the naming convention V<\d+>__<description>.sql
Example, `V3__add_api_grants1.sql`

to update the database from a migration, I can either 
A - `./shf_dev_ops.sh update_db`
B - `./gradlew flywayMigrate`

For active development,
run `./gradlew build`

This will activate the generator in data_model.