-- -	про те, хто із службовців прибирав номер вказаного клієнта у заданий день тижня,
SELECT * FROM staff s
JOIN cleaning_schedule cs ON s.staff_id = cs.staff_id
JOIN days_of_week d ON cs.day_id = d.day_id
JOIN guests g ON g.room_id = cs.floor  
WHERE g.passport_number = 'AB123456' 
AND d.day_name = 'Четвер';


