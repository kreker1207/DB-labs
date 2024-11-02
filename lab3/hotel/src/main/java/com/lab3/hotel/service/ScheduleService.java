package com.lab3.hotel.service;

import com.lab3.hotel.model.dto.enums.DayOfWeek;
import com.lab3.hotel.model.dto.request.ScheduleFilter;
import com.lab3.hotel.model.dto.request.ScheduleSaveRequestDto;
import com.lab3.hotel.model.dto.request.ScheduleUpdateRequestDto;
import com.lab3.hotel.model.dto.response.ScheduleResponseDto;
import com.lab3.hotel.model.dto.response.ScheduleShortResponseDto;
import com.lab3.hotel.model.dto.response.StaffResponseDto;
import com.lab3.hotel.model.entity.ScheduleEntity;
import com.lab3.hotel.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final StaffService staffService;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleSaveRequestDto scheduleSaveRequestDto) {
        Optional<Long> maybeSavedId = scheduleRepository.saveSchedule(scheduleSaveRequestDto);
        if (maybeSavedId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Schedule wasn't saved");
        }
        return getScheduleById(maybeSavedId.get());
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(long scheduleId, ScheduleUpdateRequestDto updateRequestDto) {
        boolean isExist = scheduleRepository.isScheduleExistsById(scheduleId);
        if (!isExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule wasn't found by id: " + scheduleId);
        }
        scheduleRepository.updateSchedule(scheduleId, updateRequestDto);
        return getScheduleById(scheduleId);
    }

    public ScheduleResponseDto getScheduleById(long scheduleId) {
        Optional<ScheduleEntity> maybeScheduleEntity = scheduleRepository.getScheduleById(scheduleId);
        if (maybeScheduleEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule wasn't found by id: " + scheduleId);
        }
        ScheduleEntity scheduleEntity = maybeScheduleEntity.get();
        StaffResponseDto staff = staffService.getStaffById(scheduleEntity.getStaffId());
        return ScheduleResponseDto.builder()
                .id(scheduleEntity.getId())
                .floor(scheduleEntity.getFloor())
                .day(DayOfWeek.getDayOfWeekById(scheduleEntity.getDayId()))
                .staff(staff)
                .build();
    }

    public List<ScheduleShortResponseDto> getAllSchedulesByFilters(ScheduleFilter filter) {
        return scheduleRepository.getAllSchedulesByFilters(filter);
    }
}
