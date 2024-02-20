INSERT INTO CUSTOMERS (NAME, LASTNAME, EMAIL, PHONE_NUMBER, ACCOUNT_CREATION_DATE, SUBSCRIBER, ACTIVE)
VALUES ('John', 'Doe', 'john.doe@example.com', 123456789, '2024-01-01', true, true),
       ('Alice', 'Smith', 'alice.smith@example.com', 987654321, '2024-01-02', false, true),
       ('Bob', 'Johnson', 'bob.johnson@example.com', 555666777, '2024-01-03', true, false),
       ('Emma', 'Brown', 'emma.brown@example.com', 111222333, '2024-01-04', false, true),
       ('Michael', 'Davis', 'michael.davis@example.com', 444555666, '2024-01-05', true, false),
       ('Sophia', 'Miller', 'sophia.miller@example.com', 777888999, '2024-01-06', false, true),
       ('William', 'Wilson', 'william.wilson@example.com', 111333555, '2024-01-07', true, true),
       ('Olivia', 'Moore', 'olivia.moore@example.com', 999888777, '2024-01-08', false, false),
       ('James', 'Taylor', 'james.taylor@example.com', 222444666, '2024-01-09', true, true),
       ('Emily', 'Anderson', 'emily.anderson@example.com', 666333111, '2024-01-10', false, false);

COMMIT;

INSERT INTO TOURS (NAME, COUNTRY, DESCRIPTION, START_DATE, END_DATE, START_LOCATION, END_LOCATION, PRICE_EURO, ACTIVE)
VALUES ('Adventure in the Alps', 'Switzerland', 'Hiking and skiing in the Swiss Alps', '2024-06-01', '2024-06-10',
        'Zurich', 'Geneva', 1500, true),
       ('Safari in Africa', 'Kenya', 'Wildlife safari adventure in Kenya', '2024-07-01', '2024-07-10', 'Nairobi',
        'Mombasa', 2000, true),
       ('Historical Tour in Italy', 'Italy', 'Exploring ancient ruins and historical landmarks in Italy', '2024-08-01',
        '2024-08-10', 'Rome', 'Venice', 1800, true),
       ('Tropical Paradise in Maldives', 'Maldives', 'Relaxing beach vacation in the Maldives', '2024-09-01',
        '2024-09-10', 'Male', 'Addu City', 2500, true),
       ('Culture and Heritage in Japan', 'Japan', 'Immersive cultural experience in Japan', '2024-10-01', '2024-10-10',
        'Tokyo', 'Kyoto', 2200, true),
       ('Northern Lights Expedition in Norway', 'Norway', 'Chasing the Northern Lights in Norway', '2024-11-01',
        '2024-11-10', 'Oslo', 'Tromsø', 2800, true),
       ('City Tour in France', 'France', 'Exploring the vibrant cities of France', '2024-12-01', '2024-12-10', 'Paris',
        'Nice', 1700, true),
       ('Adventure in New Zealand', 'New Zealand', 'Outdoor adventure in the breathtaking landscapes of New Zealand',
        '2025-01-01', '2025-01-10', 'Auckland', 'Queenstown', 2100, true),
       ('Beach Holiday in Greece', 'Greece', 'Relaxing beach holiday in the Greek islands', '2025-02-01', '2025-02-10',
        'Athens', 'Santorini', 1900, true),
       ('Road Trip in USA', 'USA', 'Epic road trip across the United States', '2025-03-01', '2025-03-10', 'New York',
        'Los Angeles', 3000, true);

COMMIT;

