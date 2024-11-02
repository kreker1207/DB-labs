package com.lab3.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {
    private Long id;
    private int roomNumber;
    private int roomTypeId;
    private BigDecimal pricePerDay;
    private String phoneNumber;
    private int floor;
    private boolean isAvailable;

}
