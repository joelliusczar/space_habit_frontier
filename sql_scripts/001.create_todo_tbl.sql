CREATE TABLE IF NOT EXISTS todos {
	id SERIAL PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	note VARCHAR(2000),
	
}