package com.lab3.hotel.repository;

import com.lab3.hotel.model.dto.request.ScheduleFilter;
import com.lab3.hotel.model.dto.request.ScheduleSaveRequestDto;
import com.lab3.hotel.model.dto.request.ScheduleUpdateRequestDto;
import com.lab3.hotel.model.dto.response.ScheduleShortResponseDto;
import com.lab3.hotel.model.entity.ScheduleEntity;
import com.lab3.hotel.utils.QueryHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {
    private final QueryHelper queryHelper;

    public Optional<Long> saveSchedule(ScheduleSaveRequestDto scheduleSaveRequestDto) {
        //language=PostgreSQL
        String sql = "INSERT INTO cleaning_schedule (staff_id, day_id, floor) VALUES (:staffId, :dayId, :floor) RETURNING schedule_id ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffId", scheduleSaveRequestDto.getStaffId())
                .addValue("dayId", scheduleSaveRequestDto.getDayId())
                .addValue("floor", scheduleSaveRequestDto.getFloor());


        return queryHelper.queryForObject(sql, params, Long.class, "saving schedule");
    }

    public boolean isScheduleExistsById(long scheduleId) {
        //language=PostgreSQL
        String sql = "SELECT COUNT(*) FROM cleaning_schedule WHERE schedule_id = :scheduleId ";
        MapSqlParameterSource params = new MapSqlParameterSource("scheduleId", scheduleId);
        Integer counter = queryHelper.queryForObjectWithoutEmptyCheck(sql, params, Integer.class, "checking if schedule exists");
        return counter > 0;
    }

    public void updateSchedule(long scheduleId, ScheduleUpdateRequestDto updateRequestDto) {
        //language=PostgreSQL
        String sql = "UPDATE cleaning_schedule SET " +
                "staff_id = COALESCE(:staffId, staff_id), " +
                "day_id = COALESCE(:dayId, day_id), " +
                "floor = COALESCE(:floor, floor) " +
                "WHERE schedule_id = :scheduleId ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("scheduleId", scheduleId)
                .addValue("staffId", updateRequestDto.getStaffId())
                .addValue("dayId", updateRequestDto.getDayId())
                .addValue("floor", updateRequestDto.getFloor());
        queryHelper.update(sql, params, "updating schedule");
    }

    public Optional<ScheduleEntity> getScheduleById(long scheduleId) {
        //language=PostgreSQL
        String sql = "SELECT schedule_id AS id, staff_id AS staffId, day_id AS dayId, floor AS floor FROM cleaning_schedule WHERE schedule_id = :scheduleId ";
        MapSqlParameterSource params = new MapSqlParameterSource("scheduleId", scheduleId);
        return queryHelper.queryForObject(sql, params, new BeanPropertyRowMapper<>(ScheduleEntity.class), "getting schedule by id: " + scheduleId);
    }

    public List<ScheduleShortResponseDto> getAllSchedulesByFilters(ScheduleFilter filter) {
        //language=PostgreSQL
        String sql = "SELECT sch.schedule_id AS id, sch.floor AS floor, dow.short_name AS day, " +
                "s.first_name || ' ' || s.last_name AS staffName " +
                "FROM cleaning_schedule AS sch " +
                "JOIN days_of_week dow on dow.day_id = sch.day_id " +
                "JOIN staff s on s.staff_id = sch.staff_id " +
                "WHERE 1=1 ";
        if (filter.getDayId() != null) {
            sql += " AND sch.day_id = :dayId ";
        }
        if (filter.getStaffId() != null) {
            sql += " AND sch.staff_id = :staffId ";
        }
        if (filter.getRoomId() != null) {
            sql += " AND r.room_id = :roomId ";
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("dayId", filter.getDayId())
                .addValue("staffId", filter.getStaffId())
                .addValue("roomId", filter.getRoomId());
        return queryHelper.query(sql, params, new BeanPropertyRowMapper<>(ScheduleShortResponseDto.class), "getting schedule by filters");
    }
}
