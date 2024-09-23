-- -	поселити клієнта
UPDATE guests
SET is_inhabited = true, room_id = 4
WHERE guest_id = 3;  

UPDATE rooms
SET is_available = false
WHERE room_id = 4; 


