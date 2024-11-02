package com.lab3.hotel.repository;

import com.lab3.hotel.model.dto.request.GuestFilter;
import com.lab3.hotel.model.dto.request.GuestSaveRequestDto;
import com.lab3.hotel.model.dto.response.GuestShortResponseDto;
import com.lab3.hotel.model.entity.GuestEntity;
import com.lab3.hotel.utils.QueryHelper;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GuestRepository {
    private final QueryHelper queryHelper;

    public Optional<Long> saveGuest(GuestSaveRequestDto guestSaveRequestDto) {
        //language=SQL
        String sql = "INSERT INTO guests (passport_number, last_name, first_name, middle_name, city, check_in_date, is_inhabited, room_id) " +
                "  VALUES (:passport, :lastName, :firstName, :middleName, :city, :checkInDate, :isInhabited, :roomId) RETURNING guest_id ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("passport", guestSaveRequestDto.getPassportNumber())
                .addValue("lastName", guestSaveRequestDto.getLastName())
                .addValue("firstName", guestSaveRequestDto.getFirstName())
                .addValue("middleName", guestSaveRequestDto.getMiddleName())
                .addValue("city", guestSaveRequestDto.getCity())
                .addValue("isInhabited", guestSaveRequestDto.isInhabited())
                .addValue("checkInDate", guestSaveRequestDto.isInhabited() ? OffsetDateTime.now() : null)
                .addValue("roomId", guestSaveRequestDto.getRoomId());

        return queryHelper.queryForObject(sql, params, Long.class, "saving guest");
    }

    public boolean isExistsBtId(long guestId) {
        //language=PostgreSQL
        String sql = "SELECT COUNT(*) FROM guests WHERE guest_id = :guestId ";
        MapSqlParameterSource params = new MapSqlParameterSource("guestId", guestId);
        Integer counter = queryHelper.queryForObjectWithoutEmptyCheck(sql, params, Integer.class, "checking if guest exists");
        return counter > 0;
    }

    public List<GuestShortResponseDto> getAllGuests(GuestFilter filter) {
        //language=PostgreSQL
        String sql = "SELECT guest_id AS id, first_name AS firstName, last_name AS lastName, city, passport_number AS passportNumber, is_inhabited AS isInhabited FROM guests " +
                "WHERE 1=1 ";
        if (filter.getGuestName() != null) {
            sql += "AND first_name LIKE :guestName OR last_name LIKE :guestName ";
        }
        if (filter.getIsInhabited() != null) {
            sql += "AND is_inhabited LIKE :isInhabited ";
        }
        if (filter.getCity() != null) {
            sql += "AND city LIKE :city ";
        }
        if (filter.getRoomId() != null) {
            sql += "AND room_id LIKE :roomId ";
        }
        MapSqlParameterSource params = new MapSqlParameterSource("guestName", filter.getGuestName())
                .addValue("isInhabited", filter.getIsInhabited())
                .addValue("city", filter.getCity())
                .addValue("roomId", filter.getRoomId());
        return queryHelper.query(sql, params, new BeanPropertyRowMapper<>(GuestShortResponseDto.class), "getting all partners");

    }

    public Optional<GuestEntity> getGuestById(long guestId) {
        //language=PostgreSQL
        String sql = "SELECT guest_id AS id, passport_number AS passportNumber, last_name AS lastName, first_name AS firstName, " +
                "middle_name AS middleName, city, check_in_date AS checkInDate, is_inhabited AS isInhabited, room_id AS roomId " +
                "FROM guests " +
                "WHERE guest_id = :guestId ";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("guestId", guestId);
        return queryHelper.queryForObject(sql, parameterSource, new BeanPropertyRowMapper<>(GuestEntity.class), "getting guest by id: " + guestId);
    }

    public void checkInGuestById(long guestId, long roomId) {
        //language=PostgreSQL
        String sql = "UPDATE guests SET check_in_date = :checkInDate, is_inhabited = true, room_id = :roomId WHERE guest_id = :guestId ";
        MapSqlParameterSource params = new MapSqlParameterSource("checkInDate", OffsetDateTime.now())
                .addValue("roomId", roomId)
                .addValue("guestId", guestId);
        queryHelper.update(sql, params, "check in guest");

    }

    public void checkOutGuest(long guestId) {
        //language=PostgreSQL
        String sql = "UPDATE guests SET is_inhabited = false, room_id = NULL WHERE guest_id = :guestId ";
        MapSqlParameterSource params = new MapSqlParameterSource("guestId", guestId);
        queryHelper.update(sql, params, "checking out guest");
    }
}
