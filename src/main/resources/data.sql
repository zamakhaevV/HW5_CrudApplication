BEGIN TRANSACTION;
    INSERT INTO person (age, company, name) VALUES (23, 'company1', 'John');
    INSERT INTO person (age, company, name) VALUES (30, 'company2', 'Steve');
    INSERT INTO person (age, company, name) VALUES (41, 'company2', 'Ann');
    INSERT INTO person (age, company, name) VALUES (25, 'company1', 'Kira');
    INSERT INTO person (age, company, name) VALUES (28, 'company2', 'Leo');
COMMIT;