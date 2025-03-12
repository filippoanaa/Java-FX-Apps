-- Inserarea unor locații în tabelul location
INSERT INTO location (location_name) VALUES ('Bucharest');
INSERT INTO location (location_name) VALUES ('Cluj-Napoca');
INSERT INTO location (location_name) VALUES ('Timisoara');

INSERT INTO hotel (location_id, name, no_rooms, price_per_night, type) VALUES (1, 'Grand Hotel Bucharest', 150, 200.0, 'Luxury');
INSERT INTO hotel (location_id, name, no_rooms, price_per_night, type) VALUES (2, 'Cluj Hotel', 100, 150.0, 'Business');
INSERT INTO hotel (location_id, name, no_rooms, price_per_night, type) VALUES (3, 'Timisoara Inn', 80, 120.0, 'Boutique');

INSERT INTO special_offer (hotel_id, start_date, end_date, percent) VALUES (7, '2025-02-01', '2025-02-10', 20);
INSERT INTO special_offer (hotel_id, start_date, end_date, percent) VALUES (8, '2025-03-01', '2025-03-15', 15);
INSERT INTO special_offer (hotel_id, start_date, end_date, percent) VALUES (9, '2025-04-01', '2025-04-05', 25);


update  hotel set type = 'family' where id = 7
update  hotel set type = 'teenagers' where id = 8
update  hotel set type = 'oldPeople' where id = 9


-- Inserarea unor clienți în tabelul client
INSERT INTO client (name, fidelity_grade, age, hobby) VALUES ('John Doe', 10, 30, 'reading');
INSERT INTO client (name, fidelity_grade, age, hobby) VALUES ('Alice Smith', 8, 25, 'traveling');
INSERT INTO client (name, fidelity_grade, age, hobby) VALUES ('Bob White', 5, 22, 'gaming');
INSERT INTO client (name, fidelity_grade, age, hobby) VALUES ('Emily Johnson', 7, 28, 'cooking');
INSERT INTO client (name, fidelity_grade, age, hobby) VALUES ('Michael Brown', 9, 35, 'swimming');
select * from client
-- Adăugarea constrângerii CHECK pentru coloana type
ALTER TABLE hotel
ADD CONSTRAINT check_hotel_type
CHECK (type IN ('family', 'teenagers', 'oldPeople'));

-- Adăugarea constrângerii CHECK pentru coloana type
ALTER TABLE client
ADD CONSTRAINT check_hobby_type
CHECK (hobby IN ('reading', 'traveling', 'gaming', 'cooking', 'swimming'));

select * from special_offer

UPDATE special_offer
SET start_date = '2026-02-01', end_date = '2026-02-10', percent = 10
WHERE id = 1;

UPDATE special_offer
SET start_date = '2026-03-01', end_date = '2026-03-15', percent = 7
WHERE id = 2;

UPDATE special_offer
SET start_date = '2026-04-01', end_date = '2026-04-05', percent = 2
WHERE id = 3;

INSERT INTO location (location_name) VALUES ('Brasov');
INSERT INTO location (location_name) VALUES ('Constanta');

INSERT INTO hotel (location_id, name, no_rooms, price_per_night, type) VALUES (4, 'Brasov Mountain Resort', 120, 180.0, 'teenagers');
INSERT INTO hotel (location_id, name, no_rooms, price_per_night, type) VALUES (5, 'Constanta Beach Hotel', 200, 220.0, 'family');
select * from hotel
INSERT INTO special_offer (hotel_id, start_date, end_date, percent) VALUES (10, '2025-06-01', '2025-06-10', 15);
INSERT INTO special_offer (hotel_id, start_date, end_date, percent) VALUES (11, '2025-07-01', '2025-07-15', 20);

INSERT INTO special_offer (hotel_id, start_date, end_date, percent) VALUES (10, '2025-01-05', '2025-01-15', 20);
INSERT INTO special_offer (hotel_id, start_date, end_date, percent) VALUES (10, '2025-06-01', '2025-06-10', 15);

UPDATE special_offer
SET  percent = 2
WHERE id = 6;

select * from reservation