INSERT INTO ATTRACTIONS (LOCATION_ID, CITY, NAME, DESCRIPTION, CATEGORY, TITLE, PRICE_EURO, ACTIVE)
VALUES (1, 'Zurich', 'Swiss National Museum', 'Museum showcasing Swiss cultural history', 'Museum',
        'Swiss National Museum', 20, true),
       (2, 'Nairobi', 'Maasai Mara National Reserve', 'Famous game reserve known for its wildlife', 'Nature',
        'Maasai Mara Safari', 50, true),
       (3, 'Rome', 'Colosseum', 'Iconic ancient amphitheater in Rome', 'Landmark', 'Colosseum Guided Tour', 30, true),
       (4, 'Male', 'Biyadhoo Island', 'Scenic island with white sandy beaches', 'Beach', 'Biyadhoo Island Resort', 100,
        true),
       (5, 'Tokyo', 'Senso-ji Temple', 'Ancient Buddhist temple in Tokyo', 'Religious', 'Senso-ji Temple Visit', 25,
        true),
       (6, 'Oslo', 'Vigeland Sculpture Park', 'Sculpture park featuring the works of Gustav Vigeland', 'Park',
        'Vigeland Park Tour', 15, true),
       (7, 'Paris', 'Louvre Museum', 'World-renowned art museum in Paris', 'Museum', 'Louvre Museum Entrance', 35,
        true),
       (8, 'Auckland', 'Sky Tower', 'Iconic landmark offering panoramic views of Auckland', 'Landmark',
        'Sky Tower Observation Deck', 40, true),
       (9, 'Athens', 'Acropolis of Athens', 'Ancient citadel perched on a rocky outcrop above Athens', 'Historical',
        'Acropolis Guided Tour', 30, true),
       (10, 'New York', 'Statue of Liberty', 'Iconic statue symbolizing freedom and democracy', 'Landmark',
        'Statue of Liberty Cruise', 45, true);

COMMIT;

INSERT INTO RATES_EXCHANGE (EURO_TO_PLN, RATE_EXCHANGE_DATE)
VALUES (4.79, DATE_SUB(CURDATE(), INTERVAL 29 DAY)),
       (4.78, DATE_SUB(CURDATE(), INTERVAL 28 DAY)),
       (4.77, DATE_SUB(CURDATE(), INTERVAL 27 DAY)),
       (4.76, DATE_SUB(CURDATE(), INTERVAL 26 DAY)),
       (4.75, DATE_SUB(CURDATE(), INTERVAL 25 DAY)),
       (4.73, DATE_SUB(CURDATE(), INTERVAL 24 DAY)),
       (4.74, DATE_SUB(CURDATE(), INTERVAL 23 DAY)),
       (4.70, DATE_SUB(CURDATE(), INTERVAL 22 DAY)),
       (4.72, DATE_SUB(CURDATE(), INTERVAL 21 DAY)),
       (4.68, DATE_SUB(CURDATE(), INTERVAL 20 DAY)),
       (4.66, DATE_SUB(CURDATE(), INTERVAL 19 DAY)),
       (4.71, DATE_SUB(CURDATE(), INTERVAL 18 DAY)),
       (4.69, DATE_SUB(CURDATE(), INTERVAL 17 DAY)),
       (4.67, DATE_SUB(CURDATE(), INTERVAL 16 DAY)),
       (4.65, DATE_SUB(CURDATE(), INTERVAL 15 DAY)),
       (4.59, DATE_SUB(CURDATE(), INTERVAL 14 DAY)),
       (4.55, DATE_SUB(CURDATE(), INTERVAL 13 DAY)),
       (4.58, DATE_SUB(CURDATE(), INTERVAL 12 DAY)),
       (4.63, DATE_SUB(CURDATE(), INTERVAL 11 DAY)),
       (4.52, DATE_SUB(CURDATE(), INTERVAL 10 DAY)),
       (4.54, DATE_SUB(CURDATE(), INTERVAL 9 DAY)),
       (4.56, DATE_SUB(CURDATE(), INTERVAL 8 DAY)),
       (4.61, DATE_SUB(CURDATE(), INTERVAL 7 DAY)),
       (4.57, DATE_SUB(CURDATE(), INTERVAL 6 DAY)),
       (4.59, DATE_SUB(CURDATE(), INTERVAL 5 DAY)),
       (4.62, DATE_SUB(CURDATE(), INTERVAL 4 DAY)),
       (4.58, DATE_SUB(CURDATE(), INTERVAL 3 DAY)),
       (4.60, DATE_SUB(CURDATE(), INTERVAL 2 DAY)),
       (4.55, DATE_SUB(CURDATE(), INTERVAL 1 DAY)),
       (4.50, DATE_SUB(CURDATE(), INTERVAL 0 DAY));

COMMIT;