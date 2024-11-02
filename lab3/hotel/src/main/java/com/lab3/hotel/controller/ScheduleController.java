package com.lab3.hotel.controller;

import com.lab3.hotel.model.dto.request.ScheduleFilter;
import com.lab3.hotel.model.dto.request.ScheduleSaveRequestDto;
import com.lab3.hotel.model.dto.request.ScheduleUpdateRequestDto;
import com.lab3.hotel.model.dto.response.ScheduleResponseDto;
import com.lab3.hotel.model.dto.response.ScheduleShortResponseDto;
import com.lab3.hotel.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    @PostMapping()
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleSaveRequestDto scheduleSaveRequestDto) {
        return scheduleService.createSchedule(scheduleSaveRequestDto);
    }

    @PutMapping("/{scheduleId}")
    public ScheduleResponseDto updateSchedule(@PathVariable("scheduleId") long scheduleId,
                                              @RequestBody ScheduleUpdateRequestDto updateRequestDto) {
        return scheduleService.updateSchedule(scheduleId, updateRequestDto);
    }


    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable("id") long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @GetMapping()
    public List<ScheduleShortResponseDto> getAllSchedulesByFilters(ScheduleFilter filter) {
        return scheduleService.getAllSchedulesByFilters(filter);
    }


}
