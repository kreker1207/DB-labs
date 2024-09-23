INSERT INTO room_types (room_type, initial_price_per_day)
VALUES 
    ('Одномісний', 500.00),
    ('Двомісний', 700.00),
    ('Тримісний', 900.00),
    ('Люкс', 1500.00);

INSERT INTO rooms (room_number, room_type_id, price_per_day, phone_number, floor)
VALUES 
    (101, 1, 550.00, '380123456789', 1),
    (102, 2, 750.00, '380123456790', 1),
    (201, 1, 500.00, '380123456791', 2),
    (202, 3, 950.00, '380123456792', 2),
    (301, 4, 1600.00, '380123456793', 3);

INSERT INTO guests (passport_number, last_name, first_name, middle_name, city, check_in_date, is_inhabited, room_id)
VALUES 
    ('AB123456', 'Іванов', 'Олександр', 'Іванович', 'Київ', '2024-09-01', true, 1),
    ('CD789012', 'Петрова', 'Марія', 'Петрівна', 'Львів', '2024-09-03', true, 2),
    ('EF345678', 'Коваленко', 'Олена', 'Олегівна', 'Одеса', '2024-09-05', false, 3);

INSERT INTO staff (last_name, first_name, middle_name)
VALUES 
    ('Сидоров', 'Андрій', 'Васильович'),
    ('Козлов', 'Микола', 'Олександрович'),
    ('Бойко', 'Ірина', 'Володимирівна');

INSERT INTO days_of_week (day_id, day_name, short_name)
VALUES 
    (1, 'Понеділок', 'Пн'),
    (2, 'Вівторок', 'Вт'),
    (3, 'Середа', 'Ср'),
    (4, 'Четвер', 'Чт'),
    (5, 'П`ятниця', 'Пт'),
    (6, 'Субота', 'Сб'),
    (7, 'Неділя', 'Нд');

INSERT INTO cleaning_schedule (staff_id, day_id, floor)
VALUES 
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (1, 4, 1),
    (2, 5, 2),
    (3, 6, 3);
