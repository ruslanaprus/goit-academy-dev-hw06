-- Task 1

CREATE TABLE worker (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL CHECK (length(name) BETWEEN 2 AND 1000),
	birthday TEXT NOT NULL,
	level TEXT NOT NULL CHECK (level IN ('Trainee', 'Junior', 'Middle', 'Senior')),
	salary INTEGER NOT NULL CHECK (salary BETWEEN 100 AND 100000)
);

-- Trigger to enforce the birthday year check
CREATE TRIGGER check_birthday_year
BEFORE INSERT ON worker
FOR EACH ROW
BEGIN
    -- Ensure the birth year is between 1901 and the current year
    SELECT CASE
        WHEN strftime('%Y', NEW.birthday) < '1901' OR strftime('%Y', NEW.birthday) > strftime('%Y', 'now')
        THEN RAISE(ABORT, 'Birthday year must be between 1901 and the current year.')
    END;
END;


CREATE TABLE client (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL CHECK (length(name) BETWEEN 2 AND 1000)
);

CREATE TABLE project (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	client_id INTEGER NOT NULL,
	start_date TEXT,
	finish_date TEXT CHECK (date(finish_date) >= date(start_date)),
	FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE project_worker (
	project_id INTEGER NOT NULL,
	worker_id INTEGER NOT NULL,
	PRIMARY KEY (project_id, worker_id),
	FOREIGN KEY (project_id) REFERENCES project(id),
	FOREIGN KEY (worker_id) REFERENCES worker(id)
);
