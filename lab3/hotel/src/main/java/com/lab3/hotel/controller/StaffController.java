package com.lab3.hotel.controller;

import com.lab3.hotel.model.dto.request.StaffSaveRequestDto;
import com.lab3.hotel.model.dto.response.StaffResponseDto;
import com.lab3.hotel.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PostMapping()
    public StaffResponseDto applyStaff(@RequestBody StaffSaveRequestDto staffSaveRequestDto) {
        return staffService.applyStaff(staffSaveRequestDto);
    }

    @GetMapping("/{id}")
    public StaffResponseDto getStaffById(@PathVariable("id") long staffId) {
        return staffService.getStaffById(staffId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStaffById(@PathVariable("id") long staffId) {
        return staffService.deleteStaffById(staffId);
    }

    @GetMapping()
    public List<StaffResponseDto> getAllStaff() {
        return staffService.getAllStaff();
    }
}
