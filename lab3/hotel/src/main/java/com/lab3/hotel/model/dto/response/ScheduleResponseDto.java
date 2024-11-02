package com.lab3.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private StaffResponseDto staff;
    private String day;
    private int floor;
}
