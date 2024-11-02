package com.lab3.hotel.repository;

import com.lab3.hotel.model.dto.request.StaffSaveRequestDto;
import com.lab3.hotel.model.dto.response.StaffResponseDto;
import com.lab3.hotel.utils.QueryHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StaffRepository {
    private final QueryHelper queryHelper;

    public List<StaffResponseDto> getAllStaff() {
        //language=PostgreSQL
        String sql = "SELECT staff_id AS id, last_name, first_name, middle_name FROM staff ";
        return queryHelper.query(sql, new MapSqlParameterSource(), new BeanPropertyRowMapper<>(StaffResponseDto.class), "getting all staff");
    }

    public void deleteStaffById(long staffId) {
        //language=PostgreSQL
        String sql = "DELETE FROM staff WHERE staff_id = :staffId ";
        MapSqlParameterSource params = new MapSqlParameterSource("staffId", staffId);
        queryHelper.update(sql, params, "delete staff by id" + staffId);

    }

    public Optional<StaffResponseDto> getStaffById(long staffId) {
        //language=PostgreSQL
        String sql = "SELECT  staff_id AS id, first_name AS firstName, last_name AS lastName, middle_name AS middleName FROM staff WHERE staff_id = :staffId ";
        MapSqlParameterSource params = new MapSqlParameterSource("staffId", staffId);
        return queryHelper.queryForObject(sql, params, new BeanPropertyRowMapper<>(StaffResponseDto.class), "getting staff by id" + staffId);
    }

    public Optional<Long> saveStaff(StaffSaveRequestDto staffSaveRequestDto) {
        //language=SQL
        String sql = "INSERT INTO staff (last_name, first_name, middle_name) " +
                "  VALUES (:lastName, :firstName,:middleName) RETURNING staff_id ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("lastName", staffSaveRequestDto.getLastName())
                .addValue("firstName", staffSaveRequestDto.getFirstName())
                .addValue("middleName", staffSaveRequestDto.getMiddleName());

        return queryHelper.queryForObject(sql, params, Long.class, "saving staff");
    }
}
