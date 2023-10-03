-- Insert User Role
INSERT INTO roles (name) VALUES ('USER');

-- Insert Admin Role
INSERT INTO roles (name) VALUES ('ADMIN');


select * from roles;
select * from users;

SELECT * FROM roles WHERE name ILIKE 'user';