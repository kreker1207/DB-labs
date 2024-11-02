-- Таблиця для типів номерів
CREATE TABLE IF NOT EXISTS room_types
(
    room_type_id          SERIAL PRIMARY KEY,
    room_type             VARCHAR(50) NOT NULL,
    initial_price_per_day NUMERIC(10, 2)
);

-- Таблиця для номерів у готелі
CREATE TABLE IF NOT EXISTS rooms
(
    room_id       SERIAL PRIMARY KEY,
    room_number   INT            NOT NULL,
    room_type_id  INT REFERENCES room_types (room_type_id),
    price_per_day NUMERIC(10, 2) NOT NULL,
    phone_number  VARCHAR(15)    NOT NULL,
    floor         INT            NOT NULL,
    is_available  BOOLEAN DEFAULT true
);

-- Таблиця для клієнтів
CREATE TABLE IF NOT EXISTS guests
(
    guest_id        SERIAL PRIMARY KEY,
    passport_number VARCHAR(20)  NOT NULL,
    last_name       VARCHAR(50)  NOT NULL,
    first_name      VARCHAR(50)  NOT NULL,
    middle_name     VARCHAR(50),
    city            VARCHAR(100) NOT NULL,
    check_in_date   TIMESTAMPTZ ,
    is_inhabited    BOOLEAN DEFAULT false,
    room_id         INT REFERENCES rooms (room_id)
);
CREATE INDEX IF NOT EXISTS idx_passport_number ON guests (passport_number);

-- Таблиця для службовців
CREATE TABLE IF NOT EXISTS staff
(
    staff_id    SERIAL PRIMARY KEY,
    last_name   VARCHAR(50) NOT NULL,
    first_name  VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50)
);

-- Таблиця для днів тижня
CREATE TABLE IF NOT EXISTS days_of_week
(
    day_id     INT PRIMARY KEY,
    day_name   VARCHAR(50) NOT NULL,
    short_name VARCHAR(3)  NOT NULL
);


-- Таблиця графіку прибирання
CREATE TABLE IF NOT EXISTS cleaning_schedule
(
    schedule_id SERIAL PRIMARY KEY,
    staff_id    INT REFERENCES staff (staff_id),
    day_id      INT REFERENCES days_of_week (day_id),
    floor       INT NOT NULL,
    UNIQUE (staff_id, day_id, floor)
);

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
