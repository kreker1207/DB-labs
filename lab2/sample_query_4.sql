-- -	чи є в готелі вільні місця та вільні номери і, якщо є, то скільки
SELECT COUNT(*) AS available_rooms FROM rooms WHERE is_available = true;


