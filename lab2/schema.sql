DROP DATABASE IF EXISTS lab2;

CREATE ROLE testuser LOGIN;

-- For Linux
CREATE DATABASE lab2 ENCODING 'UTF-8' LC_COLLATE 'en_US.UTF-8' LC_CTYPE 'en_US.UTF-8' TEMPLATE template0 OWNER testuser;

\c lab2

SET ROLE testuser;

-- Таблиця для типів номерів
CREATE TABLE room_types (
    room_type_id SERIAL PRIMARY KEY,
    room_type VARCHAR(50) NOT NULL, --наприклад: одномісний, двомісний, тримісний
    initial_price_per_day NUMERIC(10, 2)
    --- cюди також можна боде додавати різні фільтри, по типу чи є кондиціонер, або плита на кухні
);

-- Таблиця для номерів у готелі
CREATE TABLE rooms (
    room_id SERIAL PRIMARY KEY, 
    room_number INT NOT NULL, 
    room_type_id INT REFERENCES room_types(room_type_id),
    price_per_day NUMERIC(10, 2) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    floor INT NOT NULL,  
    is_available BOOLEAN DEFAULT true
);

-- Таблиця для клієнтів
CREATE TABLE guests (
    guest_id SERIAL PRIMARY KEY, 
    passport_number VARCHAR(20) NOT NULL, 
    last_name VARCHAR(50) NOT NULL,  
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    city VARCHAR(100) NOT NULL,
    check_in_date DATE NOT NULL, 
    is_inhabited BOOLEAN DEFAULT false, --- в залежності від підходу компанії щодо перевикористання даних тут може бути різна реалізація 
    room_id INT REFERENCES rooms(room_id)
);
CREATE INDEX idx_passport_number ON guests(passport_number); --опціонально але я думаю що цей індекс трохи покращить продуктивність запитів за номером паспорту

-- Таблиця для службовців
CREATE TABLE staff (
    staff_id SERIAL PRIMARY KEY,  
    last_name VARCHAR(50) NOT NULL,  
    first_name VARCHAR(50) NOT NULL, 
    middle_name VARCHAR(50)
);

-- Таблиця для днів тижня
CREATE TABLE days_of_week (
    day_id INT PRIMARY KEY, 
    day_name VARCHAR(50) NOT NULL,
    short_name VARCHAR(3) NOT NULL
);

-- Таблиця для графіку прибирання
CREATE TABLE cleaning_schedule (
    schedule_id SERIAL PRIMARY KEY,  
    staff_id INT REFERENCES staff(staff_id), 
    day_id INT REFERENCES days_of_week(day_id),
    floor INT NOT NULL,
    UNIQUE (staff_id, day_id, floor)
);
---PS. я не використовувв конструкцію по типу day_of_the_week VARCHAR(10) CHECK (day_of_the_week IN ('Понеділок', 'Вівторок'...')),
--оскільки через це ми втрачаємо гнучкість, можливість перевикористання та контроль над системою наприкляд ми не зможемо індексувати це поле через "CHECK"
--або якщо в майбутньому з'явиться потреба додати нове значення для підтримки іншої мови або альтернативного формату днів тижня
--це вимагатиме зміни схеми бази даних, що може бути складно і призвести до простою системи.