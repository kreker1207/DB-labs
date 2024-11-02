package com.lab3.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {
    private long id;
    private long staffId;
    private long dayId;
    private int floor;
}
