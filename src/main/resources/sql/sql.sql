-- Insert User Role
INSERT INTO role (name) VALUES ('USER');

-- Insert Admin Role
INSERT INTO role (name) VALUES ('ADMIN');


select * from role;
select * from users;

SELECT * FROM roles WHERE name ILIKE 'user';