-- Insert User Role
INSERT INTO role (name) VALUES ('USER');

-- Insert Admin Role
INSERT INTO role (name) VALUES ('ADMIN');


select * from role;
select * from users;
select * from user_files;

SELECT * FROM roles WHERE name ILIKE 'user';