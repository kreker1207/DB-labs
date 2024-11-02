package com.lab3.hotel.service;

import com.lab3.hotel.model.dto.request.StaffSaveRequestDto;
import com.lab3.hotel.model.dto.response.StaffResponseDto;
import com.lab3.hotel.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    @Transactional
    public StaffResponseDto applyStaff(StaffSaveRequestDto staffSaveRequestDto) {
        Optional<Long> maybeCreatedStaffId = staffRepository.saveStaff(staffSaveRequestDto);
        if (maybeCreatedStaffId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Staff wasn't saved");
        }
        return getStaffById(maybeCreatedStaffId.get());
    }

    public StaffResponseDto getStaffById(long staffId) {
        Optional<StaffResponseDto> maybeStaff = staffRepository.getStaffById(staffId);
        if (maybeStaff.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff was not found by id: " + staffId);
        }
        return maybeStaff.get();
    }

    public boolean deleteStaffById(long staffId) {
        staffRepository.deleteStaffById(staffId);
        return true;
    }

    public List<StaffResponseDto> getAllStaff() {
        return staffRepository.getAllStaff();
    }
}
