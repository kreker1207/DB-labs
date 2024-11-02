package com.lab3.hotel.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomSaveRequestDto {
    private int number;
    private Long typeId;
    private BigDecimal pricePerDay;
    private String phoneNumber;
    private int floor;
    private boolean isAvailable;
}
