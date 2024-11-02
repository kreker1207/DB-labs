package com.lab3.hotel.repository;

import com.lab3.hotel.model.dto.request.RoomFilter;
import com.lab3.hotel.model.dto.request.RoomSaveRequestDto;
import com.lab3.hotel.model.dto.response.RoomShortResponseDto;
import com.lab3.hotel.model.dto.response.RoomTypeResponseDto;
import com.lab3.hotel.model.entity.RoomEntity;
import com.lab3.hotel.utils.QueryHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final QueryHelper queryHelper;

    public void checkIn(long roomId, boolean isCheckOut) {
        //language=PostgreSQL
        String sql = "UPDATE rooms SET is_available = :isCheckOut WHERE room_id = :roomId";
        MapSqlParameterSource params = new MapSqlParameterSource("roomId", roomId).addValue("isCheckOut", isCheckOut);
        queryHelper.update(sql, params, "check in room");
    }

    public Optional<RoomEntity> getRoomById(Long roomId) {
        //language=PostgreSQL
        String sql = "SELECT room_id AS id, room_number AS roomNumber, room_type_id AS roomTypeId, price_per_day AS pricePerDay, " +
                "phone_number AS phoneNumber, floor AS floor, is_available AS isAvailable FROM rooms WHERE room_id = :roomId";
        MapSqlParameterSource params = new MapSqlParameterSource("roomId", roomId);
        return queryHelper.queryForObject(sql, params, new BeanPropertyRowMapper<>(RoomEntity.class), "getting room by id: " + roomId);
    }

    public boolean isRoomExistsById(long roomId) {
        //language=PostgreSQL
        String sql = "SELECT COUNT(*) FROM rooms WHERE room_id = :roomId; ";
        MapSqlParameterSource params = new MapSqlParameterSource("roomId", roomId);
        Integer counter = queryHelper.queryForObjectWithoutEmptyCheck(sql, params, Integer.class, "checking if room exists");
        return counter > 0;
    }

    public Optional<Long> createRoom(RoomSaveRequestDto roomSaveRequestDto) {
        //language=SQL
        String sql = "INSERT INTO rooms (room_number, room_type_id, price_per_day, phone_number, floor, is_available) " +
                "  VALUES (:roomNumber, :roomTypeId, :pricePerDay, :phoneNumber, :floor, :isAvailable) RETURNING room_id;";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("roomNumber", roomSaveRequestDto.getNumber())
                .addValue("roomTypeId", roomSaveRequestDto.getTypeId())
                .addValue("pricePerDay", roomSaveRequestDto.getPricePerDay())
                .addValue("phoneNumber", roomSaveRequestDto.getPhoneNumber())
                .addValue("floor", roomSaveRequestDto.getFloor())
                .addValue("isAvailable", roomSaveRequestDto.isAvailable());

        return queryHelper.queryForObject(sql, params, Long.class, "saving room");
    }

    public List<RoomShortResponseDto> getAllRooms(RoomFilter filter) {
        //language=PostgreSQL
        String sql = "SELECT r.room_id AS id, r.room_number AS number, r.price_per_day AS pricePerDay, r.phone_number AS phoneNumber, " +
                "r.floor, r.is_available AS isAvailable, rt.room_type AS type " +
                "FROM rooms AS r " +
                "JOIN room_types AS rt ON r.room_type_id = rt.room_type " +
                "WHERE 1=1 ";
        if(filter.getFloor() != null) {
            sql += "AND floor = :floor ";
        }
        if (filter.getRoomTypeId() != null) {
            sql += "AND room_type_id LIKE :roomTypeId ";
        }
        if (filter.getIsAvailable() != null) {
            sql += "AND is_available LIKE :isAvailable ";
        }
        
        MapSqlParameterSource params = new MapSqlParameterSource("floor", filter.getFloor())
                .addValue("room_type_id", filter.getRoomTypeId())
                .addValue("is_available", filter.getIsAvailable());
        return queryHelper.query(sql, params, new BeanPropertyRowMapper<>(RoomShortResponseDto.class), "getting all rooms");

    }

    public Optional<RoomTypeResponseDto> getRoomTypeById(long roomTypeId) {
        //language=PostgreSQL
        String sql = "SELECT room_type_id AS id, room_type AS roomType, initial_price_per_day AS pricePerDay FROM room_types WHERE room_type_id = :roomTypeId;";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("roomTypeId", roomTypeId);
        return queryHelper.queryForObject(sql, parameterSource, new BeanPropertyRowMapper<>(RoomTypeResponseDto.class), "getting room type by id: " + roomTypeId);
    }

    public List<RoomTypeResponseDto> getAllRoomTypes() {
        //language=PostgreSQL
        String sql = "SELECT room_type_id AS id, room_type AS roomType, initial_price_per_day AS pricePerDay FROM room_types ";
        return queryHelper.query(sql, new MapSqlParameterSource(), new BeanPropertyRowMapper<>(RoomTypeResponseDto.class), "getting all room types");
    }
}
